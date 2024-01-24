package com.example.aboutme.validation.annotation;

import com.example.aboutme.validation.validator.CharacterTypeBoundaryValidator;
import com.example.aboutme.validation.validator.RoomTypeBoundaryValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RoomTypeBoundaryValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface RoomTypeBoundary {
    String message() default "방 타입은 1부터 4까지의 숫자만 가능합니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
