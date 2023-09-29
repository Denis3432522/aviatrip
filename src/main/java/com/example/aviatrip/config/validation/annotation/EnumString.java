package com.example.aviatrip.config.validation.annotation;

import com.example.aviatrip.config.validation.validator.EnumStringValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.TYPE_USE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumStringValidator.class)
@ReportAsSingleViolation
@Documented
public @interface EnumString {
    Class<? extends Enum<?>> enumClazz();
    String propertyName() default "";
    String message() default "value doesn't exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
