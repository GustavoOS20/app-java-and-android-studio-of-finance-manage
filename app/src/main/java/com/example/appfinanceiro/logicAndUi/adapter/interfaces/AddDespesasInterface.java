package com.example.appfinanceiro.logicAndUi.adapter.interfaces;

import com.example.appfinanceiro.logicAndUi.adapter.model.ModelExpense;

import java.util.Map;

public interface AddDespesasInterface {
    void addDespesas(String id, ModelExpense despesasData);
    void removeDespesas(String id);
    Map<String, ModelExpense> getDespesas();

}
