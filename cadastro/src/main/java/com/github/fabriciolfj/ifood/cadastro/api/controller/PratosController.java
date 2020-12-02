package com.github.fabriciolfj.ifood.cadastro.api.controller;

import com.github.fabriciolfj.ifood.cadastro.domain.entity.Prato;
import com.github.fabriciolfj.ifood.cadastro.domain.entity.Restaurante;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/restaurantes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "restaurante")
public class PratosController {

    @PUT
    @Path("{idRestaurante}/pratos/{id}")
    @Transactional
    @Tag(name = "prato")
    public void atualizar(@PathParam("idRestaurante") final Long idRestaurante, @PathParam("id") final Long id, final Prato dto) {
        validarExistenciaRestaurante(idRestaurante);

        final Optional<Prato> pratoop = Prato.findByIdOptional(id);
        pratoop.map(prato -> {
            prato.preco = dto.preco;
            prato.persist();
            return prato;
        }).orElseThrow(() -> new NotFoundException());
    }

    @GET
    @Path("{idRestaurante}/pratos")
    @Tag(name = "prato")
    public List<Prato> findAll(@PathParam("idRestaurante") final Long idRestaurante) {
        Optional<Restaurante> restauranteop = Restaurante.findByIdOptional(idRestaurante);

        if (restauranteop.isEmpty()) {
            throw new NotFoundException(String.format("Restaurante %d não existe", idRestaurante));
        }

        return Prato.list("restaurante", restauranteop.get());
    }

    @POST
    @Path("{idRestaurante}/pratos")
    @Transactional
    @Tag(name = "prato")
    public Response create(final Prato prato, @PathParam("idRestaurante") final Long idRestaurante) {
        final Optional<Restaurante> restauranteop = Restaurante.findByIdOptional(idRestaurante);
        return restauranteop.map(restaurante -> {
            prato.restaurante = restaurante;
            prato.persist();
            return Response.status(Response.Status.CREATED).build();
        }).orElseThrow(() -> new NotFoundException());
    }

    @DELETE
    @Path("{idRestaurante}/pratos/{id}")
    @Transactional
    @Tag(name = "prato")
    public void delete(@PathParam("idRestaurante") final Long idRestaurante, @PathParam("id") final Long id) {
        validarExistenciaRestaurante(idRestaurante);

        final Optional<Prato> pratoop = Prato.findByIdOptional(id);

        if (pratoop.isEmpty()) {
            throw new NotFoundException();
        }

        pratoop.get().delete();
    }

    private void validarExistenciaRestaurante(Long idRestaurante) {
        final Optional<Restaurante> restauranteop = Restaurante.findByIdOptional(idRestaurante);

        if(restauranteop.isEmpty()) {
            throw new NotFoundException(String.format("Restaurante inexistente para o id %d", idRestaurante));
        }
    }
}
