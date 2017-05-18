package com.denis.learning.io.chapter_3.data;

import java.io.DataOutput;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class LittleEndianOutputStream extends FilterOutputStream implements DataOutput {
    protected int written;

    public LittleEndianOutputStream(OutputStream out) {
        super(out);
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    @Override
    public void writeBoolean(boolean v) throws IOException {
        if (v) {
            write(1);
        }
        else write(0);
    }

    @Override
    public void writeByte(int v) throws IOException {
        write(v);
    }

    @Override
    public void writeShort(int v) throws IOException {
        write((v >>> 0) & 0xFF);
        write((v >>> 8) & 0xFF);
        written += 2;
    }

    @Override
    public void writeChar(int v) throws IOException {
        writeShort(v);
    }

    @Override
    public void writeInt(int v) throws IOException {
        write((v >>> 0) & 0xFF);
        write((v >>> 8) & 0xFF);
        write((v >>> 16) & 0xFF);
        write((v >>> 24) & 0xFF);
        written += 4;
    }

    @Override
    public void writeLong(long v) throws IOException {
        write((int) ((v >>> 0) & 0xFF));
        write((int) ((v >>> 8) & 0xFF));
        write((int) ((v >>> 16) & 0xFF));
        write((int) ((v >>> 24) & 0xFF));
        write((int) ((v >>> 32) & 0xFF));
        write((int) ((v >>> 40) & 0xFF));
        write((int) ((v >>> 48) & 0xFF));
        write((int) ((v >>> 56) & 0xFF));
        written += 8;
    }

    @Override
    public void writeFloat(float v) throws IOException {
        writeInt(Float.floatToIntBits(v));
    }

    @Override
    public void writeDouble(double v) throws IOException {
        writeLong(Double.doubleToLongBits(v));
    }

    @Override
    public void writeBytes(String s) throws IOException {
        int length = s.length( );
        for (int i = 0; i < length; i++) {
            out.write((byte) s.charAt(i));
        }
        written += length;
    }

    @Override
    public void writeChars(String s) throws IOException {
        int length = s.length();
        for (int i = 0; i < length; i++) {
            int c = s.charAt(i);
            out.write(c & 0xFF);
            out.write((c >>> 8) & 0xFF);
            written += 2;
        }

    }

    @Override
    public void writeUTF(String s) throws IOException {
        int numChars = s.length();
        int numBytes = 0;

        for (int i = 0 ; i < numChars ; i++) {
            int c = s.charAt(i);
            if ((c >= 0x0001) && (c <= 0x007F)) numBytes++;
            else if (c > 0x07FF) numBytes += 3;
            else numBytes += 2;
        }
        //TODO...
    }
}
