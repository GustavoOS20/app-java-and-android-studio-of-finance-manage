package com.example.appfinanceiro.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.appfinanceiro.model.Mes;

import java.util.List;

public class AdapterDataMainMes extends ArrayAdapter<Mes> {
    public AdapterDataMainMes(@NonNull Context context, List<Mes> opcoesMes) {
        super(context, android.R.layout.simple_spinner_item, opcoesMes);
    }
}
