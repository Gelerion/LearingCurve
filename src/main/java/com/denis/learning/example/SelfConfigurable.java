package com.denis.learning.example;

public interface SelfConfigurable<E extends SelfConfigurable<E>> {
    E configure(String settings);
}
