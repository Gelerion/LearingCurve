package com.denis.learning.patterns.visitor;

public interface Visitable {
    void accept(Visitor visitor);
}
