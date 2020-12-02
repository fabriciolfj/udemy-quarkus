package com.github.fabriciolfj.ifood.cadastro.api.mapper;

import com.github.fabriciolfj.ifood.cadastro.api.dto.request.AdicionarPratoDTO;
import com.github.fabriciolfj.ifood.cadastro.domain.entity.Prato;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface PratoMapper {

    Prato toEntity(final AdicionarPratoDTO dto);
}
