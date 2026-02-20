package com.example.appfinanceiro.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Mes {
    private final String mes;
    private final int mesNum;

    private static final ArrayList<Mes> mesArrayList = new ArrayList<>();

    public Mes(String mes, int mesNum) {
        this.mes = mes;
        this.mesNum = mesNum;
    }

    public int getMesNum() {
        return mesNum;
    }

    public static ArrayList<Mes> getMesArrayList() {
        return mesArrayList;
    }

    public static String[] mesArray = new String[]{
            "Janeiro",
            "Fevereiro",
            "Março",
            "Abril",
            "Maio",
            "Junho",
            "Julho",
            "Agosto",
            "Setembro",
            "Outubro",
            "Novembro",
            "Dezembro"
    };

    public static int[] numeroMes = new int[]{
        1,2,3,4,5,6,7,8,9,10,11,12
    };

    public String getMes() {
        return mes;
    }

    @NonNull
    @Override
    public String toString() {
        return mes;
    }

}
