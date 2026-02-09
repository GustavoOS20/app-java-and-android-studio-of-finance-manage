package com.example.appfinanceiro.service;

import com.example.appfinanceiro.data.AddDespesasData;
import com.example.appfinanceiro.interfaces.AddDespesasInterface;
import com.example.appfinanceiro.model.ModelExpense;

import java.util.Map;

public class ServiceDespesas {
    AddDespesasInterface addDespesasInterface;
    public ServiceDespesas(AddDespesasInterface addDespesasInterface) {
        this.addDespesasInterface = addDespesasInterface;
    }

    public void addDespesas(String id, ModelExpense despesasData) {
        addDespesasInterface.addDespesas(id, despesasData);
    }

    public void removeDespesas(String id) {
        addDespesasInterface.removeDespesas(id);
    }

    public Map<String, ModelExpense> getDespesas() {
        return addDespesasInterface.getDespesas();
    }
}
