package com.denis.learning.laziness;

/**
 * Created by denis.shuvalov on 04/02/2018.
 */
public class LazyLists {

    static LazyList<Integer> from(int n) {
        return new LazyList<>(n, () -> from(n + 1));
    }

    static HeadTailList<Integer> primes(HeadTailList<Integer> numbers) {
        return new LazyList<>(numbers.head(),
                () -> primes(numbers
                        .tail()
                        .filter(n -> n % numbers.head() != 0)));
    }


    static <T> void printAll(HeadTailList<T> list) {
        if(list.isEmpty()) return;
        System.out.println(list.head());
        slowExecution();
        printAll(list.tail());
    }

    private static void slowExecution() {
        try { Thread.sleep(500); }
        catch (InterruptedException e) {e.printStackTrace();}
    }

}
