package com.denis.learning.database.repository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class RecordReader {
    //record's key
    String key;
    //record data
    byte[] data;
    ByteArrayInputStream in;
    ObjectInputStream objIn;

    public RecordReader(String key, byte[] data) {
        this.key = key;
        this.data = data;
        this.in = new ByteArrayInputStream(data);
    }

    public InputStream getInputStream() throws IOException {
        return in;
    }

    public ObjectInputStream getObjectInputStream() throws IOException {
        if (objIn == null) {
            objIn = new ObjectInputStream(in);
        }
        return objIn;
    }

    public Object readObject() throws IOException, ClassNotFoundException {
        return getObjectInputStream().readObject();
    }


}
