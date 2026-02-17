package com.example.appfinanceiro;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appfinanceiro.data.AddDespesasData;
import com.example.appfinanceiro.databinding.ActivityAddDespesasBinding;
import com.example.appfinanceiro.interfaces.AddDespesasInterface;
import com.example.appfinanceiro.model.ModelExpense;
import com.example.appfinanceiro.service.ServiceDespesas;
import com.example.appfinanceiro.utilitiesClass.ViewUtilities;
import com.example.appfinanceiro.verifications.VerificationsAdd;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;
import java.util.UUID;

public class AddDespesasActivity extends AppCompatActivity{
    private ActivityAddDespesasBinding binding;
    private String data;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inicializarLayout();
        ViewUtilities.setMargins(binding.getRoot());
        calendarOpacity();
        valueCalendar();
        confirmAdd();
        actionBar();
    }

    private void actionBar(){
        setSupportActionBar(binding.toolbar3);
        ViewUtilities.actionBar(this, true);
    }

    private void confirmAdd() {
        binding.addSaldoButton.setOnClickListener(view -> {
            if (VerificationsAdd.verifyAddDespesas(binding, data)) {
                String valor = Objects.requireNonNull(binding.addValorField.getText()).toString();
                String descricao = Objects.requireNonNull(binding.idDescricaoDes.getText()).toString();
                String contaOrigem = Objects.requireNonNull(binding.contaOrigemId.getText()).toString();
                String categoria = Objects.requireNonNull(binding.categoriaIdDes.getText()).toString();;

                ModelExpense despesasData = new ModelExpense(valor, descricao, contaOrigem, categoria, data);
                AddDespesasInterface addDespesasInterface = new AddDespesasData();
                ServiceDespesas serviceDespesas = new ServiceDespesas(addDespesasInterface);
                Snackbar.make(binding.getRoot(), "Valor" + valor + " /" + "Descrição" + descricao + " /" + "Conta Origem" + contaOrigem + " /" + "Categoria" + categoria, Snackbar.LENGTH_SHORT).show();

                serviceDespesas.addDespesas(ViewUtilities.IdValue(), despesasData);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
            data = dayOfMonth + "/" + (month + 1) + "/" + year;
            binding.valorCalendar.setText(data);
        });
    }
}
