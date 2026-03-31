package com.example.appfinanceiro.logicAndUi.adapter.data;

import com.example.appfinanceiro.database.DAO.BalanceDao;
import com.example.appfinanceiro.database.DAO.CreditCardDao;
import com.example.appfinanceiro.database.DAO.DespesasDao;
import com.example.appfinanceiro.database.FinanceDatabase;
import com.example.appfinanceiro.logicAndUi.adapter.adapter.AdapterDataMainMes;
import com.example.appfinanceiro.logicAndUi.adapter.adapter.AdapterDataMainYear;
import com.example.appfinanceiro.databinding.ActivityMainBinding;
import com.example.appfinanceiro.logicAndUi.adapter.interfaces.AddBalanceInterface;
import com.example.appfinanceiro.logicAndUi.adapter.interfaces.AddCreditCardInterface;
import com.example.appfinanceiro.logicAndUi.adapter.interfaces.AddDespesasInterface;
import com.example.appfinanceiro.logicAndUi.adapter.model.Mes;
import com.example.appfinanceiro.logicAndUi.adapter.model.Year;
import com.example.appfinanceiro.logicAndUi.adapter.service.ServiceBalance;
import com.example.appfinanceiro.logicAndUi.adapter.service.ServiceCreditCard;
import com.example.appfinanceiro.logicAndUi.adapter.service.ServiceDespesas;

import java.util.Calendar;
import java.util.List;

public class MainData {
    private final Calendar calendar = Calendar.getInstance();
    private final AddCreditCardInterface addCreditCardInterface = new AddCreditCard();
    private final ServiceCreditCard serviceCreditCard = new ServiceCreditCard(addCreditCardInterface);
    private final AddDespesasInterface addDespesasInterface = new AddDespesasData();
    private final ServiceDespesas serviceDespesas = new ServiceDespesas(addDespesasInterface);
    private final AddBalanceInterface addBalanceInterface = new AddBalanceData();
    private final ServiceBalance serviceBalance = new ServiceBalance(addBalanceInterface);
    private static int saldo;
    private int receitas;
    private int despesas;
    private double saldoCartao;
    private int mes;
    private int ano;

    public int getSaldo() {
        return saldo;
    }

    public int getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }

    public int getDespesas() {
        return despesas;
    }

    public int getReceitas() {
        return receitas;
    }

    public double getSaldoCartao() {
        return saldoCartao;
    }

    public void setSaldoAdd(int valor) {
        saldo += valor;
    }

    public void setSaldoRemove(int valor) {
        saldo -= valor;
    }

    public void despesas(ActivityMainBinding binding) {
        dataPresente();
        FinanceDatabase db = FinanceDatabase.getDatabase(binding.getRoot().getContext());
        DespesasDao despesasDao = db.despesasDao();
        List<Integer> despesas = despesasDao.getDespesasPorData(mes, ano);
        int despesasFor = despesas.stream().mapToInt(Integer::intValue).sum();
        this.despesas += despesasFor;
   }

   public void balance(ActivityMainBinding binding){
       dataPresente();
       FinanceDatabase db = FinanceDatabase.getDatabase(binding.getRoot().getContext());
       BalanceDao balanceDao = db.balanceDao();
       List<Integer> balanceD = balanceDao.getBalancePorData(mes, ano);
       int balance = balanceD.stream().mapToInt(Integer::intValue).sum();
       receitas += balance;
    }

    public void creditCard(ActivityMainBinding binding){
        dataPresente();
        FinanceDatabase db = FinanceDatabase.getDatabase(binding.getRoot().getContext());
        CreditCardDao creditCardDao = db.creditDao();
        List<Integer> creditCard = creditCardDao.getCreditPorData(mes, ano);
        double cartao = creditCard.stream().mapToDouble(Integer::doubleValue).sum();
        saldoCartao += cartao;
    }

    public void dataPresente(){
        mes = calendar.get(Calendar.MONTH) + 1;
        ano = calendar.get(Calendar.YEAR);
    }

    public void changeDate(ActivityMainBinding binding, Object mesS, Object anoS) {
        FinanceDatabase db = FinanceDatabase.getDatabase(binding.getRoot().getContext());
        BalanceDao balanceDao = db.balanceDao();
        DespesasDao despesasDao = db.despesasDao();
        CreditCardDao creditCardDao = db.creditDao();
            List<Integer> despesasList = despesasDao.getDespesasPorData(mes, ano);
            List<Integer> balanceList = balanceDao.getBalancePorData(mes, ano);
            List<Integer> creditCardList = creditCardDao.getCreditPorData(mes, ano);


        if (anoS == null || mesS == null) {
            return;
        }
        String mesF  = mesS.toString();
        String anoF = anoS.toString();
        for(Mes mes : Mes.getMesArrayList()){
            if(mes.getMes().equalsIgnoreCase(mesF)){
                mesF = String.valueOf(mes.getMesNum());
            }
        }
        int mesFor = Integer.parseInt(mesF);
        int anoFor = Integer.parseInt(anoF);

        despesas = 0;
        receitas = 0;
        saldoCartao = 0;

        int despesas = despesasList.stream().mapToInt(Integer::intValue).sum();
        String despesasFormat = "Despesas: " + despesas;
        binding.DespesasId.setText(despesasFormat);

        int balance = balanceList.stream().mapToInt(Integer::intValue).sum();
        String balanceFormat = "Receita: " + balance;
        binding.receitasId.setText(balanceFormat);

        double cartao = creditCardList.stream().mapToDouble(Integer::doubleValue).sum();
        String cartaoFormat = "Saldo Cartão: " + cartao;
        binding.saldoCartO.setText(cartaoFormat);
    }

    public void addAdapter(ActivityMainBinding binding){
        if(Mes.getMesArrayList().isEmpty()) {
            for (int i = 0; i < Mes.numeroMes.length; i++) {
                Mes mes1 = new Mes(Mes.mesArray[i], Mes.numeroMes[i]);
                Mes.getMesArrayList().add(mes1);
            }
        }
        Year year = new Year();
        if(!Year.getAnos().isEmpty()) {
            Year.getAnos().clear();
            year.gerarAnos();
        }else{
            year.gerarAnos();
        }
        AdapterDataMainYear adapterDataMainYear = new AdapterDataMainYear(binding.getRoot().getContext(), Year.getAnos());
        binding.anoId.setAdapter(adapterDataMainYear);

        AdapterDataMainMes adapterDataMainMes = new AdapterDataMainMes(binding.getRoot().getContext(), Mes.getMesArrayList());
        binding.mesId.setAdapter(adapterDataMainMes);
    }
}
