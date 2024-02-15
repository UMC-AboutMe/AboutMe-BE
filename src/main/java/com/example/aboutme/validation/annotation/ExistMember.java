package com.example.aboutme.validation.annotation;

import com.example.aboutme.validation.validator.MemberExistValidator;
import com.example.aboutme.validation.validator.MoodExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MemberExistValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistMember {

    String message() default "해당하는 멤버가 존재하지 않습니다";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
