package com.example.appfinanceiro.model;

public class ModelExpense {
    private final String valor;
    private final String descricao;
    private final String data;
    private final String contaOrigem;
    private final String categoria;

    public ModelExpense(String valor, String descricao, String data, String contaOrigem, String categoria) {
        this.valor = valor;
        this.descricao = descricao;
        this.data = data;
        this.contaOrigem = contaOrigem;
        this.categoria = categoria;
    }

    public String getValor() {
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
