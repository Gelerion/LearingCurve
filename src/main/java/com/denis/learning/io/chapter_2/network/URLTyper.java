package com.denis.learning.io.chapter_2.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class URLTyper {
    public static void main(String[] args) throws IOException {
        URL u = new URL("http://www.oreilly.com/");
        try (InputStream in = u.openStream()) {
            for (int i = in.read(); i != -1; i = in.read()) {
                System.out.write(i);
            }
        }

    }
}
