package com.denis.learning.io.chapter_3;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class PrintStreamTest {
    public static void main(String[] args) throws FileNotFoundException {
        PrintStream out = new PrintStream(new FileOutputStream("numbers.dat"));
        for (int i = 0; i <= 127; i++) out.write(i); //just bytes
        for (int i = 0; i <= 127; i++) out.print(i); //strings
    }
}
