package com.example.aviatrip.config.validation.validator;

import com.example.aviatrip.config.validation.annotation.EnumString;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EnumStringValidator implements ConstraintValidator<EnumString, String> {

    private List<String> valueList;
    private String messageTemplate;

    @Override
    public void initialize(EnumString constraintAnnotation) {
        valueList = Arrays.stream(constraintAnnotation.enumClazz()
                .getEnumConstants())
                .map(Enum::toString)
                .toList();

        String propertyName = constraintAnnotation.propertyName();
        messageTemplate = (!propertyName.equals("") ? propertyName + " " : "") + "? doesn't exist";
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(valueList.contains(s.toUpperCase()))
            return true;

        String msg = messageTemplate.replaceAll("\\?", s);

        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
        return false;
    }
}