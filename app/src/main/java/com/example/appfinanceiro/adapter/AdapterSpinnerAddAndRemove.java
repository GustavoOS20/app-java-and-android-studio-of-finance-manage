package com.example.appfinanceiro.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class AdapterSpinnerAddAndRemove extends ArrayAdapter<String> {
    public AdapterSpinnerAddAndRemove(@NonNull Context context, List<String> opcoes) {
        super(context, android.R.layout.simple_spinner_item, opcoes);
    }
}
