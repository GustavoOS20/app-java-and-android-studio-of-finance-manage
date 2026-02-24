package com.example.appfinanceiro;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appfinanceiro.data.MainData;
import com.example.appfinanceiro.databinding.ActivityMainBinding;
import com.example.appfinanceiro.model.Year;
import com.example.appfinanceiro.utilitiesClass.ViewUtilities;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity{
    MainData mainData = new MainData();
    private ActivityResultLauncher<Intent> cadastroLauncher;
    private ActivityMainBinding binding;
    private final ColorStateList buttonColorDisabled = ColorStateList.valueOf(Color.parseColor("#571895D8"));
    private final ColorStateList buttonColorEnabled = ColorStateList.valueOf(Color.parseColor("#925FBC"));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inicializarLayout();
        alterValues();
        ViewUtilities.setMargins(binding.getRoot());
        menuNavegation();
        fabAddAndRemove();
        actionBar();
        defineValues();
        salvarDados();
        mudarData();
    }

    private void inicializarLayout(){
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void salvarDados(){
        cadastroLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        defineValues();
                        Toast.makeText(this, "Finança salva!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void defineValues(){
        mainData.despesas();
        mainData.balance();
        mainData.creditCard();
        mainData.setSaldo();
        String despesas = "Despesas: " + mainData.getDespesas();
        String receitas = "Receitas: " + mainData.getReceitas();
        String saldo = "Saldo: " + mainData.getSaldo();
        String saldoCartao = "Saldo Cartão: " + mainData.getSaldoCartao();
        binding.DespesasId.setText(despesas);
        binding.saldoId.setText(saldo);
        binding.receitasId.setText(receitas);
        binding.saldoCartO.setText(saldoCartao);

    }

    private void actionBar(){
        setSupportActionBar(binding.toolbar);
        ViewUtilities.actionBar(this, false);
    }

    private void fabAddAndRemove() {
        binding.AddSaldo.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddBalanceActivity.class);
            cadastroLauncher.launch(intent);
        });
        binding.addDespesas.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddDespesasActivity.class);
            cadastroLauncher.launch(intent);
        });
        binding.addCartao.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddCreditCardActivity.class);
            cadastroLauncher.launch(intent);
        });
    }

    private void alterValues(){
        binding.main.setOnClickListener(view -> {
            binding.AddSaldo.setVisibility(GONE);
            binding.addDespesas.setVisibility(GONE);
            binding.addCartao.setVisibility(GONE);
        });
        binding.AddSaldo.setVisibility(GONE);
        binding.addDespesas.setVisibility(GONE);
        binding.addCartao.setVisibility(GONE);
        binding.AddValues.setOnClickListener(view -> {
            binding.AddSaldo.setVisibility(VISIBLE);
            binding.addDespesas.setVisibility(VISIBLE);
            binding.addCartao.setVisibility(VISIBLE);
        });
    }

    private void menuNavegation(){
        binding.bottomNavigation.setOnItemSelectedListener(v-> {
            if (binding.bottomNavigation.getSelectedItemId() != v.getItemId()) {
                Intent intent = new Intent(MainActivity.this, ExtratoActivity.class);
                cadastroLauncher.launch(intent);
                overridePendingTransition(0, 0);
            }
            return true;
        });
    }

    private void mudarData(){
        mainData.addAdapter(binding);
        Calendar calendar = Calendar.getInstance();
        int mes = calendar.get(Calendar.MONTH);
        binding.mesId.setSelection(mes);
        int ano = calendar.get(Calendar.YEAR);

        int posicao = Year.getAnos().indexOf(ano);

        if (posicao != -1) {
            binding.anoId.setSelection(posicao);
        }

        resgatarValueSpinner(binding.mesId);
        resgatarValueSpinner(binding.anoId);
    }

    private void resgatarValueSpinner(Spinner spinner){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mainData.changeDate(binding, binding.mesId.getSelectedItem(), binding.anoId.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
