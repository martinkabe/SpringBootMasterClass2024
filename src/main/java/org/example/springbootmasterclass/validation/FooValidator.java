package org.example.springbootmasterclass.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FooValidator implements ConstraintValidator<Foo, String> {
    private String message;

    @Override
    public void initialize(Foo constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank() || value.equalsIgnoreCase("Foo")) {
            // Disable the default error message
            context.disableDefaultConstraintViolation();

            // Add custom validation message to the context
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
            return false;  // Indicate that the value is not valid
        }
        return true;  // Valid value
    }
}
