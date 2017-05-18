package com.denis.learning.io.chapter_4;

import java.awt.*;
import java.io.*;

public class ObjectWriter {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Point point = new Point(34, 22);
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("point.ser"));
        out.writeObject(point);
        out.close();
        System.out.println("Done writing");

        ObjectInputStream in = new ObjectInputStream(new FileInputStream("point.ser"));
        Point alive = (Point) in.readObject();
        System.out.println("alive = " + alive);
    }
}
