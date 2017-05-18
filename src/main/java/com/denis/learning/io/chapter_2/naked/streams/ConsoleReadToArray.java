package com.denis.learning.io.chapter_2.naked.streams;

import java.io.IOException;

public class ConsoleReadToArray {
    public static void main(String[] args) throws IOException {
        byte[] b = new byte[10];
        int offset = 0;
        while (offset < b.length) {
            int bytesRead = System.in.read(b, offset, b.length);
            if(bytesRead == -1) break;
            offset += bytesRead;
        }
    }
}
