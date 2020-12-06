package com.github.fabriciolfj.ifodd.mp.domain.integracao;

import com.github.fabriciolfj.ifodd.mp.domain.Restaurante;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;

@Slf4j
@ApplicationScoped
public class RestauranteCadastrado {

    @Inject
    private PgPool pgPool;

    @Incoming("restaurantes")
    public void receberRestaurante(final String json) {
        final var create = JsonbBuilder.create();
        final var restaurante = create.fromJson(json, Restaurante.class);

        log.info(restaurante.toString());
        restaurante.persist(pgPool);
    }
}
