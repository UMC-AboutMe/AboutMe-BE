package com.example.aboutme.validation.validator;

import com.example.aboutme.apiPayload.code.status.ErrorStatus;
import com.example.aboutme.repository.ProfileRepository;
import com.example.aboutme.validation.annotation.ExistProfileBySerialNum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProfileExistBySerialNumValidator implements ConstraintValidator<ExistProfileBySerialNum, Integer> {

    private final ProfileRepository profileRepository;

    @Override
    public boolean isValid(Integer serialNumber, ConstraintValidatorContext context) {

        boolean isValid = profileRepository.existsBySerialNumber(serialNumber);

        if(!isValid){
            log.info("마이프로필이 존재하지 않음: {}", serialNumber);
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.PROFILE_NOT_FOUND.toString()).addConstraintViolation();
        }

        return isValid;
    }
}
