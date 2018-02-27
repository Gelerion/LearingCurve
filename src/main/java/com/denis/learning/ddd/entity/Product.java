package com.denis.learning.ddd.entity;

import com.denis.learning.ddd.value.objects.Option;
import com.denis.learning.ddd.value.objects.Price;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denis.shuvalov on 21/02/2018.
 * <p>
 * the product entity encapsulates data and exposes behavior;
 * the data of the class is hidden.
 */
public class Product extends Entity<String> {

    private List<Option> options;
    private Price sellingPrice;
    private Price retailPrice;

    public Product(String id, Price sellingPrice, Price retailPrice) {
        super(id);
        this.sellingPrice = sellingPrice;
        if (!sellingPriceMatches(retailPrice)) {
            throw new RuntimeException("Selling and retail price must be in the same currency");
        }

        this.retailPrice = retailPrice;
        this.options = new ArrayList<>();
    }

    public void changePriceTo(Price newPrice) {
        if (!sellingPriceMatches(newPrice)) {
            throw new RuntimeException("You cannot change the price of this product to a different currency");
        }
        sellingPrice = newPrice;
    }

    public Price savings() {
        Price savings = retailPrice.minus(sellingPrice);
        if (savings.isGreaterThanZero()) {
            return savings;
        } else {
            return new Price(0, sellingPrice.currency());
        }
    }

    public void add(Option option) {
        if (!options.contains(option)) {
            options.add(option);
        } else {
            throw new RuntimeException("This product already has the option" + option);
        }
    }

    private boolean sellingPriceMatches(Price retailPrice) {
        return sellingPrice.equals(retailPrice);
    }
}
