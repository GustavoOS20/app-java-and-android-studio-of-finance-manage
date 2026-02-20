package com.example.appfinanceiro.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class AdapterDataMainYear extends ArrayAdapter<Integer> {
    public AdapterDataMainYear(@NonNull Context context, List<Integer> opcoesAno) {
        super(context, android.R.layout.simple_spinner_item, opcoesAno);

    }
}
