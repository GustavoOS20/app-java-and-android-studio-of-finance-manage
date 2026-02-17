package com.example.appfinanceiro.verifications;

import android.text.TextUtils;
import android.widget.Spinner;

import com.example.appfinanceiro.databinding.ActivityAddBalanceBinding;
import com.example.appfinanceiro.databinding.ActivityAddDespesasBinding;
import com.example.appfinanceiro.databinding.ActivityCardCreditBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class VerificationsAdd {
    public static boolean verifyinput(ActivityAddBalanceBinding binding, String data) {
        boolean noVerified = false;
        if (TextUtils.isEmpty(binding.AddSaldoField.getText()) ||
                TextUtils.isEmpty(binding.IdDescricao.getText()) ||
                TextUtils.isEmpty(binding.ContaDestinoId.getText()) ||
                TextUtils.isEmpty(binding.StatusId.getText()) ||
                TextUtils.isEmpty(binding.CategoriaId.getText()) ||
                TextUtils.isEmpty(data) ||
                data.equalsIgnoreCase("Data")) {
            Snackbar.make(binding.getRoot(), "Preencha todos os campos", Snackbar.LENGTH_SHORT).show();
            noVerified = true;
        }
        return !noVerified;
    }

    public static boolean verifyAddDespesas(ActivityAddDespesasBinding binding, String data){
        boolean noVerified = false;
        if(TextUtils.isEmpty(binding.addValorField.getText()) ||
                TextUtils.isEmpty(binding.contaOrigemId.getText())
                || TextUtils.isEmpty(binding.categoriaIdDes.getText())
                || TextUtils.isEmpty(binding.idDescricaoDes.getText())
                || TextUtils.isEmpty(data)
                || data.equalsIgnoreCase("Data")) {
            Snackbar.make(binding.getRoot(), "Preencha todos os campos", Snackbar.LENGTH_SHORT).show();
            noVerified = true;
        }
        return !noVerified;
    }

    public static boolean verifyAddCard(ActivityCardCreditBinding binding, String data, Spinner spinner){
        boolean noVerified = false;
        if(TextUtils.isEmpty(binding.CreditBank.getText()) ||
                TextUtils.isEmpty(binding.categoriaIdCard.getText()) ||
                TextUtils.isEmpty(binding.idDescricaoCard.getText()) ||
                TextUtils.isEmpty(binding.ValorCartao.getText()) ||
                TextUtils.isEmpty(data) ||
                spinner.getSelectedItem() == null ||
                data.equalsIgnoreCase("Data")) {
            Snackbar.make(binding.getRoot(), "Preencha todos os campos", Snackbar.LENGTH_SHORT).show();
            noVerified = true;
        }
        return !noVerified;
    }
}
