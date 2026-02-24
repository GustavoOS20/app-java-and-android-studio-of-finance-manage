package com.example.appfinanceiro.model;

import java.math.BigDecimal;

public class ModelExpense extends FinanceModel {
    private final BigDecimal valor;
    private final String descricao;
    private final String data;
    private final String contaOrigem;
    private final String categoria;
    private final int mes;
    private final int ano;

    public ModelExpense(BigDecimal valor, String descricao, String data, String contaOrigem, String categoria, int mes, int ano) {
        this.valor = valor;
        this.descricao = descricao;
        this.data = data;
        this.contaOrigem = contaOrigem;
        this.categoria = categoria;
        this.mes = mes;
        this.ano = ano;
    }

    public int getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getData() {
        return data;
    }

    public String getContaOrigem() {
        return contaOrigem;
    }

    public String getCategoria() {
        return categoria;
    }
}
