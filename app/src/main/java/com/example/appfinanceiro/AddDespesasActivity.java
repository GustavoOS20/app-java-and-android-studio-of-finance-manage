package com.example.appfinanceiro;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appfinanceiro.data.AddDespesasData;
import com.example.appfinanceiro.databinding.ActivityAddDespesasBinding;
import com.example.appfinanceiro.interfaces.AddDespesasInterface;
import com.example.appfinanceiro.model.ModelExpense;
import com.example.appfinanceiro.service.ServiceDespesas;
import com.example.appfinanceiro.utilitiesClass.ViewUtilities;
import com.example.appfinanceiro.verifications.VerificationsAdd;

import java.util.Objects;
import java.util.UUID;

public class AddDespesasActivity extends AppCompatActivity{
    private ActivityAddDespesasBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inicializarLayout();
        ViewUtilities.setMargins(binding.getRoot());
        calendarOpacity();
        valueCalendar();
        confirmAdd();
    }

    private void confirmAdd() {
        binding.addSaldoButton.setOnClickListener(view -> {
            if (VerificationsAdd.verifyAddDespesas(binding)) {
                String valor = Objects.requireNonNull(binding.addValorField.getText()).toString();
                String descricao = Objects.requireNonNull(binding.idDescricaoDes.getText()).toString();
                String contaOrigem = Objects.requireNonNull(binding.contaOrigemId.getText()).toString();
                String categoria = Objects.requireNonNull(binding.categoriaIdDes.getText()).toString();
                String data = binding.valorCalendar.getText() != null ? binding.valorCalendar.getText().toString() : "";

                ModelExpense despesasData = new ModelExpense(valor, descricao, contaOrigem, categoria, data);
                AddDespesasInterface addDespesasInterface = new AddDespesasData();
                ServiceDespesas serviceDespesas = new ServiceDespesas(addDespesasInterface);

                serviceDespesas.addDespesas(ViewUtilities.IdValue(), despesasData);
            }
        });
    }
    private void inicializarLayout(){
        binding = ActivityAddDespesasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void calendarOpacity() {
        binding.calendarDespesas.setVisibility(GONE);
        binding.fabCalendar.setOnClickListener(view -> {
            binding.calendarDespesas.setVisibility(VISIBLE);
        });
        binding.getRoot().setOnClickListener(view -> {
            binding.calendarDespesas.setVisibility(GONE);
        });
    }

    private void valueCalendar() {
        binding.calendarDespesas.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String date = dayOfMonth + "/" + (month + 1) + "/" + year;
            binding.valorCalendar.setText(date);
        });
    }
}
