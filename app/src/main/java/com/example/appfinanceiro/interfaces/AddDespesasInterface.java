package com.example.appfinanceiro.interfaces;

import com.example.appfinanceiro.data.AddDespesasData;
import com.example.appfinanceiro.model.ModelExpense;

import java.util.Map;

public interface AddDespesasInterface {
    void addDespesas(String id, ModelExpense despesasData);
    void removeDespesas(String id);
    Map<String, ModelExpense> getDespesas();

}
