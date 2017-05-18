package com.denis.learning.io.chapter_6;

import java.io.UnsupportedEncodingException;

public class Encodings {
    public static void main(String[] args) throws UnsupportedEncodingException {
        byte[] isoLatin1 = new byte[256];
        for (int i = 0; i < 256; i++) isoLatin1[i] = (byte) i;
        String s = new String(isoLatin1, "8859_1");
        System.out.println("8859_1 = " + s);

        byte[] isoLatin1_2 = new byte[256];
        for (int i = 0; i < 256; i++) isoLatin1_2[i] = (byte) i;
        String s2 = new String(isoLatin1_2);
        System.out.println("default = " + s2);
    }
}
