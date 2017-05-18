package com.denis.learning.io.chapter_3.checksum;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class FileSummer {
    public static void main(String[] args) throws IOException {
        FileInputStream fin = new FileInputStream(args[0]);
        System.out.println(args[0] + ":\t" + getCRC32(fin));
        fin.close( );
    }

    private static Checksum getCRC32(FileInputStream fin) throws IOException {
        Checksum cr = new CRC32();
        // It would be more efficient to read chunks of data
        // at a time, but this is simpler and easier to understand.

        for (int i = fin.read(); i > 0; i = fin.read()) {
            cr.update(i);
        }

        return cr;
    }
}
