package com.denis.learning.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

public class CaffeineTest {
    public static void main(String[] args) {
        Cache<String, String> cache = Caffeine.newBuilder().build();
        cache.asMap();
    }
}
