package com.denis.learning.deathmatch.tut_1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MyClass {
    private int num = 0;

    public static void main(String args[]) {
        MyClass m1 = new MyClass();

        System.out.print(m1.strToInt("10")); //10
        System.out.println(m1.strToInt(m1.num + "1")); //101

        System.out.println();
        //-----------------------------------------------------
        Map<String, Object> collection = new TreeMap<>();

        System.out.println(collection.compute("foo", (k, v) -> (v == null) ? new ArrayList<Object>() : ((List) v).add("bar")));
        System.out.println(collection.compute("foo", (k, v) -> (v == null) ? new ArrayList<Object>() : ((List) v).add("ber")));
    }

    public int strToInt(String num) {
        this.num = Integer.parseInt(num);
        return this.num;
    }
}