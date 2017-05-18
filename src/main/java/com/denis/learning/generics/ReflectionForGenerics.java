package com.denis.learning.generics;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ReflectionForGenerics {

    public static void main(String[] args) throws ClassNotFoundException {
        ArrayList<String> strings = new ArrayList<>();
        TypeVariable<? extends Class<? extends ArrayList>>[] typeParameters = strings.getClass().getTypeParameters();
        for (TypeVariable<? extends Class<? extends ArrayList>> typeParameter : typeParameters) {
            String name = typeParameter.getName();
            System.out.println("name = " + name);
            for (Type type : typeParameter.getBounds()) {
                System.out.println("type = " + type);
            }

        }

//        genericString();
    }

    private static void genericString() throws ClassNotFoundException {
        Class<?> k = Class.forName(Cell.class.getName());
        toString(k);
        toGenericString(k);

        System.out.println(Cell.class.toGenericString());
    }

    public static void toString(Class<?> k) {
        System.out.println(k + " (toString)");
        for (Field f : k.getDeclaredFields())
            System.out.println(f.toString());
        for (Constructor c : k.getDeclaredConstructors())
            System.out.println(c.toString());
        for (Method m : k.getDeclaredMethods())
            System.out.println(m.toString());
        System.out.println();
    }
    public static void toGenericString(Class<?> k) {
        System.out.println(k + " (toGenericString)");
        for (Field f : k.getDeclaredFields())
            System.out.println(f.toGenericString());
        for (Constructor c : k.getDeclaredConstructors())
            System.out.println(c.toGenericString());
        for (Method m : k.getDeclaredMethods())
            System.out.println(m.toGenericString());
        System.out.println();
    }

    static class Cell<E> {
        private E value;
        public Cell(E value) { this.value=value; }
        public E getValue() { return value; }
        public void setValue(E value) { this.value=value; }
        public static <T> Cell<T> copy(Cell<T> cell) {
            return new Cell<T>(cell.getValue());
        }
    }
}
