package com.github.fabriciolfj.ifood.pedido.domain.integracao;

import com.github.fabriciolfj.ifood.pedido.api.dto.PedidoRealizadoDTO;
import com.github.fabriciolfj.ifood.pedido.api.dto.PratoPedidoDTO;
import com.github.fabriciolfj.ifood.pedido.domain.Pedido;
import com.github.fabriciolfj.ifood.pedido.domain.Prato;
import com.github.fabriciolfj.ifood.pedido.domain.Restaurante;
import org.bson.types.Decimal128;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.util.ArrayList;

@ApplicationScoped
public class PedidoRealizadoIncoming {

    @Inject
    private ESService elastic;

    @Incoming("pedidos")
    public void lerPedidos(PedidoRealizadoDTO dto) throws IOException {
        System.out.println("-----------------");
        System.out.println(dto);

        Pedido p = new Pedido();

        p.setPratos(new ArrayList<>());
        dto.pratos.forEach(prato -> p.getPratos().add(from(prato)));
        Restaurante restaurante = new Restaurante();
        restaurante.nome = dto.restaurante.nome;
        p.setRestaurante(restaurante);
        p.setCliente(dto.cliente);
        String json = JsonbBuilder.create().toJson(dto);
        elastic.index("pedido", json);
        p.persist();

    }

    private Prato from(PratoPedidoDTO prato) {
        Prato p = new Prato();
        p.setDescricao(prato.descricao);
        p.setNome(prato.nome);
        p.setPreco(new Decimal128(prato.preco));
        return p;
    }
}
