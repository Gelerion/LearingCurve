package com.denis.learning.io.chapter_3.flate;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class GZipper {
    public final static String GZIP_SUFFIX = ".gz";

    public static void main(String[] args) throws IOException {
        FileInputStream fin = new FileInputStream("abc");
        FileOutputStream fout = new FileOutputStream("abc" + GZIP_SUFFIX);
        GZIPOutputStream gzout = new GZIPOutputStream(fout);

        for (int c = fin.read(); c != -1 ; c = fin.read()) {
            gzout.write(c);
        }

        gzout.close();

    }
}
