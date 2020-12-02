package com.github.fabriciolfj.ifood.cadastro.api.mapper;

import com.github.fabriciolfj.ifood.cadastro.api.dto.request.AdicionarRestauranteDTO;
import com.github.fabriciolfj.ifood.cadastro.domain.entity.Restaurante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface RestauranteMapper {

    @Mapping(target = "nome", source = "nome")
    Restaurante toEntity(final AdicionarRestauranteDTO dto);
}
