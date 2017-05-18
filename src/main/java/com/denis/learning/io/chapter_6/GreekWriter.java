package com.denis.learning.io.chapter_6;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class GreekWriter {
    public static void main(String[] args) throws IOException {
        String arete = "\u03B1\u03C1\u03B5\u03C4\u03B7";
        OutputStreamWriter out = new OutputStreamWriter(System.out);
        out.write(arete, 0, arete.length());
        out.flush();
    }
}
