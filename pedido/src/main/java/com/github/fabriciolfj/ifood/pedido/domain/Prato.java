package com.github.fabriciolfj.ifood.pedido.domain;

import org.bson.types.Decimal128;

import java.math.BigDecimal;

public class Prato {

    private String nome;

    private String descricao;

    private Decimal128 preco;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Decimal128 getPreco() {
        return preco;
    }

    public void setPreco(Decimal128 preco) {
        this.preco = preco;
    }
}
