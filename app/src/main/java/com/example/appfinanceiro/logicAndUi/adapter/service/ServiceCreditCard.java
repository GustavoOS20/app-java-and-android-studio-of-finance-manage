package com.example.appfinanceiro.logicAndUi.adapter.service;

import com.example.appfinanceiro.logicAndUi.adapter.interfaces.AddCreditCardInterface;
import com.example.appfinanceiro.logicAndUi.adapter.model.ModelCreditCard;
import com.example.appfinanceiro.logicAndUi.adapter.model.Parcela;

import java.util.List;
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

    public void criarParcela(){
        addCreditCardInterface.criarParcela();
    }

    public List<Parcela> getListaDeParcelas(){
        return addCreditCardInterface.getListaDeParcelas();
    }
}
