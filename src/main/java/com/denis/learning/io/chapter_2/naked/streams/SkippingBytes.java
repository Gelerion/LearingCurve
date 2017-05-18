package com.denis.learning.io.chapter_2.naked.streams;

import java.io.IOException;

public class SkippingBytes {
    public static void main(String[] args) throws IOException {
        long bytesSkipped = 0;
        long bytesToSkip = 80;
        while (bytesSkipped < bytesToSkip) {
            long n = System.in.skip(bytesToSkip - bytesSkipped);
            if(n == -1) break;
            bytesSkipped += n;
        }
    }
}
