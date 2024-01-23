package com.example.aboutme.validation.validator;

import com.example.aboutme.apiPayload.code.status.ErrorStatus;
import com.example.aboutme.validation.annotation.RoomTypeBoundary;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RoomTypeBoundaryValidator implements ConstraintValidator<RoomTypeBoundary, Integer> {
    @Override
    public void initialize(RoomTypeBoundary constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        boolean isValid = (value >= 1) && (value <= 4);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus._UNVALID_ROOM_TYPE.toString()).addConstraintViolation();
        }
        return isValid;
    }
}
