package com.example.appfinanceiro.logicAndUi.adapter.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.appfinanceiro.logicAndUi.adapter.model.Parcela;

import java.util.List;

public class AdapterSpinner extends ArrayAdapter<Parcela> {
    public AdapterSpinner(@NonNull Context context, List<Parcela> opcoesPar) {
        super(context, android.R.layout.simple_spinner_item, opcoesPar);
    }
}