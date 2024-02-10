package com.example.aboutme.validation.validator;

import com.example.aboutme.apiPayload.code.status.ErrorStatus;
import com.example.aboutme.validation.annotation.CheckEnumType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
@Slf4j
public class EnumValidator implements ConstraintValidator<CheckEnumType, String> {

    private Enum[] enumArr;
    private String enumClassName;

    @Override
    public void initialize(CheckEnumType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        enumArr = constraintAnnotation.enumClass().getEnumConstants();
        enumClassName = constraintAnnotation.enumClass().getSimpleName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        boolean isValid = false;

        if(enumArr == null){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus._INTERNAL_SERVER_ERROR.toString()).addConstraintViolation();
            return false;
        }

        for(Enum enumValue : enumArr){
            if(enumValue.name().equals(value)){
                isValid = true;
                break;
            }
        }

        if(!isValid){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(enumClassName + ": " + ErrorStatus._ENUM_TYPE_NOT_MATCH.getMessage()).addConstraintViolation();
        }

        return isValid;
    }
}
