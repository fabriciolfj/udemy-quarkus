package com.github.fabriciolfj.ifood.pedido.controller.deserializer;

import com.github.fabriciolfj.ifood.pedido.controller.dto.PedidoRealizadoDTO;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class PedidoDeserializer extends ObjectMapperDeserializer<PedidoRealizadoDTO> {

    public PedidoDeserializer() {
        super(PedidoRealizadoDTO.class);
    }
}
