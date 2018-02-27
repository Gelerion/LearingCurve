package com.denis.learning.ddd.value.objects;

/**
 * Created by denis.shuvalov on 21/02/2018.
 */
public class Price {
    public Price(int amount, Currency currency) {

    }

    public Price minus(Price sellingPrice) {
        return null;
    }

    public boolean isGreaterThanZero() {
        return true;
    }

    public Currency currency() {
        return null;
    }
}
