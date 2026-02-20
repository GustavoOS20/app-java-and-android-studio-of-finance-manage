package com.example.appfinanceiro.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Year {
    int anoInicio = 2025;
    private static final ArrayList<Integer> anos = new ArrayList<>();
    int anoDestino = Calendar.getInstance().get(Calendar.YEAR) + 10;

    public static ArrayList<Integer> getAnos() {
        return anos;
    }

    public void gerarAnos() {
        for (int i = anoInicio; i <= anoDestino; i++) {
            anos.add(i);
        }
    }
}
