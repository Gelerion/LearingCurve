package com.denis.learning.threads;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public enum TitlesCache {

    INSTANCE;

    private final Map<Integer, Title> cache = new LinkedHashMap<>();

    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();

    public void fillCahce() {
        cache.put(1, new Title(1));
        cache.put(2, new Title(2));
        cache.put(3, new Title(3));
    }

    public Title findById(Integer id) {
        return cache.get(id);
    }

    public void update() {
        System.out.println(Thread.currentThread() + " UPDATE CALLED");
        System.out.println("REMOVE 2");
        cache.remove(2);
        System.out.println("ADD 4, 5, 6");
        cache.put(4, new Title(4));
        cache.put(5, new Title(5));
        cache.put(6, new Title(6));
    }

    public void updateRandom() {
        System.out.println("UPDATE CALLED");
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            int id = random.nextInt(1000);
            System.out.println("ADD ID: " + id);
            cache.put(id, new Title(id));
        }

        for (int i = 0; i < 1000; i++) {
            Title title = cache.get(i);
            if(title != null) {
                System.out.println("REMOVING: " + i);
                cache.remove(1);
                break;
            }
        }
    }
}
