package com.vesalukkarila.web.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to validate that a year falls within a specified range.
 */
@Constraint(validatedBy = YearRangeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface YearRange {
    /**
     * Error message to be returned when validation fails.
     *
     * @return the default error message
     */
    String message() default "Year must be between 1889 and the current year.";

    /**
     * Groups for the validation.
     *
     * @return the groups that this constraint belongs to
     */
    Class<?>[] groups() default {};

    /**
     * Payload for additional data to be carried with the annotation.
     *
     * @return the payload
     */
    Class<? extends Payload>[] payload() default {};
}
