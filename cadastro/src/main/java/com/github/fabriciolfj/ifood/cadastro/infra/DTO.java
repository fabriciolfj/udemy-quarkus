package com.github.fabriciolfj.ifood.cadastro.infra;

import javax.validation.ConstraintValidatorContext;

public interface DTO {

    default boolean isValid(final ConstraintValidatorContext constraintValidatorContext) {
        return true;
    }
}
