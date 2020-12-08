package com.github.fabriciolfj.ifood.cadastro.api.dto.request;

import com.github.fabriciolfj.ifood.cadastro.domain.entity.Restaurante;
import com.github.fabriciolfj.ifood.cadastro.infra.DTO;
import com.github.fabriciolfj.ifood.cadastro.infra.ValidDTO;

import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ValidDTO
public class AdicionarRestauranteDTO implements DTO {

    @Size(min = 3, max = 30)
    public String nome;
    public LocalizacaoDTO localizacao;

    @NotNull
    @Pattern(regexp = "[0-9]{2}\\.[0-9]{3}\\.[0-9]{3}\\/[0-9]{4}\\-[0-9]{2}")
    public String cnpj;

    @Override
    public boolean isValid(ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        if (Restaurante.find("cnpj", cnpj).count() > 0) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("CNPJ já cadastrado")
                    .addPropertyNode("cnpj")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
