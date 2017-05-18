package com.denis.learning.io.chapter_6;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Java source code can include Unicode escapes for characters not available in the current character set.
 * An escape sequence is a \\u followed by the four-hexadecimal-digit equivalent of the Unicode character.
 * As an example, I’ll write a FilterReader subclass that reads a \\u-escaped file and converts it to pure Unicode.
 * This is a much trickier problem than it first appears. First, there’s not a fixed ratio between the number
 * of bytes and number of chars. Most of the time one byte is one char, but some of the time five bytes are one char.
 * The second difficulty is ensuring that \u09EF is recognized as Unicode escape while \\u09EF is not.
 * In other words, only a u preceded by an odd number of slashes is a valid Unicode escape.
 */
public class SourceReader extends FilterReader {

    private int buffer = -1;

    protected SourceReader(Reader in) {
        super(in);
    }

    @Override
    public int read() throws IOException {

        if (this.buffer != -1) {
            int c = this.buffer;
            this.buffer = -1;
            return c;
        }

        int c = in.read();
        if (c != '\\') return c;
        int next = in.read();
        if (next != 'u') { // This is not a Unicode escape
            this.buffer = next;
            return c;
        }

        // Read next 4 hex digits
        // If the next four chars do not make a valid hex digit
        // this is not a valid .java file.
        StringBuilder sb = new StringBuilder();
        sb.append((char) in.read());
        sb.append((char) in.read());
        sb.append((char) in.read());
        sb.append((char) in.read());
        String hex = sb.toString();
        try {
            return Integer.valueOf(hex, 16);
        }
        catch (NumberFormatException ex) {
            throw new IOException("Bad Unicode escape: \\u" + hex);
        }
    }

    private boolean endOfStream;

    @Override
    public int read(char[] text, int off, int len) throws IOException {
        if (endOfStream) return -1;
        int numRead = 0;

        for (int i = off; i < off + len; i++) {
            int temp = this.read();
            if (temp == -1) {
                endOfStream = true;
                break;
            }

            text[i] = (char) temp;
            numRead++;
        }

        return numRead;
    }

    public long skip(long n) throws IOException {
        char[] c = new char[(int) n];
        int numSkipped = this.read(c);
        return numSkipped;
    }
}
