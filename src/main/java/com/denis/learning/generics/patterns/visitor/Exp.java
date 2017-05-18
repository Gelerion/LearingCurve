package com.denis.learning.generics.patterns.visitor;

public abstract class Exp<T> {
    abstract public T eval();

    static Exp<Integer> lit(final int i) {
        return new Exp<Integer>() {
            @Override
            public Integer eval() {
                return i;
            }
        };
    }

    static Exp<Integer> plus(final Exp<Integer> e1, final Exp<Integer> e2) {
        return new Exp<Integer>() {
            @Override
            public Integer eval() {
                return e1.eval() + e2.eval();
            }
        };
    }

    static <A, B> Exp<Pair<A, B>> pair(final Exp<A> e1, final Exp<B> e2) {
        return new Exp<Pair<A, B>>() {
            public Pair<A, B> eval() {
                return new Pair<A, B>(e1.eval(), e2.eval());
            }
        };
    }

    static <A, B> Exp<A> left(final Exp<Pair<A, B>> e) {
        return new Exp<A>() {
            public A eval() { return e.eval().getLeft(); }
        };
    }

    static <A, B> Exp<B> right(final Exp<Pair<A, B>> e) {
        return new Exp<B>() {
            public B eval() { return e.eval().getRight(); }
        };
    }

    public static void main(String[] args) {
        Exp<Integer> e = left(pair(plus(lit(3), lit(4)), lit(5)));
        assert e.eval() == 7;
    }

}
