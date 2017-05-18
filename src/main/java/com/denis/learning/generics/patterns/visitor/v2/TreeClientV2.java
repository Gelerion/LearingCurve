package com.denis.learning.generics.patterns.visitor.v2;

public class TreeClientV2 {
    public static void main(String[] args) {

    }

    static <T> String toString(TreeV2<T> t) {
        return t.visit(new TreeV2.Visitor<T, String>() {
            @Override
            public String leaf(T elt) {
                return elt.toString();
            }

            @Override
            public String branch(String left, String right) {
                return  "(" + left + "^" + right + ")";
            }
        });
    }
}
