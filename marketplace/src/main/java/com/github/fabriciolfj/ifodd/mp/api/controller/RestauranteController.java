package com.github.fabriciolfj.ifodd.mp.api.controller;

import com.github.fabriciolfj.ifodd.mp.api.dto.PratoDTO;
import com.github.fabriciolfj.ifodd.mp.domain.Prato;
import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("restaurantes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestauranteController {

    @Inject
    PgPool pgPool;

    @GET
    @Path("/{idRestaurante}/pratos")
    public Multi<PratoDTO> buscarPratoPeloRestaurante(@PathParam("idRestaurante") final Long id) {
        return Prato.findPratoPorRestaurante(pgPool, id);
    }
}
