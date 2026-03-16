package com.example.appfinanceiro.logicAndUi.adapter.data;

import com.example.appfinanceiro.database.DAO.CategoriasDao;
import com.example.appfinanceiro.database.DAO.CreditCardDao;
import com.example.appfinanceiro.database.Entity.TransacoesDbCartao;
import com.example.appfinanceiro.database.FinanceDatabase;
import com.example.appfinanceiro.databinding.ActivityCardCreditBinding;
import com.example.appfinanceiro.logicAndUi.adapter.interfaces.AddCreditCardInterface;
import com.example.appfinanceiro.logicAndUi.adapter.model.ModelCreditCard;
import com.example.appfinanceiro.logicAndUi.adapter.model.Parcela;
import com.example.appfinanceiro.logicAndUi.adapter.service.ServiceCreditCard;
import com.example.appfinanceiro.logicAndUi.adapter.utilitiesClass.ViewUtilities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCreditCard implements AddCreditCardInterface {
    private ActivityCardCreditBinding binding;
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
            FinanceDatabase financeDatabase = FinanceDatabase.getDatabase(binding.getRoot().getContext());
            CreditCardDao creditCardDao = financeDatabase.creditDao();
            CategoriasDao categoriasDao = financeDatabase.categoriasDao();
            TransacoesDbCartao transacoesDbCartao = new TransacoesDbCartao();
            transacoesDbCartao.valor = valorDividido;
            String descricao = addCreditCard.getDescricao() + "Parcela: "+ addCreditCard.getParcelas();
            transacoesDbCartao.descricao = descricao;
            transacoesDbCartao.parcelas = 1;
            transacoesDbCartao.dia = addCreditCard.getDia();
            transacoesDbCartao.categoriaId = categoriasDao.getId(addCreditCard.getCategoria());
            transacoesDbCartao.cartaoId = categoriasDao.getId(addCreditCard.getBankcCard());
            transacoesDbCartao.mes = mes;
            transacoesDbCartao.ano = ano;
            transacoesDbCartao.data = addCreditCard.getData();



            //AddCreditCardInterface creditCardInterface = new AddCreditCard();
           // ServiceCreditCard serviceCreditCard = new ServiceCreditCard(creditCardInterface);

            creditCardDao.insert(transacoesDbCartao);
           /* ModelCreditCard creditCardData = new ModelCreditCard(valorDividido,
                    addCreditCard.getBankcCard(),
                    1,
                    descricao,
                    addCreditCard.getCategoria(),
                    addCreditCard.getDia(),
                    mes,
                    ano);*/
           // serviceCreditCard.addCreditCard(ViewUtilities.IdValue(), creditCardData);
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
