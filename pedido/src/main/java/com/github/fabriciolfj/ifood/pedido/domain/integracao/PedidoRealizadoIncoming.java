package com.github.fabriciolfj.ifood.pedido.domain.integracao;

import com.github.fabriciolfj.ifood.pedido.controller.dto.PedidoRealizadoDTO;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PedidoRealizadoIncoming {

    @Incoming("pedidos")
    public void lerPedidos(final PedidoRealizadoDTO dto) {
        System.out.println(dto.toString());
    }
}
