package com.example.appfinanceiro.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfinanceiro.data.AddBalanceData;
import com.example.appfinanceiro.data.AddCreditCard;
import com.example.appfinanceiro.data.AddDespesasData;
import com.example.appfinanceiro.data.ExtratoData;
import com.example.appfinanceiro.databinding.ItemExtratoBinding;
import com.example.appfinanceiro.interfaces.AddBalanceInterface;
import com.example.appfinanceiro.interfaces.AddCreditCardInterface;
import com.example.appfinanceiro.interfaces.AddDespesasInterface;
import com.example.appfinanceiro.service.ServiceBalance;
import com.example.appfinanceiro.service.ServiceCreditCard;
import com.example.appfinanceiro.service.ServiceDespesas;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdapterExtrato extends RecyclerView.Adapter<AdapterExtrato.extratoViewHolder>{
    AddBalanceInterface addBalanceInterface = new AddBalanceData();
    ServiceBalance serviceBalance = new ServiceBalance(addBalanceInterface);
    AddDespesasInterface addDespesasInterface = new AddDespesasData();
    ServiceDespesas serviceDespesas = new ServiceDespesas(addDespesasInterface);
    AddCreditCardInterface addCreditCardInterface = new AddCreditCard();
    ServiceCreditCard serviceCreditCard = new ServiceCreditCard(addCreditCardInterface);
    private ItemExtratoBinding binding;

    @NonNull
    @Override
    public extratoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemExtratoBinding itemView = ItemExtratoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new extratoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull extratoViewHolder holder, int position) {
        ExtratoData extratoData = filtrarListas().get(position);
        holder.binding.dataId.setText(extratoData.getData());
        holder.binding.descricaoId.setText(extratoData.getDescricao());
        holder.binding.saldoId.setText(String.valueOf(extratoData.getValor()));
    }

    @Override
    public int getItemCount() {
        return filtrarListas().size();
    }

    private List<ExtratoData> filtrarListas(){
        Stream<ExtratoData> balanceStream = serviceBalance.getBalances().values().stream()
                .map(b -> new ExtratoData(b.getData(), b.getDescricao(), b.getSaldo().intValue()));

        Stream<ExtratoData> expenseStream = serviceDespesas.getDespesas().values().stream()
                .map(e -> new ExtratoData(e.getData(), e.getDescricao(), e.getValor().intValue()));

        Stream<ExtratoData> creditCardStream = serviceCreditCard.getCreditCards().values().stream()
                .map(c -> new ExtratoData(c.getData(), c.getDescricao(), c.getValor().intValue()));

        return Stream.of(balanceStream, expenseStream, creditCardStream)
                .flatMap(s -> s)
                .sorted(Comparator.comparing(ExtratoData::getData).reversed())
                .collect(Collectors.toList());
    }


    public static class extratoViewHolder extends RecyclerView.ViewHolder {
        private ItemExtratoBinding binding;

        public extratoViewHolder(@NonNull ItemExtratoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }
    }
}
