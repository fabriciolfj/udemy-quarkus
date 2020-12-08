package com.github.fabriciolfj.ifood.pedido.api.deserializer;

import com.github.fabriciolfj.ifood.pedido.api.dto.PedidoRealizadoDTO;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class PedidoDeserializer extends ObjectMapperDeserializer<PedidoRealizadoDTO> {

    public PedidoDeserializer() {
        super(PedidoRealizadoDTO.class);
    }
}
