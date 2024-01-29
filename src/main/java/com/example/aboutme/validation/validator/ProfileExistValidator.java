package com.example.aboutme.validation.validator;


import com.example.aboutme.apiPayload.code.status.ErrorStatus;
import com.example.aboutme.apiPayload.exception.GeneralException;
import com.example.aboutme.repository.ProfileRepository;
import com.example.aboutme.validation.annotation.ExistMyProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProfileExistValidator implements ConstraintValidator<ExistMyProfile, Long> {

    private final ProfileRepository profileRepository;

    @Override
    public void initialize(ExistMyProfile constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long profileId, ConstraintValidatorContext context) {

        boolean isValid = profileRepository.existsById(profileId);

        if(!isValid){
            log.info("마이프로필이 존재하지 않음: {}", profileId);
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.PROFILE_NOT_FOUND.toString()).addConstraintViolation();
        }

        return isValid;
    }
}
