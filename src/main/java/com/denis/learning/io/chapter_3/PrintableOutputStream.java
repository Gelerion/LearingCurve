package com.denis.learning.io.chapter_3;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PrintableOutputStream extends FilterOutputStream {

    public PrintableOutputStream(OutputStream out) {
        super(out);
    }

    @Override
    public void write(int b) throws IOException {
        // carriage return, linefeed, and tab
        if (b == '\n' || b == '\r' || b == '\t') {
            out.write(b);
        }
        else if (b < 32 || b > 126) {
            out.write('?');
        }
        else {
            out.write(b);
        }
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        for (int i = off; i < len; i++) {
            this.write(b[i]);
        }
    }

    public static void main(String[] args) {
        OutputStream out = new PrintableOutputStream(System.out);
    }
}
