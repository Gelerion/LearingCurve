package com.denis.learning.combinators.validations;

import java.util.Optional;

public class ValidationSupport {
    public static final ValidationResult valid = new ValidationResult() {
        public boolean isValid() { return true; }
        public Optional<String> getReason() { return Optional.empty(); }
    };

    public static ValidationResult valid() {
        // since all valid results are indistinguishable,
        // the same instance can be reused to reduce garbage
        return valid;
    }
}
