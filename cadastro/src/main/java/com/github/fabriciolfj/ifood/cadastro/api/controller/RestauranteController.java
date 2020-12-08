package com.github.fabriciolfj.ifood.cadastro.api.controller;

import com.github.fabriciolfj.ifood.cadastro.api.dto.request.AdicionarRestauranteDTO;
import com.github.fabriciolfj.ifood.cadastro.api.dto.request.AtualizaRestauranteDTO;
import com.github.fabriciolfj.ifood.cadastro.api.mapper.RestauranteMapper;
import com.github.fabriciolfj.ifood.cadastro.domain.entity.Restaurante;
import com.github.fabriciolfj.ifood.cadastro.infra.ConstraintViolationResponse;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.transaction.Transactional;
import javax.validation.Valid;
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
@RolesAllowed("proprietario")
@SecurityScheme(securitySchemeName = "ifood-oauth", type = SecuritySchemeType.OAUTH2, flows = @OAuthFlows(password = @OAuthFlow(tokenUrl = "http://localhost:8080/auth/realms/ifood/protocol/openid-connect/token")))
@SecurityRequirement(name = "ifood-oauth", scopes = {})
public class RestauranteController {

    @Inject
    private RestauranteMapper mapper;

    @Inject
    @Channel("restaurantes")
    private Emitter<String> emitter;

    /*@Inject
    private JsonWebToken jwt;

    @Inject
    @Claim(standard = Claims.sub)
    private String sub;*/

    @GET
    @Counted(name = "Quantidade buscas restaurante")
    @SimplyTimed(name = "Tempo simples de busca")
    @Timed(name = "Tempo completo de busa")
    public List<Restaurante> getRestaurantes() {
        return Restaurante.listAll();
    }

    @POST
    @Transactional
    @APIResponse(responseCode = "201", description = "Caso o restaurante seja cadastrado com sucesso")
    @APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = ConstraintViolationResponse.class)))
    public Response adicionar(@Valid final AdicionarRestauranteDTO dto) {
        final var restaurante = mapper.toEntity(dto);
        restaurante.proprietario = "a";
        restaurante.persist();

        final var json = JsonbBuilder.create();
        emitter.send(json.toJson(restaurante));
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void update(final AtualizaRestauranteDTO dto, @PathParam("id") final Long id) {
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(id);
        restauranteOp.filter(r -> r.proprietario.equals("a"))
                .map(restaurante -> {
            mapper.toUpdate(dto, restaurante);
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
