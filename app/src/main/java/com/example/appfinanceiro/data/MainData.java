package com.example.appfinanceiro.data;


import com.example.appfinanceiro.MainActivity;
import com.example.appfinanceiro.adapter.AdapterDataMainMes;
import com.example.appfinanceiro.adapter.AdapterDataMainYear;
import com.example.appfinanceiro.databinding.ActivityMainBinding;
import com.example.appfinanceiro.interfaces.AddBalanceInterface;
import com.example.appfinanceiro.interfaces.AddCreditCardInterface;
import com.example.appfinanceiro.interfaces.AddDespesasInterface;
import com.example.appfinanceiro.model.Mes;
import com.example.appfinanceiro.model.ModelBalance;
import com.example.appfinanceiro.model.ModelCreditCard;
import com.example.appfinanceiro.model.ModelExpense;
import com.example.appfinanceiro.model.Year;
import com.example.appfinanceiro.service.ServiceBalance;
import com.example.appfinanceiro.service.ServiceCreditCard;
import com.example.appfinanceiro.service.ServiceDespesas;

import java.util.Calendar;

public class MainData {
    private final Calendar calendar = Calendar.getInstance();
    AddCreditCardInterface addCreditCardInterface = new AddCreditCard();
    ServiceCreditCard serviceCreditCard = new ServiceCreditCard(addCreditCardInterface);
    AddDespesasInterface addDespesasInterface = new AddDespesasData();
    ServiceDespesas serviceDespesas = new ServiceDespesas(addDespesasInterface);
    AddBalanceInterface addBalanceInterface = new AddBalanceData();
    ServiceBalance serviceBalance = new ServiceBalance(addBalanceInterface);
    private int saldo;
    private int receitas;
    private int despesas;
    private double saldoCartao;
    private int mes;
    private int ano;

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo() {
        saldo = receitas - despesas;
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

    public void despesas() {
        dataPresente();
        int despesasFor = serviceDespesas.getDespesas().values().stream()
                .filter(expense -> expense.getMes() == mes && expense.getAno() == ano).
                mapToInt(expense -> Integer.parseInt(expense.getValor())).
                sum();
        despesas += despesasFor;
   }

   public void balance(){
       dataPresente();
       int balance = serviceBalance.getBalances().values().stream()
               .filter(balance1 -> balance1.getMes() == mes && balance1.getAno() == ano)
               .mapToInt(balance1 -> Integer.parseInt(balance1.getSaldo())).
               sum();
       receitas += balance;
    }

    public void creditCard(){
        dataPresente();
        double cartao = serviceCreditCard.getCreditCards().values().stream()
                .filter(creditCard -> creditCard.getMes() == mes && creditCard.getAno() == ano).
                mapToDouble(creditCard -> creditCard.getValor().doubleValue()).
                sum();
        saldoCartao += cartao;
    }

    private void dataPresente(){
        mes = calendar.get(Calendar.MONTH) + 1;
        ano = calendar.get(Calendar.YEAR);
    }

    public void changeDate(ActivityMainBinding binding, Object mesS, Object anoS) {
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

        int despesas = serviceDespesas.getDespesas().values().stream().filter(expense -> expense.getMes() == mesFor && expense.getAno() == anoFor).mapToInt(expense -> Integer.parseInt(expense.getValor())).sum();
        String despesasFormat = "Despesas: " + despesas;
        binding.DespesasId.setText(despesasFormat);

        int balance = serviceBalance.getBalances().values().stream().filter(balance1 -> balance1.getMes() == mesFor && balance1.getAno() == anoFor).mapToInt(balance1 -> Integer.parseInt(balance1.getSaldo())).sum();
        String balanceFormat = "Saldo: " + balance;
        binding.saldoId.setText(balanceFormat);

        double cartao = serviceCreditCard.getCreditCards().values().stream().filter(creditCard -> creditCard.getMes() == mesFor && creditCard.getAno() == anoFor).mapToDouble(creditCard -> creditCard.getValor().doubleValue()).sum();
        String cartaoFormat = "Saldo Cartão: " + cartao;
        binding.saldoCartO.setText(cartaoFormat);

        setSaldo();
    }

    public void addAdapter(ActivityMainBinding binding){
        if(Mes.getMesArrayList().isEmpty()) {
            for (int i = 0; i < Mes.numeroMes.length; i++) {
                Mes mes1 = new Mes(Mes.mesArray[i], Mes.numeroMes[i]);
                Mes.getMesArrayList().add(mes1);
            }
        }
        if(!Year.getAnos().isEmpty()) {
            Year.getAnos().clear();
        }else{
            Year year = new Year();
            year.gerarAnos();
        }
        AdapterDataMainYear adapterDataMainYear = new AdapterDataMainYear(binding.getRoot().getContext(), Year.getAnos());
        binding.anoId.setAdapter(adapterDataMainYear);

        AdapterDataMainMes adapterDataMainMes = new AdapterDataMainMes(binding.getRoot().getContext(), Mes.getMesArrayList());
        binding.mesId.setAdapter(adapterDataMainMes);
    }
}
