package com.example.appfinanceiro.verifications;

import com.example.appfinanceiro.databinding.ActivityAddBalanceBinding;
import com.example.appfinanceiro.databinding.ActivityAddDespesasBinding;
import com.example.appfinanceiro.databinding.ActivityCardCreditBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class VerificationsAdd {
    public static boolean verifyinput(ActivityAddBalanceBinding binding) {
        boolean noVerified = false;
        if (Objects.requireNonNull(binding.AddSaldoField.getText()).toString().isEmpty() ||
                Objects.requireNonNull(binding.IdDescricao.getText()).toString().isEmpty() ||
                Objects.requireNonNull(binding.ContaDestinoId.getText()).toString().isEmpty() ||
                Objects.requireNonNull(binding.StatusId.getText()).toString().isEmpty() ||
                Objects.requireNonNull(binding.CategoriaId.getText()).toString().isEmpty() ||
                binding.calendarBalance.toString().isEmpty() ||
                binding.calendarBalance.toString().equalsIgnoreCase("Data") ) {
            Snackbar.make(binding.getRoot(), "Preencha todos os campos", Snackbar.LENGTH_SHORT).show();
            noVerified = true;
        }
        return !noVerified;
    }

    public static boolean verifyAddDespesas(ActivityAddDespesasBinding binding){
        boolean noVerified = false;
        if(Objects.requireNonNull(binding.addValorField.getText()).toString().isEmpty() ||
                Objects.requireNonNull(binding.contaOrigemId.getText()).toString().isEmpty()
                || Objects.requireNonNull(binding.categoriaIdDes.getText()).toString().isEmpty()
                || Objects.requireNonNull(binding.idDescricaoDes.getText()).toString().isEmpty()
                || binding.calendarDespesas.toString().isEmpty()) {
            Snackbar.make(binding.getRoot(), "Preencha todos os campos", Snackbar.LENGTH_SHORT).show();
            noVerified = true;
        }
        return !noVerified;
    }

    public static boolean verifyAddCard(ActivityCardCreditBinding binding, String data){
        boolean noVerified = false;
        if(Objects.requireNonNull(binding.CreditBank.getText()).toString().isEmpty() ||
                Objects.requireNonNull(binding.categoriaIdCard.getText()).toString().isEmpty() ||
                Objects.requireNonNull(binding.idDescricaoCard.getText()).toString().isEmpty() ||
                Objects.requireNonNull(binding.ValorCartao.getText()).toString().isEmpty() ||
                data.isEmpty()) {
            Snackbar.make(binding.getRoot(), "Preencha todos os campos", Snackbar.LENGTH_SHORT).show();
            noVerified = true;
        }
        return !noVerified;
    }
}
