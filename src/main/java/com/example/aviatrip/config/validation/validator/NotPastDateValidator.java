package com.example.aviatrip.config.validation.validator;

import com.example.aviatrip.config.validation.annotation.NotPastDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.ZoneId;

public class NotPastDateValidator implements ConstraintValidator<NotPastDate, LocalDate> {
    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate currentDate = LocalDate.now(ZoneId.of("UTC"));

        if(currentDate.getYear() == date.getYear())
            return currentDate.getDayOfYear() <= date.getDayOfYear();

        return currentDate.getYear() < date.getYear();
    }

}
