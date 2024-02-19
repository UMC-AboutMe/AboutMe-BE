package com.example.aboutme.validation.annotation;

import com.example.aboutme.validation.validator.SpaceExistValidator;
import com.example.aboutme.validation.validator.SpaceNicknameExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SpaceNicknameExistValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistSpaceNickname {

    String message() default "해당 닉네임의 스페이스가 이미 존재합니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
