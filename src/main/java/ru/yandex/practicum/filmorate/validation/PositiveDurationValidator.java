package ru.yandex.practicum.filmorate.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.time.Duration;

public class PositiveDurationValidator implements ConstraintValidator<DurationPositive, Duration> {
    @Override
    public void initialize(DurationPositive constraintAnnotation) {
    }

    @Override
    public boolean isValid(Duration value, ConstraintValidatorContext context) {
        return value != null && !value.isNegative();
    }
}