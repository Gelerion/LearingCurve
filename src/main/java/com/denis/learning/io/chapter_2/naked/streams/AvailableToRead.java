package com.denis.learning.io.chapter_2.naked.streams;

import java.io.IOException;

public class AvailableToRead {
    public static void main(String[] args) throws IOException {
        byte[] b = new byte[10];
        int offset = 0;
        while (offset < b.length) {
            int available = System.in.available();
            //better idiom:
            // byte[] b = new byte[System.in.available( )];
            // System.in.read(b);
            int bytesRead = System.in.read(b, offset, available);

            if(bytesRead == -1) break; // end of stream
            offset += bytesRead;
        }
    }
}
