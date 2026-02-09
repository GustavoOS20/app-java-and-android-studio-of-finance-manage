package com.example.appfinanceiro;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appfinanceiro.data.AddCreditCard;
import com.example.appfinanceiro.databinding.ActivityCardCreditBinding;
import com.example.appfinanceiro.interfaces.AddBalanceInterface;
import com.example.appfinanceiro.interfaces.AddCreditCardInterface;
import com.example.appfinanceiro.model.ModelCreditCard;
import com.example.appfinanceiro.service.ServiceCreditCard;
import com.example.appfinanceiro.utilitiesClass.ViewUtilities;
import com.example.appfinanceiro.verifications.VerificationsAdd;

import java.math.BigDecimal;
import java.util.Objects;

public class AddCreditCardActivity extends AppCompatActivity {
    private String data;
    private String dia;
    private String mes;
    private String ano;
    private ActivityCardCreditBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inicializarLayout();
        ViewUtilities.setMargins(binding.getRoot());
        calendarOpacity();
        valueCalendar();
        confirmAdd();
    }

    protected void confirmAdd() {
        binding.addCardButton.setOnClickListener(v -> {
            if(VerificationsAdd.verifyAddCard(binding, data)){
                String valor = Objects.requireNonNull(binding.ValorCartao.getText()).toString();
                String descricao = Objects.requireNonNull(binding.idDescricaoCard.getText()).toString();
                String categoria = Objects.requireNonNull(binding.categoriaIdCard.getText()).toString();
                String banco = Objects.requireNonNull(binding.CreditBank.getText()).toString();
                AddCreditCardInterface creditCardInterface = new AddCreditCard();
                ServiceCreditCard serviceCreditCard = new ServiceCreditCard(creditCardInterface);
                BigDecimal bDValor = new BigDecimal(valor);
               // ModelCreditCard modelCreditCard = new ModelCreditCard(bDValor, banco, , descricao, categoria, dia, mes, ano);
              //  serviceCreditCard.addCreditCard(ViewUtilities.IdValue(), modelCreditCard);
            }
        });

    }

    private void inicializarLayout(){
        binding = ActivityCardCreditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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
}
