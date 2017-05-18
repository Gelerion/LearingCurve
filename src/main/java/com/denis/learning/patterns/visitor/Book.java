package com.denis.learning.patterns.visitor;

public class Book implements Visitable {
    private double price;
    private double weight;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public double getPrice() {
        return price;
    }
    public double getWeight() {
        return weight;
    }
}
