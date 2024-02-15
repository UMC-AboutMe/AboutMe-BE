package com.example.aboutme.validation.validator;

import com.example.aboutme.apiPayload.code.status.ErrorStatus;
import com.example.aboutme.domain.constant.Mood;
import com.example.aboutme.repository.MemberRepository;
import com.example.aboutme.validation.annotation.ExistMember;
import com.example.aboutme.validation.annotation.ExistMood;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class MemberExistValidator implements ConstraintValidator<ExistMember, Long> {

    private final MemberRepository memberRepository;
    @Override
    public void initialize(ExistMember constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        boolean isValid = memberRepository.existsById(value);

        if(!isValid){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.PROFILE_NOT_FOUND.toString()).addConstraintViolation();
        }

        return isValid;
    }
}
