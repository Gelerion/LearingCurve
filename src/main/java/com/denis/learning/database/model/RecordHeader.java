package com.denis.learning.database.model;

import com.denis.learning.database.repository.RecordsFileException;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class RecordHeader {
	/**
	 * File pointer to the first byte of record data (8 bytes).
	 */
	private long dataPointer;
	/**
	 * Actual number of bytes of data held in this record (4 bytes).
	 */
	private int dataCount;
	/**
	 * Number of bytes of data that this record can hold (4 bytes).
	 */
	private int dataCapacity;
	/**
	 * Indicates this header's position in the file index.
	 */
	private int indexPosition;

	protected RecordHeader() {
	}

	public RecordHeader(long dataPointer, int dataCapacity) {
		if (dataCapacity < 1) {
			throw new IllegalArgumentException("Bad record size: " + dataCapacity);
		}
		this.dataPointer = dataPointer;
		this.dataCapacity = dataCapacity;
		this.dataCount = 0;
	}

	public int getIndexPosition() {
		return indexPosition;
	}

	public void setIndexPosition(int indexPosition) {
		this.indexPosition = indexPosition;
	}

	public int getDataCapacity() {
		return dataCapacity;
	}

	public long getDataPointer() {
		return dataPointer;
	}

	public int getDataCount() {
		return dataCount;
	}

	public int getFreeSpace() {
		return dataCapacity - dataCount;
	}

	protected void read(DataInput in) throws IOException {
		dataPointer = in.readLong();
		dataCapacity = in.readInt();
		dataCount = in.readInt();
	}

	public void write(DataOutput out) throws IOException {
        out.writeLong(dataPointer);
        out.writeInt(dataCapacity);
        out.writeInt(dataCount);
    }

    public static RecordHeader readHeader(DataInput in) throws IOException {
        RecordHeader result = new RecordHeader();
        result.read(in);
        return result;
    }

    /**
     * Returns a new record header which occupies the free space of this record.
     * Shrinks this record size by the size of its free space.
     */
    public RecordHeader split() throws RecordsFileException {
        long newFp = dataPointer + (long) dataCount;
        RecordHeader newRecord = new RecordHeader(newFp, getFreeSpace());
        dataCapacity = dataCount;
        return newRecord;
    }

	public void setDataCount(int dataCount) {
		this.dataCount = dataCount;
	}

	public RecordHeader setDataPointer(long dataPointer) {
		this.dataPointer = dataPointer;
		return this;
	}

	public RecordHeader setDataCapacity(int dataCapacity) {
		this.dataCapacity = dataCapacity;
		return this;
	}
}
