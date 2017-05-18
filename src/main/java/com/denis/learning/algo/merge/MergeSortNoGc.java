package com.denis.learning.algo.merge;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class MergeSortNoGc {
    public static void main(String[] args) {
        int[] array = IntStream.generate(() -> ThreadLocalRandom.current()
                .nextInt(50)).distinct().limit(10).toArray();

        System.out.println(Arrays.toString(mergeSort(array)));
    }

    private static int[] mergeSort(int[] array) {
        int[] aux = array.clone();
        topDownSplit(aux, 0, array.length, array); //sort data from aux[] into array[]
        return array;
    }

    private static void topDownSplit(int[] aux, int startIndex, int endIndex, int[] original) {
        if(endIndex - startIndex < 2) return;

        int mid = (startIndex + endIndex) >>> 1;

        topDownSplit(original, startIndex, mid, aux); // sort the left  run
        topDownSplit(original, mid, endIndex, aux);   // sort the right run

        merge(aux, startIndex, mid, endIndex, original);
    }

    private static void merge(int[] aux, int startIndex, int mid, int endIndex, int[] original) {
        int i = startIndex;
        int j = mid;

        // While there are elements in the left or right runs...
        for (int k = startIndex; k < endIndex; k++) {
            // If left run head exists and is <= existing right run head.
            if (i < mid && (j >= endIndex || aux[i] <= aux[j])) {
                original[k] = aux[i];
                i++;
            }
            else {
                original[k] = aux[j];
                j++;
            }
        }
    }
}
