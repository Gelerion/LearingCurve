package com.denis.learning.annotationProcessor.meal;

import com.denis.learning.annotationProcessor.prepare.Factory;

@Factory(
        id = "Tiramisu",
        type = Meal.class
)
public class Tiramisu implements Meal {

    @Override public float getPrice() {
        return 4.5f;
    }
}
