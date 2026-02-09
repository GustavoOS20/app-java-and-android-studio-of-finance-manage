package com.example.appfinanceiro.service;

import com.example.appfinanceiro.interfaces.AddCreditCardInterface;
import com.example.appfinanceiro.model.ModelCreditCard;

import java.util.Map;

public class ServiceCreditCard {
    AddCreditCardInterface addCreditCardInterface;

    public ServiceCreditCard(AddCreditCardInterface addCreditCardInterface) {
        this.addCreditCardInterface = addCreditCardInterface;
    }

    public void addCreditCard(String id, ModelCreditCard creditCardData) {
        addCreditCardInterface.addCreditCard(id, creditCardData);
    }

    public void removeCreditCard(String id) {
        addCreditCardInterface.removeCreditCard(id);
    }

    public Map<String, ModelCreditCard> getCreditCards() {
        return addCreditCardInterface.getCreditCards();
    }
}
