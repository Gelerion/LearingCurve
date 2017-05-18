package com.denis.learning.generics.patterns.visitor.v2;

public abstract class TreeV2<E> {
    public interface Visitor<E, R> {
        R leaf(E elt);
        R branch(R left, R right);
    }

    public abstract <R> R visit(Visitor<E, R> v);

    public static <E> TreeV2<E> leaf(E elt) {

        return new TreeV2<E>() {
            @Override
            public <R> R visit(Visitor<E, R> v) {
                return v.leaf(elt);
            }
        };
    }

    public static <E> TreeV2<E> branch(TreeV2<E> left, TreeV2<E> right) {
        return new TreeV2<E>() {
            @Override
            public <R> R visit(Visitor<E, R> v) {
                return v.branch(left.visit(v), right.visit(v));
            }
        };
    }
}
