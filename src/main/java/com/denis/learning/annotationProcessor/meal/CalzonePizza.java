package com.denis.learning.annotationProcessor.meal;

import com.denis.learning.annotationProcessor.prepare.Factory;

@Factory(
        id = "Calzone",
        type = Meal.class
)
public class CalzonePizza implements Meal {

    @Override public float getPrice() {
        return 8.5f;
    }
}
