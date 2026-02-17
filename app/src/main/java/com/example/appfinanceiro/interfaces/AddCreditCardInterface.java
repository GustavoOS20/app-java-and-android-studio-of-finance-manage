package com.example.appfinanceiro.interfaces;

import com.example.appfinanceiro.data.AddCreditCard;
import com.example.appfinanceiro.model.ModelCreditCard;
import com.example.appfinanceiro.model.Parcela;

import java.util.List;
import java.util.Map;

public interface AddCreditCardInterface {
    void addCreditCard(String id, ModelCreditCard creditCardData);
    void removeCreditCard(String id);
    Map<String, ModelCreditCard> getCreditCards();
    void criarParcela();
    List<Parcela> getListaDeParcelas();
}
