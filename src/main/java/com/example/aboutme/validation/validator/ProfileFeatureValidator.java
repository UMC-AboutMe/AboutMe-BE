package com.example.aboutme.validation.validator;

import com.example.aboutme.apiPayload.code.status.ErrorStatus;
import com.example.aboutme.repository.ProfileFeatureRepository;
import com.example.aboutme.validation.annotation.ExistMyProfile;
import com.example.aboutme.validation.annotation.ExistProfileFeature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProfileFeatureValidator implements ConstraintValidator<ExistProfileFeature, Long> {

    private final ProfileFeatureRepository profileFeatureRepository;

    @Override
    public boolean isValid(Long profileFeatureId, ConstraintValidatorContext context) {

        boolean isValid = profileFeatureRepository.existsById(profileFeatureId);

        if(!isValid){
            log.info("프로필 특징이 존재하지 않음: {}", profileFeatureId);
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.PROFILE_FEATURE_NOT_FOUND.toString()).addConstraintViolation();
        }

        return isValid;
    }
}
