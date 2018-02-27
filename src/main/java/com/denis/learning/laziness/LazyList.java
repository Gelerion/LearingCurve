package com.denis.learning.laziness;

import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by denis.shuvalov on 04/02/2018.
 */
public class LazyList<T> implements HeadTailList<T> {

    private final T head;
    private final Supplier<HeadTailList<T>> tail;

    LazyList(T head, Supplier<HeadTailList<T>> tail) {
        this.head = head;
        this.tail = tail;
    }

    @Override
    public T head() {
        return head;
    }

    @Override
    public HeadTailList<T> tail() {
        return tail.get();
    }

    @Override
    public HeadTailList<T> filter(Predicate<T> predicate) {
        return isEmpty() ?
                this :
                predicate.test(head) ? new LazyList<>(head(), () -> tail().filter(predicate)) :
                tail().filter(predicate);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
