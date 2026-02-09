package com.example.appfinanceiro.service;

import com.example.appfinanceiro.interfaces.AddBalanceInterface;
import com.example.appfinanceiro.model.ModelBalance;

import java.util.Map;

public class ServiceBalance {
    AddBalanceInterface addBalanceInterface;
    public ServiceBalance(AddBalanceInterface addBalanceInterface) {
        this.addBalanceInterface = addBalanceInterface;
    }

    public void addBalance(String id, ModelBalance balanceData) {
        addBalanceInterface.addBalance(id, balanceData);
    }

    public void removeBalance(String id) {
        addBalanceInterface.removeBalance(id);
    }

    public Map<String, ModelBalance> getBalances() {
        return addBalanceInterface.getBalances();
    }

}
