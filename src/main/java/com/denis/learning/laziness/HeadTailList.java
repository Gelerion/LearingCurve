package com.denis.learning.laziness;

import java.util.function.Predicate;

/**
 * Created by denis.shuvalov on 04/02/2018.
 */
public interface HeadTailList<T> {

    T head();

    HeadTailList<T> tail();

    HeadTailList<T> filter(Predicate<T> predicate);

    boolean isEmpty();

}
