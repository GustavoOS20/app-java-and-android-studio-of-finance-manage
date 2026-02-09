package com.example.appfinanceiro.interfaces;

import com.example.appfinanceiro.data.AddBalanceData;
import com.example.appfinanceiro.model.ModelBalance;

import java.util.Map;

public interface AddBalanceInterface {
    void addBalance(String id, ModelBalance balanceData);
    void removeBalance(String id);

    Map<String, ModelBalance> getBalances();
}
