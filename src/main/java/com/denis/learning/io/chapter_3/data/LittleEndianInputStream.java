package com.denis.learning.io.chapter_3.data;

import java.io.*;

public class LittleEndianInputStream extends FilterInputStream implements DataInput {

    protected LittleEndianInputStream(InputStream in) {
        super(in);
    }

    @Override
    public boolean readBoolean() throws IOException {
        int b = in.read();
        if(b == -1)  throw new EOFException();
        return b == 1;
    }

    @Override
    public byte readByte() throws IOException {
        int b = in.read();
        if(b == -1)  throw new EOFException();
        return (byte) b;
    }

    @Override
    public int readUnsignedByte() throws IOException {
        int b = in.read();
        if(b == -1)  throw new EOFException();
        return b;
    }

    @Override
    public short readShort() throws IOException {
        int byte1 = in.read();
        int byte2 = in.read();

        // only need to test last byte read if byte1 is -1 so is byte2
        if (byte2 == -1) throw new EOFException( );

        return (short) ((byte2 << 8) | byte1);
    }

    @Override
    public int readUnsignedShort() throws IOException {
        return 0;
    }

    @Override
    public char readChar() throws IOException {
        return 0;
    }

    @Override
    public int readInt() throws IOException {
        return 0;
    }

    @Override
    public long readLong() throws IOException {
        return 0;
    }

    @Override
    public float readFloat() throws IOException {
        return 0;
    }

    @Override
    public double readDouble() throws IOException {
        return 0;
    }

    @Override
    public String readLine() throws IOException {
        return null;
    }

    @Override
    public String readUTF() throws IOException {
        return null;
    }

    @Override
    public void readFully(byte[] b) throws IOException {

    }

    @Override
    public void readFully(byte[] b, int off, int len) throws IOException {

    }

    @Override
    public int skipBytes(int n) throws IOException {
        return 0;
    }
}
