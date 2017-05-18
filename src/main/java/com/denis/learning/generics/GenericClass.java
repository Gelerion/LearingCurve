package com.denis.learning.generics;

import java.util.ArrayList;
import java.util.List;

public class GenericClass {
    public static void main(String[] args) {
        Number num = new Integer(42);
        //expression: ? extends
        Class<? extends Number> aClass = num.getClass(); //actual type of num is int
        Class<Number> numberClass = Number.class;

        List<Integer> ints = new ArrayList<Integer>();
        List<String> strs = new ArrayList<String>();

        Class<? extends List> aClass1 = ints.getClass();
        Class<? extends List> aClass2 = strs.getClass();

        System.out.println(ints.getClass() == strs.getClass()); //true
        System.out.println(ints.getClass() == ArrayList.class); //true

        try {
            Integer integer = int.class.newInstance(); //InstantiationException: int
        }
        catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
