package com.denis.learning.dsl;

import java.util.function.Consumer;

public interface NewableConsumer<T> extends Consumer<T>, Newable<T> {
    default Consumer<T> consumer() {
        return this;
    }
}
