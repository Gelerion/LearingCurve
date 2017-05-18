package com.denis.learning.combinators;

import com.denis.learning.combinators.validations.UserValidation;
import com.denis.learning.combinators.validations.UserValidationSimple;
import com.denis.learning.combinators.validations.ValidationResult;

public class User{
    public final String name;
    public final int age;
    public final String email;

    public User(String name, int age, String email){
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public boolean isValid(){
        return nameIsNotEmpty() && eMailContainsAtSign();
    }

    private boolean nameIsNotEmpty(){
        return !name.trim().isEmpty();
    }

    private boolean eMailContainsAtSign(){
        return email.contains("@");
    }

    public static void main(String[] args) {
        User gregor = new User("Gregor", 30, "nicemail@gmail.com");
        gregor.isValid(); // true

        //more flexible way
        UserValidationSimple.eMailContainsAtSign().and(UserValidationSimple.nameIsNotEmpty()).apply(gregor);
        /*List<User> users = findAllUsers()
                .stream().parallel()
                .filter(nameIsNotEmpty().and(eMailContainsAtSign())::apply) // to Predicate
                .collect(Collectors.toList());*/

        UserValidation validation = UserValidation.nameIsNotEmpty().and(UserValidation.eMailContainsAtSign());
        ValidationResult result = validation.apply(gregor);
        result.getReason().ifPresent(System.out::println); // Name is empty.

    }
}
