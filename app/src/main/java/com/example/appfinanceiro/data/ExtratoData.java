package com.example.appfinanceiro.data;

public class ExtratoData {
    private final String data;
    private final String descricao;
    private final Integer valor;

    public ExtratoData(String data, String descricao, Integer valor) {
        this.data = data;
        this.descricao = descricao;
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getValor() {
        return valor;
    }

}
