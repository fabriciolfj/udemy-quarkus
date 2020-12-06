package com.github.fabriciolfj.ifood.pedido.controller.dto;

import java.math.BigDecimal;

public class PratoDTO {

    public Long id;

    public String nome;

    public String descricao;

    public BigDecimal preco;


    @Override
    public String toString() {
        return "PratoDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", preco=" + preco +
                '}';
    }
}
