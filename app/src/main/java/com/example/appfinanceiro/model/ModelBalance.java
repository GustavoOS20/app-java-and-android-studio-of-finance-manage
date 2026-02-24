package com.example.appfinanceiro.model;

import java.math.BigDecimal;

public class ModelBalance extends FinanceModel {
    private final BigDecimal saldo;
    private final String descricao;
    private final String contaDestino;
    private final String status;
    private final String categoria;
    private final String data;
    private final int mes;
    private final int ano;


    public ModelBalance(BigDecimal saldo, String descricao, String contaDestino, String status, String categoria, String data, int mes, int ano) {
        this.saldo = saldo;
        this.descricao = descricao;
        this.contaDestino = contaDestino;
        this.status = status;
        this.categoria = categoria;
        this.data = data;
        this.mes = mes;
        this.ano = ano;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getSaldo() {
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

    public int getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }
}
