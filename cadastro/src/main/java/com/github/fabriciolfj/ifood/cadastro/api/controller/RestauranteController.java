package com.github.fabriciolfj.ifood.cadastro.api.controller;

import com.github.fabriciolfj.ifood.cadastro.api.dto.request.AdicionarRestauranteDTO;
import com.github.fabriciolfj.ifood.cadastro.api.mapper.RestauranteMapper;
import com.github.fabriciolfj.ifood.cadastro.domain.entity.Restaurante;

import javax.inject.Inject;
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
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestauranteController {

    @Inject
    private RestauranteMapper mapper;

    @GET
    public List<Restaurante> getRestaurantes() {
        return Restaurante.listAll();
    }

    @POST
    @Transactional
    public Response adicionar(final AdicionarRestauranteDTO dto) {
        final var restaurante = mapper.toEntity(dto);
        restaurante.persist();
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void update(final Restaurante dto, @PathParam("id") final Long id) {
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(id);
        restauranteOp.map(restaurante -> {
            restaurante.nome = dto.nome;
            restaurante.persist();
            return restaurante;
        }).orElseThrow(() -> new NotFoundException());
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void delete(@PathParam("id") final Long id) {
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(id);
        restauranteOp.ifPresentOrElse(Restaurante::delete, () -> {
            throw new NotFoundException();
        });
    }

}
