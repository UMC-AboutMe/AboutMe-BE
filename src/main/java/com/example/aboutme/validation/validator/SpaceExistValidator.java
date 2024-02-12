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
public class SpaceExistValidator implements ConstraintValidator<ExistMySpace, Long> {

    private final SpaceRepository spaceRepository;

    @Override
    public void initialize(ExistMySpace constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long spaceId, ConstraintValidatorContext context) {

        boolean isValid = spaceRepository.existsById(spaceId);

        if(!isValid){
            log.info("스페이스가 존재하지 않음: {}", spaceId);
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.SPACE_NOT_FOUND.toString()).addConstraintViolation();
        }

        return isValid;
    }
}
