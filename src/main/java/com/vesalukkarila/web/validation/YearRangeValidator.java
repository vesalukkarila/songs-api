package com.vesalukkarila.web.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

/**
 * Validator for the YearRange annotation.
 */
public class YearRangeValidator implements ConstraintValidator<YearRange, Integer> {

    /**
     * Initializes the validator in preparation for validation.
     * This method is empty as no initialization is required for this validator.
     *
     * @param constraintAnnotation the annotation instance for a given
     *                             constraint declaration
     */
    @Override
    public void initialize(YearRange constraintAnnotation) {
    }

    /**
     * Validates that the given year is within the acceptable range.
     *
     * @param value the year to validate (can be null)
     * @param context the context in which the constraint is evaluated
     * @return {@code true} if the year is between 1889 and the current year,
     *         or if the value is null; {@code false} otherwise
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null values are considered valid due to patch validation
        }
        int currentYear = Year.now().getValue();
        return value >= 1889 && value <= currentYear;
    }
}
