package com.example.aboutme.validation.validator;

import com.example.aboutme.apiPayload.code.status.ErrorStatus;
import com.example.aboutme.repository.ProfileRepository;
import com.example.aboutme.validation.annotation.ExistMyProfile;
import com.example.aboutme.validation.annotation.ExistProfilesBySerialNum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.ModCheck;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProfilesExistBySerialNumValidator implements ConstraintValidator<ExistProfilesBySerialNum, List<Integer>> {

    private final ProfileRepository profileRepository;

    @Override
    public boolean isValid(List<Integer> profileList, ConstraintValidatorContext context) {
        boolean isValid = profileList.stream()
                .allMatch(value -> profileRepository.existsBySerialNumber(value));

        if(!isValid){
            log.info("마이프로필이 존재하지 않음: {}", profileList);
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.PROFILE_NOT_FOUND.toString()).addConstraintViolation();
        }

        return isValid;
    }
}
