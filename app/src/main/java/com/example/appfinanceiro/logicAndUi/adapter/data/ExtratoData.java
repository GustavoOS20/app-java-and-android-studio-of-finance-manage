package com.example.appfinanceiro.logicAndUi.adapter.data;

import androidx.room.ColumnInfo;

import java.time.LocalDate;

public class ExtratoData {
    @ColumnInfo (name = "valor")
    private Integer valor;
    @ColumnInfo (name = "categoria")
    private String categoria;
    @ColumnInfo (name = "data")
    private LocalDate data;
    @ColumnInfo (name = "descricao")
    private String descricao;

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }



    public ExtratoData(){}

    @androidx.room.Ignore
    public ExtratoData(LocalDate data, String descricao, Integer valor, String categoria) {
        this.data = data;
        this.descricao = descricao;
        this.valor = valor;
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
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
