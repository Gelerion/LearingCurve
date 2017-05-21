package com.denis.learning.codefight.thumbtack_bot.fight_2;

import java.util.*;

public class ProfPreferences {
    public static void main(String[] args) {
        String[] pros = {"Jack", "Leon", "Maria"};
        String[][] preferences = {
                {"Computer repair", "Handyman", "House cleaning"},
                {"Computer lessons", "Computer repair", "Data recovery service"},
                {"Computer lessons", "House cleaning"}
        };

        String[][][] strings = proCategorization(pros, preferences);

    }

    /*
    [
        [
            ["Computer lessons"],["Leon", "Maria"]
        ],
        [
            ["Computer repair"], ["Jack", "Leon"]
        ],
        [["Data recovery service"], ["Leon"]],
        [["Handyman"], ["Jack"]],
        [["House cleaning"], ["Jack", "Maria"]]
     ]
     */
    static String[][][] proCategorization(String[] pros, String[][] preferences) {
        Map<String, Set<String>> holder = new TreeMap<>();

        for (int i = 0; i < pros.length; i++) {
            String current = pros[i];
            String[] profPrefers = preferences[i];

            for (String profPref : profPrefers) {
                holder.compute(profPref, (k, v) -> {
                    if (v == null) v = new TreeSet<>();
                    v.add(current);
                    return v;
                });
            }
        }

        if (holder.size() == 0) {
            return new String[0][0][0];
        }


        String[][][] result = new String[holder.keySet().size()][2][1];
        int i = 0;
        for (Map.Entry<String, Set<String>> entry : holder.entrySet()) {
            int j = 0;
            result[i][j++] = new String[] { entry.getKey() };
            result[i][j] = entry.getValue().toArray(new String[entry.getValue().size()]);
            i++;
        }


        String[][] first = result[0];
        System.out.println("first = " + Arrays.deepToString(first));
        String[] inner = first[0];
        System.out.println("inner = " + Arrays.toString(inner));

        String[] pref = result[0][0];
        System.out.println("pref = " + Arrays.toString(pref));
        String[] profs = result[0][1];
        System.out.println("profs = " + Arrays.toString(profs));
        //        for (String[][] strings : result) {
//            System.out.println("strings = " + Arrays.deepToString(strings));
//        }

        System.out.println("result = " + Arrays.deepToString(result));

        return result;
    }

/*    static String[][][] proCategorization2(String[] pros, String[][] preferences) {
        Map<String, String> holder = new TreeMap<>();

        for (int i = 0; i < pros.length; i++) {
            String current = pros[i];
            String[] profPrefers = preferences[i];

            for (String profPref : profPrefers) {
                if(holder.containsKey(profPref)) {
                    String value = holder.get(profPref);
                    String newValue = String.join(",", value, current);
                    holder.put(profPref, newValue);
                }
                else {
                    if(current == null) continue;
                    System.out.println("current = " + current);
                }
                holder.compute(profPref, (k, v) -> v == null ? current : String.join(",", v, current));
            }
        }

        if (holder.size() == 0) {
            return new String[0][0][0];
        }


        String[][][] result = new String[holder.keySet().size()][2][2];
        int i = 0;
        for (Map.Entry<String, String> entry : holder.entrySet()) {
            int prefIn = 0;
            result[i][prefIn++][0] = entry.getKey();

            int profIn = 0;


            String[] profs = entry.getValue().split(",");
            Arrays.sort(profs);
            for (String prof : profs) {
                result[i][prefIn][profIn++] = prof;
            }
            i++;
        }

        return result;
    }*/


}
