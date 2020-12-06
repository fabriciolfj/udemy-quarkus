package com.github.fabriciolfj.ifodd.mp.api.dto;

import io.vertx.mutiny.sqlclient.Row;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestauranteDTO {

    private Long id;
    private String nome;

    public static RestauranteDTO toDto(final Row row) {
        return RestauranteDTO
                .builder()
                .id(row.getLong("id"))
                .nome(row.getString("nome"))
                .build();
    }
}
