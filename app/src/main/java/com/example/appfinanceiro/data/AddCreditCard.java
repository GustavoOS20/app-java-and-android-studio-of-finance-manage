package com.example.appfinanceiro.data;

import com.example.appfinanceiro.interfaces.AddCreditCardInterface;
import com.example.appfinanceiro.model.ModelCreditCard;
import com.example.appfinanceiro.model.Parcela;
import com.example.appfinanceiro.service.ServiceCreditCard;
import com.example.appfinanceiro.utilitiesClass.ViewUtilities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCreditCard implements AddCreditCardInterface {
    private static final List<Parcela > listaDeParcelas = new ArrayList<>();
    private static final Map<String, ModelCreditCard> creditCards = new HashMap<>();

    public void addCreditCard(String id, ModelCreditCard creditCardData){
        if(creditCardData.getParcelas() > 1){
            DividirValor(creditCardData.getMes(), creditCardData.getAno(), creditCardData);
        } else{
            creditCards.put(id, creditCardData);
        }
    }

    public Map<String, ModelCreditCard> getCreditCards(){
        return creditCards;
    }

    public void removeCreditCard(String id){
        creditCards.remove(id);
    }

    public void DividirValor( int mes, int ano, ModelCreditCard addCreditCard) {
        BigDecimal valorDividido = addCreditCard.getValor().divide(new BigDecimal(addCreditCard.getParcelas()), 2, RoundingMode.HALF_UP);
        for (int i = 0; i < addCreditCard.getParcelas(); i++) {
            AddCreditCardInterface creditCardInterface = new AddCreditCard();
            ServiceCreditCard serviceCreditCard = new ServiceCreditCard(creditCardInterface);
            String descricao = addCreditCard.getDescricao() + "Parcela: "+ addCreditCard.getParcelas();
            ModelCreditCard creditCardData = new ModelCreditCard(valorDividido,
                    addCreditCard.getBankcCard(),
                    1,
                    descricao,
                    addCreditCard.getCategoria(),
                    addCreditCard.getDia(),
                    mes,
                    ano);
            serviceCreditCard.addCreditCard(ViewUtilities.IdValue(), creditCardData);
            if(mes == 12){
                mes = 0;
                ano ++;
            }
            mes ++;
        }
    }

    @Override
    public void criarParcela(){
        for (int i = 1; i <= 12; i++) {
            listaDeParcelas.add(new Parcela(i));
        }
    }

    public List<Parcela> getListaDeParcelas(){
        return listaDeParcelas;
    }

}
