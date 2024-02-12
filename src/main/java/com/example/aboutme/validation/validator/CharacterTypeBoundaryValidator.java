package com.example.aboutme.validation.validator;

import com.example.aboutme.apiPayload.code.status.ErrorStatus;
import com.example.aboutme.validation.annotation.CharacterTypeBoundary;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CharacterTypeBoundaryValidator implements ConstraintValidator<CharacterTypeBoundary, Integer> {
    @Override
    public void initialize(CharacterTypeBoundary constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        boolean isValid = (value >= 1) && (value <= 9);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus._UNVALID_CHARACTER_TYPE.toString()).addConstraintViolation();
        }

        return isValid;
    }
}
