package com.denis.learning.threads;

public class Quet {
    public static void sleep() {
        try {
            Thread.sleep(100);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
