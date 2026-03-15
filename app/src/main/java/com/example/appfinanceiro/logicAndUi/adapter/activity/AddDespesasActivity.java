package com.example.appfinanceiro.logicAndUi.adapter.activity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appfinanceiro.database.DAO.CategoriasDao;
import com.example.appfinanceiro.database.Entity.CategoriasDb;
import com.example.appfinanceiro.database.FinanceDatabase;
import com.example.appfinanceiro.logicAndUi.adapter.adapter.AdapterSpinnerAddAndRemove;
import com.example.appfinanceiro.logicAndUi.adapter.data.AddDespesasData;
import com.example.appfinanceiro.logicAndUi.adapter.data.MainData;
import com.example.appfinanceiro.databinding.ActivityAddDespesasBinding;
import com.example.appfinanceiro.logicAndUi.adapter.interfaces.AddDespesasInterface;
import com.example.appfinanceiro.logicAndUi.adapter.model.Categorias;
import com.example.appfinanceiro.logicAndUi.adapter.model.ModelExpense;
import com.example.appfinanceiro.logicAndUi.adapter.service.ServiceDespesas;
import com.example.appfinanceiro.logicAndUi.adapter.utilitiesClass.ViewUtilities;
import com.example.appfinanceiro.logicAndUi.adapter.verifications.VerificationsAdd;
import com.google.android.material.snackbar.Snackbar;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AddDespesasActivity extends AppCompatActivity{
    private ActivityAddDespesasBinding binding;
    private int mes;
    private int ano;
    private LocalDate data;
    private String dataF;

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
        for(String categoria : Categorias.getCategoriasBanco()) {
            saveDatabase(categoria, "Banco");
        }
        for (String categoria : Categorias.getCategoriasDespesas()) {
            saveDatabase(categoria, "Despesa");
        }
        binding.addSaldoButton.setOnClickListener(view -> {

            if (VerificationsAdd.verifyAddDespesas(binding, dataF)) {
                String valor = Objects.requireNonNull(binding.addValorField.getText()).toString();
                String descricao = Objects.requireNonNull(binding.idDescricaoDes.getText()).toString();
                String contaOrigem = (String) binding.spinnerDepespesasBanco.getSelectedItem();
                String categoria = (String) binding.spinnerDepespesas.getSelectedItem();

                BigDecimal valorBigDecimal = new BigDecimal(valor);
                ModelExpense despesasData = new ModelExpense(valorBigDecimal, descricao, data, contaOrigem, categoria , mes, ano);
                AddDespesasInterface addDespesasInterface = new AddDespesasData();
                ServiceDespesas serviceDespesas = new ServiceDespesas(addDespesasInterface);
                Snackbar.make(binding.getRoot(), "Valor" + valor + " /" + "Descrição" + descricao + " /" + "Conta Origem" + contaOrigem + " /" + "Categoria" + categoria, Snackbar.LENGTH_SHORT).show();
                serviceDespesas.addDespesas(ViewUtilities.IdValue(), despesasData);
                MainData mainData = new MainData();
                mainData.setSaldoRemove(Integer.parseInt(valor));
                setResult(Activity.RESULT_OK);
                finish();
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            mes = month + 1;
            ano = year;
            dataF = dayOfMonth + "/" + (month + 1) + "/" + year;
            data = LocalDate.parse(dataF, formatter);
            binding.valorCalendar.setText(dataF);
        });
    }

    public void saveDatabase(String categoria, String tipo){
        CategoriasDb categoriasDb = new CategoriasDb();
        categoriasDb.nome = categoria;
        categoriasDb.tipo = tipo;
        FinanceDatabase db = FinanceDatabase.getDatabase(this);
        CategoriasDao categoriasDao = db.categoriasDao();

        new Thread(() -> {
            categoriasDao.insert(categoriasDb);
            List<CategoriasDb> lista = categoriasDao.getAll();
            runOnUiThread(() -> {
                List<String> listaString = lista.stream().filter(c -> c.tipo.equalsIgnoreCase("Banco")).map(n -> n.nome).collect(Collectors.toList());
                AdapterSpinnerAddAndRemove categoriaa = new AdapterSpinnerAddAndRemove(this, listaString);
                binding.spinnerDepespesasBanco.setAdapter(categoriaa);
                List<String> listaString2 = lista.stream().filter(c -> c.tipo.equalsIgnoreCase("Despesa")).map(n -> n.nome).collect(Collectors.toList());
                AdapterSpinnerAddAndRemove categorias = new AdapterSpinnerAddAndRemove(this, listaString2);
                binding.spinnerDepespesas.setAdapter(categorias);
            });
        }).start();

    }
}
