package com.vesalukkarila.web.validation;

import com.vesalukkarila.web.validation.YearRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

public class YearRangeValidator implements ConstraintValidator<YearRange, Integer> {

    @Override
    public void initialize(YearRange constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null){
            return true;
        }
        int currentYear = Year.now().getValue();
        return value >= 1889 && value <= currentYear;
    }
}
