package com.example.appfinanceiro.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfinanceiro.databinding.ItemExtratoBinding;

public class AdapterExtrato extends RecyclerView.Adapter<AdapterExtrato.extratoViewHolder>{
    private ItemExtratoBinding binding;

    @NonNull
    @Override
    public extratoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemExtratoBinding itemView = ItemExtratoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new extratoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull extratoViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class extratoViewHolder extends RecyclerView.ViewHolder {
        private ItemExtratoBinding binding;

        public extratoViewHolder(@NonNull ItemExtratoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
