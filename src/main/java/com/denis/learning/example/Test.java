package com.denis.learning.example;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Test {
    public static void main(String[] args) {
        List<ConfigurableSegmentFilter<? super Number>> result = new ArrayList<>();
        ConfigurableSegmentFilter<?> newFilter = new JinniFilter();
        ConfigurableSegmentFilter<Number> newFilter2 = new ExelateFilter();
        ConfigurableSegmentFilter newFilter3 = new ExelateFilter();
//        SelfConfigurable configure = newFilter.configure("ade");


        ConfigurableSegmentFilter<?> a = newFilter.configure("a");

        ConfigurableSegmentFilter<?> configure = newFilter.configure("");
        ConfigurableSegmentFilter a1 = newFilter3.configure("a");
//        reflect(newFilter3);
//        result.addTokenInfo(newFilter);
        result.add(newFilter2);

        List<Integer> ints = new ArrayList<>();
        List<? extends Integer> regular = new ArrayList<>();
        regular = ints;

        List<Comparable<Integer>> comparables = new ArrayList<>();
        List<Comparable<? extends Integer>> aws = new ArrayList<>();
//        aws = comparables;

        List<?> something = new ArrayList<>();
        something = ints;
        something = comparables;
        something.add(null);
    }

    static void reflect(Object a) {
        Class<?> current = a.getClass();

        Method[] declaredMethods = current.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            System.out.println("declaredMethod = " + declaredMethod);
        }

//        System.out.println("a.getClass() = " + a.getClass());
        Class<?> superclass = a.getClass().getSuperclass();
//        System.out.println("superclass = " + superclass);
        while (superclass != null) {
            if (superclass != Object.class) {
                for (Method declaredMethod : superclass.getDeclaredMethods()) {
                    System.out.println("declaredMethod = " + declaredMethod);
                }
            }
            superclass = superclass.getSuperclass();
        }

//        Field[] declaredFields = a.getClass().getDeclaredFields();
//        for (Field field : declaredFields) {
//            System.out.println("field = " + field);
//        }
    }
}
