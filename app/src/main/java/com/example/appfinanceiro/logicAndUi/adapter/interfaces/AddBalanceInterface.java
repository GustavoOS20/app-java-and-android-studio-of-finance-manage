package com.example.appfinanceiro.logicAndUi.adapter.interfaces;

import com.example.appfinanceiro.logicAndUi.adapter.model.ModelBalance;

import java.util.Map;

public interface AddBalanceInterface {
    void addBalance(String id, ModelBalance balanceData);
    void removeBalance(String id);

    Map<String, ModelBalance> getBalances();
}
