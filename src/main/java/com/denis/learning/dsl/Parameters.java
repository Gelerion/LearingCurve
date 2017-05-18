package com.denis.learning.dsl;

public interface Parameters<T> extends NewableConsumer<T> {
    default T get() {
        T t = newInstance(); // provided by Newable<T>
        accept(t); // apply our config
        return t; // return the config ready to use
    }
}
