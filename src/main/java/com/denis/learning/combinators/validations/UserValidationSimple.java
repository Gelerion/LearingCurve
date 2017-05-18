package com.denis.learning.combinators.validations;

import com.denis.learning.combinators.User;

import java.util.function.Function;

public interface UserValidationSimple extends Function<User, Boolean> {

    static UserValidationSimple nameIsNotEmpty() {
        return user -> !user.name.trim().isEmpty();
    }

    static UserValidationSimple eMailContainsAtSign() {
        return user -> user.email.contains("@");
    }

    default UserValidationSimple and(UserValidationSimple other) {
        return user -> this.apply(user) && other.apply(user);
    }
}
