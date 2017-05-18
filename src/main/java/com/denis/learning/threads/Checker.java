package com.denis.learning.threads;

public class Checker {
    public static void main(String[] args) throws InterruptedException {
        TitlesCache.INSTANCE.fillCahce();

        Thread readerOne = new Thread(() -> {
//            int findId = ThreadLocalRandom.current().nextInt(1000);
            while (true) {
                int findId = 1;
                Title title = TitlesCache.INSTANCE.findById(findId);
                System.out.println(Thread.currentThread().getName() + " findById: " + findId + " " + title);
                Quet.sleep();
            }
        });

        Thread readerTwo = new Thread(() -> {
//            int findId = ThreadLocalRandom.current().nextInt(1000);
            while (true) {
                int findId = 2;
                Title title = TitlesCache.INSTANCE.findById(findId);
                System.out.println(Thread.currentThread().getName() + " findById: " + findId + " " + title);
                Quet.sleep();
            }

        });

        Thread readerThree= new Thread(() -> {
//            int findId = ThreadLocalRandom.current().nextInt(1000);
            while (true) {
                int findId = 3;
                Title title = TitlesCache.INSTANCE.findById(findId);
                System.out.println(Thread.currentThread().getName() + " findById: " + findId + " " + title);
                Quet.sleep();
            }

        });

        Thread readerfour= new Thread(() -> {
//            int findId = ThreadLocalRandom.current().nextInt(1000);
            while (true) {
                int findId = 4;
                Title title = TitlesCache.INSTANCE.findById(findId);
                System.out.println(Thread.currentThread().getName() + " findById: " + findId + " " + title);
                Quet.sleep();
            }

        });

        Thread readerfive= new Thread(() -> {
//            int findId = ThreadLocalRandom.current().nextInt(1000);
            while (true) {
                int findId = 5;
                Title title = TitlesCache.INSTANCE.findById(findId);
                System.out.println(Thread.currentThread().getName() + " findById: " + findId + " " + title);
                Quet.sleep();
            }

        });

        Thread readersix= new Thread(() -> {
            while (true) {
                int findId = 6;
                Title title = TitlesCache.INSTANCE.findById(findId);
                System.out.println(Thread.currentThread().getName() + " findById: " + findId + " " + title);
                Quet.sleep();

            }
        });

        readerOne.start();
        readerTwo.start();
        readerThree.start();
        readerfour.start();
        readerfive.start();
        readersix.start();


//        Quet.sleep();
//        Quet.sleep();
//        Quet.sleep();
//        Quet.sleep();
//        Quet.sleep();
//        Quet.sleep();
//        Quet.sleep();
//        Quet.sleep();

        Thread.sleep(1245);
        TitlesCache.INSTANCE.update();

    }
}
