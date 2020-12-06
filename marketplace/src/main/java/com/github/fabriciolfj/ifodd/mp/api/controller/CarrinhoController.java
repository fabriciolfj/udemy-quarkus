package com.github.fabriciolfj.ifodd.mp.api.controller;

import com.github.fabriciolfj.ifodd.mp.api.dto.PedidoRealizadoDTO;
import com.github.fabriciolfj.ifodd.mp.api.dto.PratoDTO;
import com.github.fabriciolfj.ifodd.mp.api.dto.PratoPedidoDTO;
import com.github.fabriciolfj.ifodd.mp.api.dto.RestauranteDTO;
import com.github.fabriciolfj.ifodd.mp.domain.Prato;
import com.github.fabriciolfj.ifodd.mp.domain.PratoCarrinho;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("carrinho")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarrinhoController {

    private static final String CLIENTE = "a";

    @Inject
    private PgPool pgPool;

    @Inject
    @Channel("pedidos")
    private Emitter<PedidoRealizadoDTO> emitter;

    @POST
    @Path("/prato/{idPrato}")
    public Uni<Long> adicionarPrato(@PathParam("idPrato") Long idPrato) {
        //poderia retornar o pedido atual
        PratoCarrinho pc = new PratoCarrinho();
        pc.cliente = CLIENTE;
        pc.prato = idPrato;
        return PratoCarrinho.save(pgPool, CLIENTE, idPrato);

    }

    @POST
    @Path("/realizar-pedido")
    public Uni<Boolean> finalizar() {
        PedidoRealizadoDTO pedido = new PedidoRealizadoDTO();
        String cliente = CLIENTE;
        pedido.cliente = cliente;
        List<PratoCarrinho> pratoCarrinho = PratoCarrinho.findCarrinho(pgPool, cliente).await().indefinitely();
        //Utilizar mapstruts
        List<PratoPedidoDTO> pratos = pratoCarrinho.stream().map(pc -> from(pc)).collect(Collectors.toList());
        pedido.pratos = pratos;

        RestauranteDTO restaurante = new RestauranteDTO();
        restaurante.nome = "nome restaurante";
        pedido.restaurante = restaurante;
        emitter.send(pedido);
        return PratoCarrinho.delete(pgPool, cliente);
    }

    private PratoPedidoDTO from(PratoCarrinho pratoCarrinho) {
        PratoDTO dto = Prato.findById(pgPool, pratoCarrinho.prato).await().indefinitely();
        return new PratoPedidoDTO(dto.nome, dto.descricao, dto.preco);
    }
}
