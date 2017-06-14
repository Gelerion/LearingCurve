package com.denis.learning.codefight.thumbtack_bot.fight_3;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

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
        int[][] res = spamClusterization(requests, ids, threshold);
        System.out.println("res = " + Arrays.deepToString(res));


    }

    //[[83, 1500], [374, 1837, 1848]]
    static int[][] spamClusterization(String[] requests, int[] ids, double threshold) {
        Message[] messages = new Message[requests.length];
        for (int i = 0; i < requests.length; i++) {
            String request = requests[i];
            int id = ids[i];
//            String[] words = request.split("[^\\w]+");
            String[] words = request.split("\\P{Alpha}+");
            Message message = new Message(id, requests.length);
            for (String word : words) {
                message.addWord(word.toLowerCase());
            }
            messages[i] = message;
        }

        int[][] result = new int[0][];

        Set<Integer> highUniqueIds = new TreeSet<>();
        Set<Integer> lowUniqueIds = new TreeSet<>();

        //cartesian
        for (Message current : messages) {
            for (Message tested : messages) {
                if (current.id == tested.id) continue;
                int intersections = current.intersections(tested);
                int distincts = current.distincts(tested);
                double jaccardSim = (double) intersections / distincts;
                if(jaccardSim > threshold) {
                    highUniqueIds.add(current.id);
                    highUniqueIds.add(tested.id);
                }
                else if(jaccardSim == threshold) {
                    lowUniqueIds.add(current.id);
                    lowUniqueIds.add(tested.id);
                }
            }
        }

        System.out.println("highUniqueIds = " + highUniqueIds);
        System.out.println("lowUniqueIds = " + lowUniqueIds);

        if(!lowUniqueIds.isEmpty() && !highUniqueIds.isEmpty()) {
            result = new int[2][];

            int a = 0;
            result[0] = new int[lowUniqueIds.size()];
            for (Integer id : lowUniqueIds) {
                result[0][a++] = id;
            }

            a = 0;
            result[1] = new int[highUniqueIds.size()];
            for (Integer id : highUniqueIds) {
                result[1][a++] = id;
            }
        }
        else {

            if(!lowUniqueIds.isEmpty()) {
                result = new int[1][];
                int a = 0;
                result[0] = new int[lowUniqueIds.size()];
                for (Integer id : lowUniqueIds) {
                    result[0][a++] = id;
                }
            }
            else if(!highUniqueIds.isEmpty()) {
                result = new int[1][];
                int a = 0;
                result[1] = new int[highUniqueIds.size()];
                for (Integer id : highUniqueIds) {
                    result[1][a++] = id;
                }
            }
        }

        return result;
    }

    public static class Message implements Iterable<String> {
        private int id;
        private String[] wordsSet;
        private int size;

        Message(int id, int length) {
            this.id = id;
            this.size = 0;
            this.wordsSet = new String[length];
        }

        Message addWord(String word) {
            if(!contains(word)) doAdd(word);
            return this;
        }

        int intersections(Message other) {
            int result = 0;
            for (String otherWord : other) {
                if (this.contains(otherWord)) {
                    result++;
                }
            }

            return result;
        }

        int distincts(Message other) {
            int result = this.size + other.size;
            for (String otherWord : other) {
                if (this.contains(otherWord)) {
                    result--;
                }
            }

            return result;
        }

        private void doAdd(String word) {
            if (wordsSet.length == size) {
                wordsSet = Arrays.copyOf(wordsSet, wordsSet.length + 3);
            }
            wordsSet[size++] = word;
        }

        //works very efficient on small arrays, prefetching it's your time
        boolean contains(String elem) {
            for (int i = 0; i < size; i++) {
                if (wordsSet[i].equals(elem)) return true;
            }

            return false;
        }

        @Override
        public Iterator<String> iterator() {
            return new Iterator<String>() {
                int curr = 0;

                @Override
                public boolean hasNext() {
                    return curr < size;
                }

                @Override
                public String next() {
                    return wordsSet[curr++];
                }
            };
        }
    }
}
