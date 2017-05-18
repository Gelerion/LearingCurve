package com.denis.learning.database.repository;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Extends ByteArrayOutputStream to provide a way of writing the buffer to
 * a DataOutput without re-allocating it.
 */
public class DbByteArrayOutputStream extends ByteArrayOutputStream {

    public DbByteArrayOutputStream() {
    }

    public DbByteArrayOutputStream(int size) {
        super(size);
    }

    public synchronized void writeTo(DataOutput out) throws IOException {
        byte[] data = super.buf;
        int size = super.size();
        out.write(data, 0, size);
    }
}
