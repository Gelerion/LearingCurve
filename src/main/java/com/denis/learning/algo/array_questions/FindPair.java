package com.denis.learning.algo.array_questions;

import java.util.HashMap;
import java.util.Map;

/**
 * Given an unsorted array of integers, find a pair with given sum in it
 * 1 - sort then [min ... high]
 * 2 - this linear
 */
public class FindPair {

    static void findPair(int arr[], int sum) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            // check if pair (arr[i], sum-arr[i]) exists
            // if difference is seen before, print the pair
            if(map.containsKey(sum - arr[i])) {
                System.out.println("Pair found at index " + map.get(sum - arr[i]) + " and " + i);
                return;
            }

            map.put(arr[i], i);
        }

        System.out.println("Pair not found");
    }

    // main function
    public static void main (String[] args)
    {
        int arr[] = { 8, 7, 2, 5, 3, 1 };
        int sum = 10;

        findPair(arr, sum);
    }
}
