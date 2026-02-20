package com.example.appfinanceiro;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appfinanceiro.data.AddBalanceData;
import com.example.appfinanceiro.data.MainData;
import com.example.appfinanceiro.databinding.ActivityAddBalanceBinding;
import com.example.appfinanceiro.interfaces.AddBalanceInterface;
import com.example.appfinanceiro.model.ModelBalance;
import com.example.appfinanceiro.service.ServiceBalance;
import com.example.appfinanceiro.utilitiesClass.ViewUtilities;
import com.example.appfinanceiro.verifications.VerificationsAdd;

import java.util.Objects;

public class AddBalanceActivity extends AppCompatActivity {
    private String data;
    private int mes;
    private int ano;
    private ActivityAddBalanceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inicializarLayout();
        ViewUtilities.setMargins(binding.getRoot());
        directBackButton();
        confirmAdd();
        calendarOpacity();
        valueCalendar();
        actionBar();
    }

    private void actionBar(){
        setSupportActionBar(binding.toolbar2);
        ViewUtilities.actionBar(this, true);
    }

    private void inicializarLayout(){
        binding = ActivityAddBalanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void backButton() {
        Intent intent = new Intent(AddBalanceActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void directBackButton(){
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                backButton();
                finish();
            }
        });
    }

    private void confirmAdd() {
        binding.AddSaldoButton.setOnClickListener(view -> {
            if (VerificationsAdd.verifyinput(binding, data)) {
                String saldo = Objects.requireNonNull(binding.AddSaldoField.getText()).toString();
                String descricao = Objects.requireNonNull(binding.IdDescricao.getText()).toString();
                String contaDestino = Objects.requireNonNull(binding.ContaDestinoId.getText()).toString();
                String status = Objects.requireNonNull(binding.StatusId.getText()).toString();
                String categoria = Objects.requireNonNull(binding.CategoriaId.getText()).toString();

                ModelBalance balanceData = new ModelBalance(saldo, descricao, contaDestino, status, categoria, data, mes, ano);
                AddBalanceInterface balanceInterface = new AddBalanceData();
                ServiceBalance serviceBalance = new ServiceBalance(balanceInterface);
                serviceBalance.addBalance(ViewUtilities.IdValue(), balanceData);
                Toast.makeText(this, " " + saldo + "/" + descricao + "/" + contaDestino + "/" + status + "/" + categoria + "/" + data, Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

    private void calendarOpacity() {
        binding.calendarBalance.setVisibility(GONE);
        binding.fabCalendarBalance.setOnClickListener(view -> binding.calendarBalance.setVisibility(VISIBLE));
        binding.getRoot().setOnClickListener(view -> binding.calendarBalance.setVisibility(GONE));
    }

    private void valueCalendar() {
        binding.calendarBalance.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            ano = year;
            mes = month + 1;
            data = dayOfMonth + "/" + (month + 1) + "/" + year;
            binding.dataCalendar.setText(data);
        });
    }
}
