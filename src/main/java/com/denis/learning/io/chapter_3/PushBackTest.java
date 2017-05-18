package com.denis.learning.io.chapter_3;

import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.Locale;

public class PushBackTest {
    public static void main(String[] args) throws IOException {
        PushbackInputStream in = new PushbackInputStream(System.in, 5);
        in.unread(0);
        in.unread(1);
        in.unread(2);
        System.out.println(in.read());
        System.out.println(in.read());
        System.out.println(in.read());

        Locale locale = Locale.US;
        System.out.println(locale.toLanguageTag());
    }
}
