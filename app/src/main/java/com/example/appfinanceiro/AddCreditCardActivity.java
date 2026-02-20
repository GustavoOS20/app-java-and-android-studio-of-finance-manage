package com.example.appfinanceiro;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appfinanceiro.adapter.AdapterSpinner;
import com.example.appfinanceiro.data.AddCreditCard;
import com.example.appfinanceiro.databinding.ActivityCardCreditBinding;
import com.example.appfinanceiro.interfaces.AddCreditCardInterface;
import com.example.appfinanceiro.model.ModelCreditCard;
import com.example.appfinanceiro.model.Parcela;
import com.example.appfinanceiro.service.ServiceCreditCard;
import com.example.appfinanceiro.utilitiesClass.ViewUtilities;
import com.example.appfinanceiro.verifications.VerificationsAdd;
import com.google.android.material.snackbar.Snackbar;

import java.math.BigDecimal;
import java.util.Objects;

public class AddCreditCardActivity extends AppCompatActivity {
    private String data;
    private String dia;
    private String mes;
    private String ano;
    private BigDecimal bDValor;
    private int diaInt;
    private int mesInt;
    private int anoInt;
    private ActivityCardCreditBinding binding;

    @Override
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
        setSupportActionBar(binding.toolbar4);
        ViewUtilities.actionBar(this, true);
    }

    protected void confirmAdd() {
        AddCreditCardInterface creditCardInterface = new AddCreditCard();
        ServiceCreditCard serviceCreditCard = new ServiceCreditCard(creditCardInterface);
        if(serviceCreditCard.getListaDeParcelas().isEmpty()){
            serviceCreditCard.criarParcela();
        }
        AdapterSpinner adapterSpinner = new AdapterSpinner(this, serviceCreditCard.getListaDeParcelas());
        binding.spinner.setAdapter(adapterSpinner);
        binding.addCardButton.setOnClickListener(v -> {
            if(VerificationsAdd.verifyAddCard(binding, data, binding.spinner)){
                String valor = Objects.requireNonNull(binding.ValorCartao.getText()).toString();
                String descricao = Objects.requireNonNull(binding.idDescricaoCard.getText()).toString();
                String categoria = Objects.requireNonNull(binding.categoriaIdCard.getText()).toString();
                String banco = Objects.requireNonNull(binding.CreditBank.getText()).toString();
                Parcela parcela = (Parcela) binding.spinner.getSelectedItem();

                formatarValores(valor);

                ModelCreditCard modelCreditCard = new ModelCreditCard(bDValor, banco, parcela.getNumero(), descricao, categoria, diaInt, mesInt, anoInt);
                serviceCreditCard.addCreditCard(ViewUtilities.IdValue(), modelCreditCard);
                Snackbar.make(binding.getRoot(), "Valor: " + valor + " /" + "Descrição: " + descricao + " /" + "Categoria: " + categoria + " /" + "Banco: " + banco + " /" + "Parcelas: " + parcela.getNumero(), Snackbar.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });

    }

    private void inicializarLayout(){
        binding = ActivityCardCreditBinding.inflate(getLayoutInflater());
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
        Intent intent = new Intent(AddCreditCardActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void calendarOpacity() {
        binding.calendarCreditCard.setVisibility(GONE);
        binding.fabCalendarCard.setOnClickListener(view -> binding.calendarCreditCard.setVisibility(VISIBLE));
        binding.getRoot().setOnClickListener(view -> binding.calendarCreditCard.setVisibility(GONE));
    }

    private void valueCalendar() {
        binding.calendarCreditCard.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            dia = String.valueOf(dayOfMonth);
            mes = String.valueOf(month + 1);
            ano = String.valueOf(year);
            data = dayOfMonth + "/" + (month + 1) + "/" + year;
            binding.valorCalendarCard.setText(data);
        });
    }

    public void formatarValores(String valor){
        String valorLimpo = valor.replace(".", "").replace(",", ".");
        bDValor = new BigDecimal(valorLimpo);
        diaInt = Integer.parseInt(dia);
        mesInt = Integer.parseInt(mes);
        anoInt = Integer.parseInt(ano);
    }
}
