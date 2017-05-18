package com.denis.learning.annotationProcessor.prepare;

import com.denis.learning.annotationProcessor.meal.CalzonePizza;
import com.denis.learning.annotationProcessor.meal.MargheritaPizza;
import com.denis.learning.annotationProcessor.meal.Meal;
import com.denis.learning.annotationProcessor.meal.Tiramisu;

public class MealFactory {

    public Meal create(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null!");
        }
        if ("Calzone".equals(id)) {
            return new CalzonePizza();
        }

        if ("Tiramisu".equals(id)) {
            return new Tiramisu();
        }

        if ("Margherita".equals(id)) {
            return new MargheritaPizza();
        }

        throw new IllegalArgumentException("Unknown id = " + id);
    }
}
