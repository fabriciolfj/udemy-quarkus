package com.github.fabriciolfj.ifood.cadastro.infra.validacao;

import com.github.fabriciolfj.ifood.cadastro.infra.DTO;
import com.github.fabriciolfj.ifood.cadastro.infra.ValidDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidDTOValidator implements ConstraintValidator<ValidDTO, DTO> {
    @Override
    public void initialize(ValidDTO constraintAnnotation) {

    }

    @Override
    public boolean isValid(DTO dto, ConstraintValidatorContext constraintValidatorContext) {
        return dto.isValid(constraintValidatorContext);
    }
}
