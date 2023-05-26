package com.seleccion.seleccion.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LettersValidation  implements ConstraintValidator<LettersOnly, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.matches("[a-z]+");
    }
}
