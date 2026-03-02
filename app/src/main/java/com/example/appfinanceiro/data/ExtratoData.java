package com.example.appfinanceiro.data;

import java.time.LocalDate;

public class ExtratoData {
    private final LocalDate data;
    private final String descricao;
    private final Integer valor;

    public ExtratoData(LocalDate data, String descricao, Integer valor) {
        this.data = data;
        this.descricao = descricao;
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getValor() {
        return valor;
    }

}
