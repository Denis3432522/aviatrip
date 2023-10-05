package com.example.aviatrip.config.validation.annotation;

import com.example.aviatrip.config.validation.validator.FutureDateLimitValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.TYPE_USE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FutureDateLimitValidator.class)
@ReportAsSingleViolation
@Documented
public @interface FutureDateLimit {

    int dayLimit() default 365;
    String message() default "incorrect value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

