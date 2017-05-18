package com.denis.learning.algo.array_questions;

import java.util.Arrays;

/**
 * Given an binary array, sort it in linear time and constant space.
 * Output should print contain all zeroes followed by all ones.
 *
 * Input: { 1, 0, 1, 0, 1, 0, 0, 1 }
 * Output: { 0, 0, 0, 0, 1, 1, 1, 1 }
 */
public class SortBinaryArray {

    /**
     * Instead of counting number of zeroes, if the current element is 0, we can place 0 at next
     * available position in the array. After all elements in the array are processed, we fill all remaining indexes by 1.
     */

    static void sort(int[] binary) {
        int k = 0;

        for (int i = 0; i < binary.length; i++) {
            if (binary[i] == 0) {
                binary[k++] = 0;
            }
        }

        for (int i = k; i < binary.length; i++) {
            binary[i] = 1;
        }

        System.out.println("Sorted: " + Arrays.toString(binary));
    }

    public static void main(String[] args) {
        int a[] = { 0, 0, 1, 0, 1, 1, 0, 1, 0, 0 };
        sort(a);
    }
}
