package com.denis.learning.generics.patterns.visitor.v1;

public abstract class TreeV1<E> {
    public abstract String toString();
    public abstract Double sum();

    public static <E> TreeV1<E> leaf(E elt) {
        return new TreeV1<E>() {
            @Override
            public String toString() {
                return elt.toString();
            }

            @Override
            public Double sum() {
                return ((Number)elt).doubleValue();
            }
        };
    }

    public static <E> TreeV1<E> branch(TreeV1<E> left, TreeV1<E> right) {
        return new TreeV1<E>() {
            @Override
            public String toString() {
                return "(" + left.toString() + "^" + right.toString() + ")";
            }

            @Override
            public Double sum() {
                return left.sum() + right.sum();
            }
        };
    }


    public static void main(String[] args) {
        TreeV1<Integer> tree =
                TreeV1.branch(
                        TreeV1.branch(TreeV1.leaf(1), TreeV1.leaf(2)),
                        TreeV1.leaf(3)
        );

        assert tree.toString().equals("((1^2)^3)");
        assert tree.sum() == 6;
        System.out.println("tree = " + tree);
    }
}
