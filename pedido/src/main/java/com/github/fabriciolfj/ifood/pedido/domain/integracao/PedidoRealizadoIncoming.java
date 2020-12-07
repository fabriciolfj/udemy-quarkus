package com.github.fabriciolfj.ifood.pedido.domain.integracao;

import com.github.fabriciolfj.ifood.pedido.controller.dto.PedidoRealizadoDTO;
import com.github.fabriciolfj.ifood.pedido.controller.dto.PratoDTO;
import com.github.fabriciolfj.ifood.pedido.controller.dto.PratoPedidoDTO;
import com.github.fabriciolfj.ifood.pedido.domain.Pedido;
import com.github.fabriciolfj.ifood.pedido.domain.Prato;
import com.github.fabriciolfj.ifood.pedido.domain.Restaurante;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.stream.Collectors;

@ApplicationScoped
public class PedidoRealizadoIncoming {

    @Incoming("pedidos")
    public void lerPedidos(final PedidoRealizadoDTO dto) {
        final var pedido = new Pedido();
        pedido.cliente = dto.cliente;
        pedido.pratos =  dto.pratos.stream().map(this::fromPrato).collect(Collectors.toList());
        final var restaurante = new Restaurante();
        restaurante.nome = dto.restaurante.nome;
        pedido.restaurante = restaurante;
        pedido.persist();
    }

    private Prato fromPrato(final PratoPedidoDTO dto) {
        final var prato = new Prato();
        prato.preco = dto.preco;
        prato.descricao = dto.descricao;

        return prato;
    }
}
