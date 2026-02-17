package com.example.appfinanceiro.model;

import androidx.annotation.NonNull;

public class Parcela {
    private int numero;
    public Parcela(int numero) {
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    @NonNull
    @Override
    public String toString() {
        return numero + "x";
    }
}
