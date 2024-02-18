package com.example.aboutme.validation.validator;

import com.example.aboutme.apiPayload.code.status.ErrorStatus;
import com.example.aboutme.repository.SpaceRepository;
import com.example.aboutme.validation.annotation.ExistMySpace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
@Slf4j
public class SpaceNicknameExistValidator implements ConstraintValidator<ExistMySpace, String> {

    private final SpaceRepository spaceRepository;

    @Override
    public void initialize(ExistMySpace constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String nickname, ConstraintValidatorContext context) {

        boolean isValid = spaceRepository.existsByNickname(nickname);

        if(!isValid){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.SPACE_NICKNAME_ALREADY_EXIST.toString()).addConstraintViolation();
        }

        return isValid;
    }
}
