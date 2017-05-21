package com.denis.learning.codefight.thumbtack_bot.fight_3;

import java.util.Arrays;

public class ClusterizeSpam {
    public static void main(String[] args) {
        String[] requests = {
                "I need a new window.",
                "I really, really want to replace my window!",
                "Replace my window.",
                "I want a new window.",
                "I want a new carpet, I want a new carpet, I want a new carpet.",
                "Replace my carpet"
        };

        int[] ids = {374, 2845, 83, 1848, 1837, 1500};
        double threshold = 0.5;
        spamClusterization(requests, ids, threshold);


    }

    //[[83, 1500], [374, 1837, 1848]]
    static int[][] spamClusterization(String[] requests, int[] ids, double threshold) {
        for (String request : requests) {
            String[] words = request.split("\\s+");
            String[] wordsSet = new String[words.length];
            for (String word : words) {
                word.toLowerCase();
            }
        }


        String[] strings = Arrays.stream(requests)
                .map(req -> req.split("\\s+"))
                .map(words -> Arrays.stream(words).map(String::toLowerCase).distinct())
                .toArray(String[]::new);

        System.out.println("strings = " + Arrays.toString(strings));
        return new int[0][];
    }

    //works very efficient on small arrays, branch prediction it's your time
    static boolean contains(String[] arr, String elem) {
        for (String e : arr) {
            if (e.equals(elem)) return true;
        }

        return false;
    }
}
