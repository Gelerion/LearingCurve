package com.denis.learning.memorization;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class Memoizer<T, U> {
    private final Map<T, U> cache = new ConcurrentHashMap<>();

    private Function<T, U> doMemoize(final Function<T, U> function) {
        return input -> cache.computeIfAbsent(input, function);
    }

    public static <T, U> Function<T, U> memoize(final Function<T, U> function) {
        return new Memoizer<T, U>().doMemoize(function);
    }

    //many args
    public static void main(String[] args) {
        Function<Integer, Function<Integer, Integer>> mhc = Memoizer.memoize(x -> Memoizer.memoize(y -> x + y));
    }
}
