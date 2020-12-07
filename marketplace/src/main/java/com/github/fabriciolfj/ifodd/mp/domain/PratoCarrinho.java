package com.github.fabriciolfj.ifodd.mp.domain;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;

import java.util.ArrayList;
import java.util.List;

public class PratoCarrinho {

    public String cliente;
    public Long prato;

    public static Uni<Long> save(final PgPool pgPool, final String cliente, final Long prato) {
        return pgPool.preparedQuery("INSERT INTO prato_cliente (cliente, prato) values ($1, $2) RETURNING (prato)")
                .execute(Tuple.of(cliente, prato))
                .map(row -> row.iterator().next().getLong("prato"));
    }

    public static Uni<List<PratoCarrinho>> findCarrinho(final PgPool pgPool, final String cliente) {
        return pgPool.preparedQuery("select * from prato_cliente where cliente = $1")
                .execute(Tuple.of(cliente))
                .map(row -> {
                    final List<PratoCarrinho> list = new ArrayList<>();
                    for(Row linha : row) {
                        list.add(toPratoCarrinho(linha));
                    }

                    return list;
                });
    }

    private static PratoCarrinho toPratoCarrinho(Row row) {
        PratoCarrinho pc = new PratoCarrinho();
        pc.cliente = row.getString("cliente");
        pc.prato = row.getLong("prato");
        return pc;
    }

    public static Uni<Boolean> delete(PgPool client, String cliente) {
        return client.preparedQuery("DELETE FROM prato_cliente WHERE cliente = $1").execute(Tuple.of(cliente))
                .map(pgRowSet -> pgRowSet.rowCount() == 1);

    }
}
