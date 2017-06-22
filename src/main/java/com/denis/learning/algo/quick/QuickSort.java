package com.denis.learning.algo.quick;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class QuickSort {

    public static void main(String[] args) {
        int[] array = IntStream.generate(() -> ThreadLocalRandom.current()
                .nextInt(100)).distinct().limit(20).toArray();
        //System.out.println("array = " + Arrays.toString(array));
        // Collections.shuffle(); - Do not relay on input order
        int[] tmp = {34, 54, 20, 21, 47, 37, 41, 94, 57, 9, 4, 66, 77, 56, 40, 93, 97, 16, 25, 67};
        sort(tmp, 0, tmp.length - 1);
        System.out.println("sorted = " + Arrays.toString(tmp));
    }

    private static void sort(int[] array, int lo, int hi) {
        if(hi <= lo) return;
        //some element, that leftmost elements are smaller or equally and rightmost higher
        int pivot = partition(array, lo, hi);
        sort(array, lo, pivot - 1);
        sort(array, pivot + 1, hi);
    }

    private static int partition(int[] array, int lo, int hi) {
        int argHi = hi + 1;
        int argLo = lo;
        int pivot = lo; //take lo as baseline element
        while (true) {
            //look from left part to find higher or equal to pivot element
            while (less(array[++argLo], array[pivot])) if(argLo == hi) break;
            //look from right part to find lower or equal to pivot element
            while (less(array[pivot], array[--argHi])) if(argHi == lo) break;

            if(argLo >= argHi) break;
            exch(array, argLo, argHi);
        }

        exch(array, pivot, argHi);
        return argHi; //new pivot after exchange
    }

    private static void exch(int[] array, int from, int to) {
        int tmp = array[to];
        array[to] = array[from];
        array[from] = tmp;
    }

    private static boolean less(int a, int b) {
        return a < b;
    }

}
