package com.programming.authenticationservice.common;

import com.programming.authenticationservice.dto.request.RegisterRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        RegisterRequestDto registerRequestDto = (RegisterRequestDto) o;
        return registerRequestDto.password().equals(registerRequestDto.matchingPassword());
    }
}
