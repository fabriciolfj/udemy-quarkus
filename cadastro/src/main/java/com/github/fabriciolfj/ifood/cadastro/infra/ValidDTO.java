package com.github.fabriciolfj.ifood.cadastro.infra;

import com.github.fabriciolfj.ifood.cadastro.infra.validacao.ValidDTOValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target( {ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { ValidDTOValidator.class })
public @interface ValidDTO {

    String message() default "{com.github.fabrciolfj.ifood.cadastro.infra.ValidDTO.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
