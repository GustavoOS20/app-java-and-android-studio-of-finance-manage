package com.example.appfinanceiro.model;

import java.math.BigDecimal;

public class ModelCreditCard {
    private final BigDecimal valor;
    private final String bankcCard;
    private final int parcelas;
    private final String descricao;
    private final String categoria;
    private final int dia;
    private final int mes;
    private final int ano;

    public ModelCreditCard(BigDecimal valor, String bankcCard, int parcelas, String descricao, String categoria, int dia, int mes, int ano) {
        this.valor = valor;
        this.bankcCard = bankcCard;
        this.parcelas = parcelas;
        this.descricao = descricao;
        this.categoria = categoria;
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getBankcCard() {
        return bankcCard;
    }

    public int getParcelas() {
        return parcelas;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }
}
