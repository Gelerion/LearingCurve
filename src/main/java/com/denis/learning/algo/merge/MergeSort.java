package com.denis.learning.algo.merge;

import com.google.common.base.Stopwatch;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static java.lang.System.arraycopy;

public class MergeSort {

    public static void main(String[] args) {
        int[] array = IntStream.generate(() -> ThreadLocalRandom.current()
                .nextInt())/*.distinct()*/.limit(100_000_000).toArray();

        Stopwatch sw = Stopwatch.createStarted();
//        System.out.println(Arrays.toString(array));
//        System.out.println(Arrays.toString(mergeSort(array)));
        int[] res = mergeSort(array);
        if(res.length != 0) res[12]++;
        System.out.println("Time took: " + sw.stop());
    }

    private static int[] mergeSort(int[] array) {
        int size = array.length;
        if(size == 1) return array;

        int mid = size >> 1;
        int leftMid = size % 2 == 0 ? mid : mid + 1;

        int[] leftHalf  = new int[leftMid];
        int[] rightHalf = new int[mid];
        arraycopy(array, 0, leftHalf, 0, leftMid);
        arraycopy(array, leftMid, rightHalf, 0, mid);

        int[] leftHalfSorted  = mergeSort(leftHalf);
        int[] rightHalfSorted = mergeSort(rightHalf);

        return merge(leftHalfSorted, rightHalfSorted);
    }

    private static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];

        int i = 0, j = 0, c = 0;
        for (; i < left.length && j < right.length; ) {
            if (left[i] <= right[j]) result[c++] = left[i++];
            else result[c++] = right[j++];
        }

        if(j < right.length) arraycopy(right, j, result, c, right.length - j);
        else arraycopy(left, i, result, c, left.length - i);

        return result;
    }
}
