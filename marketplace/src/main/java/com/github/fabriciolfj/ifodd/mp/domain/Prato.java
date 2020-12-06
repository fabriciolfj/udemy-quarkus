package com.github.fabriciolfj.ifodd.mp.domain;

import com.github.fabriciolfj.ifodd.mp.api.dto.PratoDTO;
import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.stream.StreamSupport;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Prato {

    private Long id;
    private String nome;
    private String descricao;
    private Restaurante restaurante;
    private BigDecimal preco;

    public static Multi<PratoDTO> findlAll(final PgPool pgPool) {
        return pgPool.preparedQuery("select * from prato")
                .execute()
                .onItem()
                .transformToMulti(set -> Multi.createFrom().items(() -> {
                    return StreamSupport.stream(set.spliterator(), false);
                }))
                .onItem().transform(PratoDTO::from);
    }

    public static Multi<PratoDTO> findPratoPorRestaurante(final PgPool pgPool, final Long id) {
        return pgPool.preparedQuery("select * from prato where prato.restaurante_id = $1")
                .execute(Tuple.of(id))
                .onItem()
                .transformToMulti(set -> Multi.createFrom().items(() -> StreamSupport.stream(set.spliterator(), false)))
                .onItem()
                .transform(PratoDTO::from);
    }
}
