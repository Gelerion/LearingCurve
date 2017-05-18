package com.denis.learning.io.chapter_3.checksum;

import java.util.zip.Checksum;

public class ParityChecksum implements Checksum {
    private long checksum;

    @Override
    public void update(int b) {
        int numOneBits = 0;

        //Integer.bitCount(b);
        for (int i = 1; i < 256; i *= 2) {
            if ((b & i) != 0) {
                numOneBits++;
            }
        }

        checksum = (checksum + numOneBits) % 2; //1 is even, 0 is odd

    }

    @Override
    public void update(byte[] b, int off, int len) {
        for (int i = off; i < len; i++) {
            update(b[i]);
        }
    }

    @Override
    public long getValue() {
        return checksum;
    }

    @Override
    public void reset() {
        checksum = 0;
    }
}
