package com.denis.learning.generics;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class GenericArrayTest {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        List<Integer>[] intLists = (List<Integer>[]) new List[]{Arrays.asList(1)}; //unchecked cast
        List<? extends Number>[] numLists = intLists;
        numLists[0] = Arrays.asList(1.01);
        int i = intLists[0].get(0);


        List<Integer> a = Arrays.asList(1, 2, 3);
        List<Integer> b = Arrays.asList(4, 5, 6);
        List<List<Integer>> x = Arrays.asList(a, b); // generic array creation
        //same as
/*        List<Integer> a = Arrays.asList(new Integer[] { 1, 2, 3 });
        List<Integer> b = Arrays.asList(new Integer[] { 4, 5, 6 });
        List<List<Integer>> x = Arrays.asList(new List<Integer>[] { a, b }); // generic array creation*/


//        createArray()

    }

    private static void createArray() {
        List<String> strings = Arrays.asList("one", "two");
        String[] a = array(strings, new String[0]);
        assert Arrays.toString(a).equals("[one, two]");
        System.out.println("a = " + Arrays.toString(a));
        String[] b = new String[] { "x","x","x","x" };
        array(strings, b);
        System.out.println("b = " + Arrays.toString(b));
        assert Arrays.toString(b).equals("[one, two, null, x]");
    }

    @SuppressWarnings("unchecked")
    static <T> T[] array(Collection<T> c, T[] a) {
        if(a.length < c.size()) {
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), c.size());
        }

        int i = 0;
        for (T t : c) {
            a[i++] = t;
        }

        if (i < a.length) a[i] = null; //mark end of the collection
        return a;
    }
}
