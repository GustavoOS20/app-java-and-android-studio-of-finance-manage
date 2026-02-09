package com.example.appfinanceiro.data;

import com.example.appfinanceiro.interfaces.AddDespesasInterface;
import com.example.appfinanceiro.model.ModelExpense;

import java.util.HashMap;
import java.util.Map;

public class AddDespesasData implements AddDespesasInterface {
    private static final Map<String, ModelExpense> despesas = new HashMap<>();

    @Override
    public void addDespesas(String id, ModelExpense despesasData) {
        despesas.put(id, despesasData);
    }

    public Map<String, ModelExpense> getDespesas() {
        return despesas;
    }

    @Override
    public void removeDespesas(String id) {
        despesas.remove(id);
    }
}
