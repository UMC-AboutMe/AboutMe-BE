package com.example.aboutme.validation.validator;

import com.example.aboutme.domain.constant.Mood;
import com.example.aboutme.validation.annotation.ExistMood;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MoodExistValidator implements ConstraintValidator<ExistMood, String> {
    @Override
    public void initialize(ExistMood constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            Mood.valueOf(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
