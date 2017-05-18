package com.denis.learning.annotationProcessor.meal;

import com.denis.learning.annotationProcessor.prepare.Factory;

@Factory(
        id = "Margherita",
        type = Meal.class
)
public class MargheritaPizza implements Meal {

    @Override public float getPrice() {
        return 6.0f;
    }
}
