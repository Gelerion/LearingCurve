package com.denis.learning.oop.lsp;

import java.util.ArrayList;
import java.util.List;

public class DoubleList<T> extends ArrayList<T> {
    private List<T> innerList = new ArrayList<>();

    @Override
    public boolean add(T value) {
        innerList.add(value);
        innerList.add(value);
        return true;
    }

    @Override
    public int size() {
        return innerList.size();
    }
}
