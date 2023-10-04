package com.example.aviatrip.config.validation.annotation;

import com.example.aviatrip.config.validation.validator.EnumStringValidator;
import com.example.aviatrip.config.validation.validator.FutureDateLimitValidator;
import com.example.aviatrip.config.validation.validator.FutureTimeOffsetValidator;
import com.example.aviatrip.config.validation.validator.NotPastDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.TYPE_USE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FutureTimeOffsetValidator.class)
@ReportAsSingleViolation
@Documented
public @interface FutureTimeOffset {

    int hourOffset() default 24;
    String message() default "incorrect value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

