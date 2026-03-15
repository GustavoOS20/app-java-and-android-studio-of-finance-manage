package com.example.appfinanceiro.logicAndUi.adapter.interfaces;

import com.example.appfinanceiro.logicAndUi.adapter.model.ModelCreditCard;
import com.example.appfinanceiro.logicAndUi.adapter.model.Parcela;

import java.util.List;
import java.util.Map;

public interface AddCreditCardInterface {
    void addCreditCard(String id, ModelCreditCard creditCardData);
    void removeCreditCard(String id);
    Map<String, ModelCreditCard> getCreditCards();
    void criarParcela();
    List<Parcela> getListaDeParcelas();
}
