package com.denis.learning.io.chapter_4.serialization.custom;

import java.io.*;

public class TwoDPoint {
    private double x;
    private double y;

    public TwoDPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "{" + "x=" + x + ", y="  + y + '}';
    }

    public void writeState(OutputStream out) throws IOException {
        DataOutputStream dout = new DataOutputStream(out);
        dout.writeDouble(x);
        dout.writeDouble(y);
    }

    public void readState(InputStream in) throws IOException {
        DataInputStream din = new DataInputStream(in);
        x = din.readDouble();
        y = din.readDouble();
    }

    public static void main(String[] args) throws IOException {
        TwoDPoint point = new TwoDPoint(12, 23);
        point.writeState(new FileOutputStream("point.dat"));
        System.out.println("before = " + point);
        point.readState(new FileInputStream("point.dat"));
        System.out.println("after = " + point);
    }
}
