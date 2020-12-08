package com.github.fabriciolfj.ifood.pedido.api.dto;

import java.math.BigDecimal;

public class PratoPedidoDTO {

    public String nome;

    public String descricao;

    public BigDecimal preco;

    @Override
    public String toString() {
        return "PratoPedidoDTO{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", preco=" + preco +
                '}';
    }
}
