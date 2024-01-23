package com.example.aboutme.validation.annotation;

import com.example.aboutme.validation.validator.CharacterTypeBoundaryValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CharacterTypeBoundaryValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CharacterTypeBoundary {
    String message() default "캐릭터 타입은 1부터 9까지의 숫자만 가능합니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
