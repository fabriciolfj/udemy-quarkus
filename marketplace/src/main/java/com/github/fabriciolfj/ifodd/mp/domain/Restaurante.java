package com.github.fabriciolfj.ifodd.mp.domain;

import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurante {

    private Long id;
    private String nome;
    private Localizacao localizacao;

    public void persist(final PgPool pgPool) {
        pgPool.preparedQuery("insert into localizacao (id, latitude, longitude) values ($1, $2, $3)").execute(
                Tuple.of(localizacao.getId(), localizacao.getLatitude(), localizacao.getLongitude())).await().indefinitely();

        pgPool.preparedQuery("insert into restaurante (id, nome, localizacao_id) values ($1, $2, $3)").execute(
                Tuple.of(id, nome, localizacao.getId())
        ).await().indefinitely();
    }
}
