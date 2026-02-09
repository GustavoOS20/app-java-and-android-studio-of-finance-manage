package com.example.appfinanceiro.interfaces;

import com.example.appfinanceiro.data.AddCreditCard;
import com.example.appfinanceiro.model.ModelCreditCard;

import java.util.Map;

public interface AddCreditCardInterface {
    void addCreditCard(String id, ModelCreditCard creditCardData);
    void removeCreditCard(String id);
    Map<String, ModelCreditCard> getCreditCards();
}
