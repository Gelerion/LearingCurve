package com.denis.learning.io.chapter_6;

import java.io.IOException;
import java.io.Writer;

public class UnsynchronizedBufferedWriter extends Writer {
    private final char[] buffer;
    private final Writer out;
    private int defSize = 8192;
    private int charsCount, position;

    public UnsynchronizedBufferedWriter(Writer out) {
        this.out = out;
        this.buffer = new char[defSize];
        this.charsCount = defSize;
        this.position = 0;
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        ensureOpen();
        if ((off < 0) || (off > cbuf.length) || (len < 0) ||
                ((off + len) > cbuf.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return;
        }

        if (len >= charsCount) {
            /* If the request length exceeds the size of the output buffer,
               flush the buffer and then write the data directly.  In this
               way buffered streams will cascade harmlessly. */
            flush();
            out.write(cbuf, off, len);
            return;
        }

        int startIndex = off, endIndex = off + len;
        while (startIndex < endIndex) {
            int copyLength = min(charsCount - position, endIndex - startIndex);
            System.arraycopy(cbuf, startIndex, buffer, position, copyLength);
            startIndex += copyLength;
            position += copyLength;
            if(position > charsCount) flush();
        }
    }

    @Override
    public void flush() throws IOException {
        out.write(buffer, 0, charsCount);
        position = 0;
        out.flush();
    }

    @Override
    public void close() throws IOException {
        flush();
        out.close();

    }

    private void ensureOpen() throws IOException {
        if (out == null)
            throw new IOException("Stream closed");
    }

    private int min(int a, int b) {
        if (a < b) return a;
        return b;
    }
}
