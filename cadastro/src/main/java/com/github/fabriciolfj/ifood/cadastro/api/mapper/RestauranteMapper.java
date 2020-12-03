package com.github.fabriciolfj.ifood.cadastro.api.mapper;

import com.github.fabriciolfj.ifood.cadastro.api.dto.request.AdicionarRestauranteDTO;
import com.github.fabriciolfj.ifood.cadastro.api.dto.request.AtualizaRestauranteDTO;
import com.github.fabriciolfj.ifood.cadastro.domain.entity.Restaurante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface RestauranteMapper {

    @Mapping(target = "nome", source = "nome")
    Restaurante toEntity(final AdicionarRestauranteDTO dto);

    @Mapping(target = "nome", source = "nome")
    void toUpdate(final AtualizaRestauranteDTO dto, @MappingTarget final Restaurante restaurante);
}
