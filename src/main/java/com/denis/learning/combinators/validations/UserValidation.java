package com.denis.learning.combinators.validations;

import com.denis.learning.combinators.User;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.denis.learning.combinators.validations.ValidationResult.invalid;
import static com.denis.learning.combinators.validations.ValidationResult.valid;

public interface UserValidation extends Function<User, ValidationResult> {

    static UserValidation nameIsNotEmpty() {
        return holds(user -> !user.name.trim().isEmpty(), "Name is empty.");
    }

    static UserValidation eMailContainsAtSign() {
        return holds(user -> user.email.contains("@"), "Missing @-sign.");
    }

    static UserValidation holds(Predicate<User> predicate, String message) {
        return user -> predicate.test(user) ?
                valid() :
                invalid(message);
    }

    static UserValidation all(UserValidation... validations) {
        return user -> {
            String reasons = Arrays.stream(validations)
                    .map(validation -> validation.apply(user))
                    .filter(result -> !result.isValid())
                    .map(ValidationResult::getReason)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.joining("\n"));

            return reasons.isEmpty() ? ValidationResult.valid() : ValidationResult.invalid(reasons);
        };
    }

    default UserValidation and(UserValidation other) {
        return user -> {
            final ValidationResult result = this.apply(user);
            return result.isValid() ? other.apply(user) : result;
        };
    }
}
