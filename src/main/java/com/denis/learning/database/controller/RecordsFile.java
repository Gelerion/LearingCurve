package com.denis.learning.database.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import com.denis.learning.database.model.RecordHeader;
import com.denis.learning.database.repository.RecordsFileException;

public class RecordsFile extends BaseRecordsFile {
    /**
     * HashMap which holds the in-memory index. For efficiency, the entire index
     * is cached in memory. The hashtable maps a key of type String to a RecordHeader.
     */
    protected HashMap<String, RecordHeader> memIndex;

    /**
     * Creates a new database file.  The initialSize parameter determines the
     * amount of space which is allocated for the index.  The index can grow
     * dynamically, but the parameter is provide to increase
     * efficiency.
     */
    public RecordsFile(String dbPath, int initialSize) throws IOException, RecordsFileException {
        super(dbPath, initialSize);
        memIndex = new HashMap<>(initialSize);
    }

    /**
     * Opens an existing database and initializes the in-memory index.
     */
    public RecordsFile(String dbPath, String accessFlags) throws IOException, RecordsFileException {
        super(dbPath, accessFlags);
        int numRecords = readNumRecordsHeader();
        memIndex = new HashMap<>(numRecords);
        for (int i = 0; i < numRecords; i++) {
            String key = readKeyFromIndex(i);
            RecordHeader header = readRecordHeaderFromIndex(i);
            header.setIndexPosition(i);
            memIndex.put(key, header);
        }
    }

    @Override
    public Iterator<String> enumerateKeys() {
        return memIndex.keySet().iterator();
    }

    @Override
    public int getNumRecords() {
        return memIndex.size();
    }

    @Override
    public boolean recordExists(String key) {
        return memIndex.containsKey(key);
    }

    @Override
    protected RecordHeader keyToRecordHeader(String key) throws RecordsFileException {
        if(!recordExists(key)) throw new RecordsFileException("Key not found: " + key);
        return memIndex.get(key);
    }

    @Override
    protected RecordHeader allocateRecord(String key, int dataLength) throws RecordsFileException, IOException {
        RecordHeader newRecord = null;
        for (RecordHeader header : memIndex.values()) {
            if (dataLength <= header.getFreeSpace()) {
                newRecord = header.split();
                writeRecordHeaderToIndex(header);
                break;
            }
        }

        if (newRecord == null) {
            // append record to end of file - grows file to allocate space
            long fp = getFileLength();
            setFileLength(fp + dataLength);
            newRecord = new RecordHeader(fp, dataLength);
        }

        return newRecord;
    }

    @Override
    protected void addEntryToIndex(String key, RecordHeader newRecord, int currentNumRecords) throws IOException, RecordsFileException {
        super.addEntryToIndex(key, newRecord, currentNumRecords);
        memIndex.put(key, newRecord);
    }

    @Override
    protected RecordHeader getRecordAt(long targetFp) throws RecordsFileException {
        for (RecordHeader recordHeader : memIndex.values()) {
            if (targetFp >= recordHeader.getDataPointer() &&
                    targetFp <= recordHeader.getDataPointer() + recordHeader.getDataCapacity()) {
				return recordHeader;
            }
        }
        return null;
    }

    public void close() throws IOException, RecordsFileException {
        try {
            super.close();
        } finally {
            memIndex.clear();
            memIndex = null;
        }
    }
}
