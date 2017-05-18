package com.denis.learning.deathmatch.tut_2;

import java.util.*;

public class TreeMapTest {
    public static void main(String[] args) {
        Map<Integer, Integer> numbers = new TreeMap<>();

        int top = 10;

        for (int i = 0; i < top; i++)
            numbers.put(i, i);

        //System.out.println(sum(numbers.get(top), top)); NPE top is 10, index is 9

        List<Integer> integers = filterUnderThreshold(Arrays.asList(1, 2, 3, 4, 5, 6, 7), 4);
        System.out.println("integers = " + integers);

    }


    private static int sum(int a, int b) {
        return a + b;
    }


    private static List<Integer> filterUnderThreshold(final List<Integer> values, final int threshold) {
        List<Integer> returnValues = new ArrayList<>(values);
        for (int i = 0; i < returnValues.size(); i++) {
            if (returnValues.get(i) >= threshold) {
                returnValues.remove(i);
            }
        }
        return returnValues;
    }

}
