package com.denis.learning.management;

public class CheckWatcher {

    public static void main(String[] args) {
        ByteWatcher helper = new ByteWatcher();
        helper.onThreadCreated((t) -> System.out.println("New thread: " + t.getName()));

        helper.reset();
        byte[] bytes = new byte[100];
        helper.forEach(c -> {
            if (c.getThread().getName().equals("main")) {
                System.out.println("Main: " + c.calculateAllocations());
            }
        });

        helper.printAllAllocations();
    }
}
