package com.example.aviatrip.config.validation.validator;

import com.example.aviatrip.config.validation.annotation.EnumString;
import com.example.aviatrip.config.validation.annotation.FutureDateLimit;
import com.example.aviatrip.config.validation.annotation.NotPastDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

public class FutureDateLimitValidator implements ConstraintValidator<FutureDateLimit, Temporal> {

    private int dayLimit;
    @Override
    public void initialize(FutureDateLimit constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);

        if(constraintAnnotation.dayLimit() < 1)
            throw new IllegalArgumentException("must be at least 1 day");

        dayLimit = constraintAnnotation.dayLimit();
    }
    @Override
    public boolean isValid(Temporal date, ConstraintValidatorContext constraintValidatorContext) {
        long durationInDays = ChronoUnit.DAYS.between(LocalDate.now(ZoneId.of("UTC")), date);

        if(durationInDays < dayLimit)
            return true;

        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate("must not be more than " + dayLimit + " days in a future").addConstraintViolation();

        return false;
    }

}