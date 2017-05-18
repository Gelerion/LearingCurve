package com.denis.learning.generics;

import java.io.*;
import java.nio.CharBuffer;
import java.util.*;

public class GenericTest {

    public static void main(String[] args) throws IOException {
        ArrayList<?> a = new ArrayList<>();

        List<Fruit> fruits = new ArrayList<>();
        fruits.add(new Apple(2));
        fruits.add(new Orange(2));
        List<Orange> oranges = new ArrayList<>();

        List<Object> objects = new ArrayList<>();
        @SuppressWarnings("unchecked")
        List<String> strs = (List<String>) (List<?>) objects;

        max(oranges);

        int size = 32;
        FileReader r = new FileReader("file.in");
        FileWriter w = new FileWriter("file.out");
        copy(r,w,size);

        BufferedReader br = new BufferedReader(new FileReader("file.in"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("file.out"));
        copy(br,bw,size);
    }

    static <S extends Readable & Closeable, T extends Appendable & Closeable>
    void copy(S src, T trg, int size) throws IOException {
        try {
            CharBuffer buf = CharBuffer.allocate(size);
            int i = src.read(buf);
            while (i >= 0) {
                buf.flip(); //prepare for writing
                trg.append(buf);
                buf.clear(); //prepare for reading
                i = src.read(buf);
            }
        }
        finally {
            src.close();
            trg.close();
        }
    }

    static abstract class Fruit implements Comparable<Fruit> {
        protected String name;
        protected int size;
        protected Fruit(String name, int size) {
            this.name = name; this.size = size;
        }

        public int compareTo(Fruit that) {
            return this.size < that.size ? -1 :
                    this.size == that.size ? 0 : 1 ;
        }
    }

    static class Apple extends Fruit {
        public Apple(int size) { super("Apple", size); }
    }

    static class Orange extends Fruit  {
        public Orange(int size) { super("Orange", size); }
    }

    public static <T extends Comparable<T>> T max(Collection<? extends T> coll) {
        Iterator<? extends T> i = coll.iterator();
        T candidate = i.next();

        while (i.hasNext()) {
            T next = i.next();
            if (next.compareTo(candidate) > 0)
                candidate = next;
        }
        return candidate;
    }
}
