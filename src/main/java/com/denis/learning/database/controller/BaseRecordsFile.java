package com.denis.learning.database.controller;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;

import com.denis.learning.database.model.RecordHeader;
import com.denis.learning.database.repository.DbByteArrayOutputStream;
import com.denis.learning.database.repository.RecordReader;
import com.denis.learning.database.repository.RecordWriter;
import com.denis.learning.database.repository.RecordsFileException;

public abstract class BaseRecordsFile {
	// The database file.
	private RandomAccessFile file;

	// Current file pointer to the start of the record data.
	protected long dataStartPtr;

	// Total length in bytes of the global database headers.
	protected static final int FILE_HEADERS_REGION_LENGTH = 16;

	// Number of bytes in the record header.
	protected static final int RECORD_HEADER_LENGTH = 16;

	// The length of a key in the index.
	protected static final int MAX_KEY_LENGTH = 64;

	// The total length of one index entry - the key length plus the record
	// header length.
	protected static final int INDEX_ENTRY_LENGTH = MAX_KEY_LENGTH + RECORD_HEADER_LENGTH;

	// File pointer to the num records header.
	protected static final long NUM_RECORDS_HEADER_LOCATION = 0;

	// File pointer to the data start pointer header.
	protected static final long DATA_START_HEADER_LOCATION = 4;

    protected BaseRecordsFile(String dbPath, int initialSize) throws IOException, RecordsFileException {
        File dbFile = new File(dbPath);
        if (dbFile.exists()) {
            throw new RecordsFileException("Database already exits: " + dbPath);
        }
        this.file = new RandomAccessFile(dbFile, "rw");
        dataStartPtr = indexPositionToKeyFp(initialSize);
        setFileLength(dataStartPtr);
        writeNumRecordsHeader(0);
        writeDataStartPtrHeader(dataStartPtr);
    }

    /**
     * Opens an existing database file and initializes the dataStartPtr. The accessFlags
     * parameter can be "r" or "rw" -- as defined in RandomAccessFile.
     */
    protected BaseRecordsFile(String dbPath, String accessFlags) throws IOException, RecordsFileException {
        File file = new File (dbPath);
        if(!file.exists()) {
            throw new RecordsFileException("Database not found: " + dbPath);
        }
        this.file = new RandomAccessFile(file, accessFlags);
        dataStartPtr = readDataStartHeader();
    }

    /**
     * Returns an Enumeration of the keys of all records in the database.
     */
    public abstract Iterator<String> enumerateKeys();
    /**
     * Returns the number or records in the database.
     */
    public abstract int getNumRecords();
    /**
     * Checks there is a record with the given key.
     */
    public abstract boolean recordExists(String key);
    /**
     * Maps a key to a record header.
     */
    protected abstract RecordHeader keyToRecordHeader(String key) throws RecordsFileException;
    /**
     * Locates space for a new record of dataLength size and initializes a RecordHeader.
     */
    protected abstract RecordHeader allocateRecord(String key, int dataLength) throws RecordsFileException, IOException;
    /**
     * Returns the record to which the target file pointer belongs - meaning the specified location
     * in the file is part of the record data of the RecordHeader which is returned.  Returns null if
     * the location is not part of a record. (O(n) mem accesses)
     */
    protected abstract RecordHeader getRecordAt(long targetFp) throws RecordsFileException;

    long indexPositionToRecordHeaderFp(int pos) {
        return indexPositionToKeyFp(pos) + MAX_KEY_LENGTH;
    }

    /**
     * Reads the ith key from the index.
     */
    String readKeyFromIndex(int position) throws IOException {
        file.seek(indexPositionToKeyFp(position));
        return file.readUTF();
    }

    /**
     * Reads the ith record header from the index.
     */
    RecordHeader readRecordHeaderFromIndex(int position) throws IOException {
        file.seek(indexPositionToRecordHeaderFp(position));
        return RecordHeader.readHeader(file);
    }

    protected void writeRecordHeaderToIndex(RecordHeader header) throws IOException {
        file.seek(indexPositionToRecordHeaderFp(header.getIndexPosition()));
        header.write(file);
    }

    /**
     * Appends an entry to end of index. Assumes that insureIndexSpace() has already been called.
     */
    protected void addEntryToIndex(String key, RecordHeader newRecord, int currentNumRecords)
            throws IOException, RecordsFileException {
        try (DbByteArrayOutputStream temp = new DbByteArrayOutputStream(MAX_KEY_LENGTH)) {
            new DataOutputStream(temp).writeUTF(key);
            if (temp.size() > MAX_KEY_LENGTH) {
                throw new RecordsFileException("Key is larger than permitted size of " + MAX_KEY_LENGTH + " bytes");
            }

            file.seek(indexPositionToKeyFp(currentNumRecords));
            temp.writeTo(file);
        }

        file.seek(indexPositionToRecordHeaderFp(currentNumRecords));
        newRecord.write(file);
        newRecord.setIndexPosition(currentNumRecords);
        writeNumRecordsHeader(currentNumRecords+1);
    }

    /**
     * Adds the given record to the database.
     */
    public void insertRecord(RecordWriter rw) throws RecordsFileException, IOException {
        String key = rw.getKey();
        if (recordExists(key)) {
            throw new RecordsFileException("Key exists: " + key);
        }
        insureIndexSpace(getNumRecords() + 1);
        RecordHeader newRecord = allocateRecord(key, rw.getDataLength());
        writeRecordData(newRecord, rw);
        addEntryToIndex(key, newRecord, getNumRecords());
    }

    /**
     * Updates the contents of the given record. A RecordsFileException is thrown if the new data does not
     * fit in the space allocated to the record. The header's data count is updated, but not
     * written to the file.
     */
    protected void writeRecordData(RecordHeader header, RecordWriter rw) throws IOException, RecordsFileException {
        if (rw.getDataLength() > header.getDataCapacity()) {
            throw new RecordsFileException ("Record data does not fit");
        }
        header.setDataCount(rw.getDataLength());
        file.seek(header.getDataPointer());
        rw.writeTo(file);
    }
    /**
     * Updates the contents of the given record. A RecordsFileException is thrown if the new data does not
     * fit in the space allocated to the record. The header's data count is updated, but not
     * written to the file.
     */
    protected void writeRecordData(RecordHeader header, byte[] data) throws IOException, RecordsFileException {
        if (data.length > header.getDataCapacity()) {
            throw new RecordsFileException ("Record data does not fit");
        }
        header.setDataCount(data.length);
        file.seek(header.getDataPointer());
        file.write(data, 0, data.length);
    }

    /**
     * Reads a record.
     */
    public synchronized RecordReader readRecord(String key) throws RecordsFileException, IOException {
        byte[] data = readRecordData(key);
        return new RecordReader(key, data);
    }

    private byte[] readRecordData(String key) throws RecordsFileException, IOException {
        return readRecordData(keyToRecordHeader(key));
    }

    /**
     * Reads the record data for the given record header.
     */
    protected byte[] readRecordData(RecordHeader header) throws IOException {
        byte[] buf = new byte[header.getDataCount()];
        file.seek(header.getDataPointer());
        file.readFully(buf);
        return buf;
    }

    protected long indexPositionToKeyFp(int pos) {
        //file header region contains:
        //  1) Num records - long
        //  2) Data start pointer - long

        //index region (entry) starts on the first byte after the file headers region
        //and extends until the byte before the data start pointer
        //entries have a fixed length
        return FILE_HEADERS_REGION_LENGTH + (INDEX_ENTRY_LENGTH * pos);
    }

    protected long getFileLength() throws IOException {
        return file.length();
    }

    protected void setFileLength(long length) throws IOException {
        file.setLength(length);
    }

    protected int readNumRecordsHeader() throws IOException {
        file.seek(NUM_RECORDS_HEADER_LOCATION);
        return file.readInt();
    }

    protected void writeNumRecordsHeader(int numRecords) throws IOException {
        file.seek(NUM_RECORDS_HEADER_LOCATION);
        file.writeInt(numRecords);
    }

    protected void writeDataStartPtrHeader(long dataStartPtr) throws IOException {
        file.seek(DATA_START_HEADER_LOCATION);
        file.writeLong(dataStartPtr);
    }

    private long readDataStartHeader() throws IOException {
        file.seek(DATA_START_HEADER_LOCATION);
        return file.readLong();
    }

    // Checks to see if there is space for and additional index entry. If
    // not, space is created by moving records to the end of the file.
//    void insureIndexSpace(int requiredNumRecords) throws RecordsFileException, IOException {
//        int currentNumRecords = getNumRecords();
//        long endIndexPtr = indexPositionToKeyFp(requiredNumRecords);
//
//        if (endIndexPtr > getFileLength()) {
//            System.out.println("No space for index");
//            throw new RuntimeException();
//        }
//
//
//        RecordHeader first = getRecordAt(dataStartPtr);
//        byte[] data = readRecordData(first);
//        first.setDataPointer(getFileLength());
//
//    }

    protected void insureIndexSpace(int requiredNumRecords) throws RecordsFileException, IOException {
        int currentNumRecords = getNumRecords();
        long endIndexPtr = indexPositionToKeyFp(requiredNumRecords);
        if (endIndexPtr > getFileLength() && currentNumRecords == 0) { //if initial size is set  to 0
            setFileLength(endIndexPtr);
            dataStartPtr = endIndexPtr;
            writeDataStartPtrHeader(dataStartPtr);
            return;
        }
        while (endIndexPtr > dataStartPtr) { //new record header points to data
            RecordHeader first = getRecordAt(dataStartPtr);
            byte[] data = readRecordData(first);
            first.setDataPointer(getFileLength()); //expand till current file size ...
            first.setDataCapacity(data.length);
            setFileLength(first.getDataPointer() + data.length);
            writeRecordData(first, data);
            writeRecordHeaderToIndex(first);
            dataStartPtr += first.getDataCapacity();
            writeDataStartPtrHeader(dataStartPtr);
        }
    }

    /**
     * Closes the file.
     */
    public synchronized void close() throws IOException, RecordsFileException {
        try {
            file.close();
        } finally {
            file = null;
        }
    }

}
