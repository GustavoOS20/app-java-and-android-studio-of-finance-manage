package com.example.appfinanceiro.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfinanceiro.data.ExtratoData;
import com.example.appfinanceiro.databinding.ItemExtratoBinding;

import java.util.ArrayList;
import java.util.List;

public class AdapterExtrato extends RecyclerView.Adapter<AdapterExtrato.extratoViewHolder>{
    private final List<ExtratoData> listExibicao = new ArrayList<>();

    public void setList(List<ExtratoData> newList) {
        this.listExibicao.clear();
        this.listExibicao.addAll(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public extratoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemExtratoBinding itemView = ItemExtratoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new extratoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull extratoViewHolder holder, int position) {
        ExtratoData extratoData = listExibicao.get(position);
        holder.binding.dataId.setText(String.valueOf(extratoData.getData()));
        holder.binding.descricaoId.setText(extratoData.getDescricao());
        holder.binding.saldoId.setText(String.valueOf(extratoData.getValor()));
    }

    @Override
    public int getItemCount() {
        return listExibicao.size();
    }

    public static class extratoViewHolder extends RecyclerView.ViewHolder {
        private final ItemExtratoBinding binding;

        public extratoViewHolder(@NonNull ItemExtratoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
