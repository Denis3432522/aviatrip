package com.example.aviatrip.config.validation.validator;

import com.example.aviatrip.config.validation.annotation.FutureTimeOffset;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class FutureTimeOffsetValidator implements ConstraintValidator<FutureTimeOffset, LocalDateTime> {

    private int hourOffset;
    @Override
    public void initialize(FutureTimeOffset constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);

        if(constraintAnnotation.hourOffset() < 24)
            throw new IllegalArgumentException("must be at least 24 hours");

        hourOffset = constraintAnnotation.hourOffset();
    }
    @Override
    public boolean isValid(LocalDateTime date, ConstraintValidatorContext constraintValidatorContext) {
        if(date == null) return true;

        long durationInSeconds = ChronoUnit.SECONDS.between(LocalDateTime.now(ZoneId.of("UTC")), date);

        if(durationInSeconds >= ((long) hourOffset * 60 * 60))
            return true;

        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate("must be at least " + hourOffset + " hours in a future").addConstraintViolation();

        return false;
    }

}

