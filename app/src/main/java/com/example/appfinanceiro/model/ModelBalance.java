package com.example.appfinanceiro.model;

public class ModelBalance {
    private final String saldo;
    private final String descricao;
    private final String contaDestino;
    private final String status;
    private final String categoria;
    private final String data;

    public ModelBalance(String saldo, String descricao, String contaDestino, String status, String categoria, String data) {
        this.saldo = saldo;
        this.descricao = descricao;
        this.contaDestino = contaDestino;
        this.status = status;
        this.categoria = categoria;
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getSaldo() {
        return saldo;
    }

    public String getContaDestino() {
        return contaDestino;
    }

    public String getStatus() {
        return status;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getData() {
        return data;
    }
}
