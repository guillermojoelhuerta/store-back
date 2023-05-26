package com.seleccion.seleccion.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LettersValidation.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface LettersOnly {
    String message() default "Solo deben ser letras";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
