package com.example.appfinanceiro.data;

import com.example.appfinanceiro.interfaces.AddBalanceInterface;
import com.example.appfinanceiro.model.ModelBalance;

import java.util.HashMap;
import java.util.Map;

public class AddBalanceData implements AddBalanceInterface {
    private static final Map<String, ModelBalance> balances = new HashMap<>();

    @Override
    public void addBalance(String id, ModelBalance balanceData) {
        balances.put(id, balanceData);
    }

    public Map<String, ModelBalance> getBalances() {
        return balances;
    }

    @Override
    public void removeBalance(String id) {
        balances.remove(id);
    }
}
