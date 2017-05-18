package com.denis.learning.io.chapter_2.naked.streams;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

public class JStreamTextArea extends JTextArea {
    private OutputStream theOutput = new TextAreOutputStream();

    public JStreamTextArea() {
        this("", 0, 0);
    }

    public JStreamTextArea(String text) {
        this(text, 0, 0);
    }

    public JStreamTextArea(int rows, int columns) {
        this("", rows, columns);
    }

    public JStreamTextArea(String text, int rows, int columns) {
        super(text, rows, columns);
        setEditable(false);
    }

    public OutputStream getOutputStream() {
        return theOutput;
    }

    private class TextAreOutputStream extends OutputStream {
        private boolean isClosed;

        @Override
        public void write(int b) throws IOException {
            checkOpen();

            // recall that the int should really just be a byte
            b &= 0x000000FF; //... 0000 1111 1111
            // must convert byte to a char in order to append it
            char c = (char) b; //http://stackoverflow.com/questions/17912640/byte-and-char-conversion-in-java

            append(String.valueOf(c));
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            append(new String(b, off, len));

        }

        @Override
        public void close() {
            this.isClosed = true;
        }

        private void checkOpen() throws IOException {
            if (isClosed) throw new IOException("Already closed");
        }
    }

}
