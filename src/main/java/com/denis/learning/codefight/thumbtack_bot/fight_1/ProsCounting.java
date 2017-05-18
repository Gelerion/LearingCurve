package com.denis.learning.codefight.thumbtack_bot.fight_1;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ProsCounting {

    public static void main(String[] args) {
		int[][] a = new int[][] { { 3, 4 }, { 3, 3, 3, 4 }, { 4 } };
        int[] ints = ratingThreshold(3.5, a);
//        int[][] a = new int[][] { { 1, 1, 2 }, { 1, 2, 2 }, { 1, 4 } };
//        int[] ints = ratingThreshold(1.3, a);
        System.out.println("ints = " + Arrays.toString(ints));
    }

    static int[] ratingThreshold(double threshold, int[][] ratings) {
        int[] result = new int[0];
        int elems = 0;
        int i = 0;
        for (int[] rating : ratings) {
            int total = rating.length;
            int sum = 0;
            for (int rate : rating) {
                sum += rate;
            }

            double thresh = (double) sum / total;
            if(thresh < threshold) {
                if(result.length == elems) {
                    result= Arrays.copyOf(result, result.length + 1);
                }
                result[elems++] = i;
            }

            i++;
        }

        return result;
    }
}
