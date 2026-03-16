package com.example.appfinanceiro.logicAndUi.adapter.activity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appfinanceiro.database.DAO.BalanceDao;
import com.example.appfinanceiro.database.DAO.CategoriasDao;
import com.example.appfinanceiro.database.Entity.CategoriasDb;
import com.example.appfinanceiro.database.Entity.TransacoesDbBalance;
import com.example.appfinanceiro.database.FinanceDatabase;
import com.example.appfinanceiro.logicAndUi.adapter.adapter.AdapterSpinnerAddAndRemove;
import com.example.appfinanceiro.logicAndUi.adapter.data.AddBalanceData;
import com.example.appfinanceiro.logicAndUi.adapter.data.MainData;
import com.example.appfinanceiro.databinding.ActivityAddBalanceBinding;
import com.example.appfinanceiro.logicAndUi.adapter.interfaces.AddBalanceInterface;
import com.example.appfinanceiro.logicAndUi.adapter.model.Categorias;
import com.example.appfinanceiro.logicAndUi.adapter.model.ModelBalance;
import com.example.appfinanceiro.logicAndUi.adapter.service.ServiceBalance;
import com.example.appfinanceiro.logicAndUi.adapter.utilitiesClass.ViewUtilities;
import com.example.appfinanceiro.logicAndUi.adapter.verifications.VerificationsAdd;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AddBalanceActivity extends AppCompatActivity {
    private LocalDate data;
    private String dataF;
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
        adapterAdd();

        binding.AddSaldoButton.setOnClickListener(view -> {
            if (VerificationsAdd.verifyinput(binding, dataF)) {
                MainData mainData = new MainData();
                AddBalanceInterface balanceInterface = new AddBalanceData();
                //ServiceBalance serviceBalance = new ServiceBalance(balanceInterface);
                String saldo = Objects.requireNonNull(binding.AddSaldoField.getText()).toString();
                String descricao = Objects.requireNonNull(binding.IdDescricao.getText()).toString();
                String contaDestino = (String) binding.spinnerContaBanco.getSelectedItem();
                String status = (String) binding.spinnerStatus.getSelectedItem();
                String categoria = (String) binding.spinnerBalance.getSelectedItem();

                BigDecimal bigDecimal = new BigDecimal(saldo);
                //ModelBalance balanceData = new ModelBalance(bigDecimal, descricao, contaDestino, status, categoria, data, mes, ano);
                //serviceBalance.addBalance(ViewUtilities.IdValue(), balanceData);
                saveDbBalance(bigDecimal, descricao, status, categoria, contaDestino);
                mainData.setSaldoAdd(Integer.parseInt(saldo));
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        binding.calendarBalance.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            ano = year;
            mes = month + 1;
            dataF = dayOfMonth + "/" + (month + 1) + "/" + year;
            data = LocalDate.parse(dataF, formatter);
            binding.dataCalendar.setText(dataF);
        });
    }

    private void adapterAdd(){
        List<String> statuss = List.of("À vista", "À pagar");
        AdapterSpinnerAddAndRemove adapterAdd = new AdapterSpinnerAddAndRemove(this, statuss);
        for(String categoria : Categorias.getCategoriasBanco()) {
            saveDatabaseCategorias(categoria, "Banco");
        }
        for (String categoria : Categorias.getCategoriasBalance()) {
            saveDatabaseCategorias(categoria, "Balance");
        }
        binding.spinnerStatus.setAdapter(adapterAdd);
    }

    private void saveDatabaseCategorias(String categoria, String tipo){
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
                binding.spinnerContaBanco.setAdapter(categoriaa);
                List<String> listaString2 = lista.stream().filter(c -> c.tipo.equalsIgnoreCase("Balance")).map(n -> n.nome).collect(Collectors.toList());
                AdapterSpinnerAddAndRemove categorias = new AdapterSpinnerAddAndRemove(this, listaString2);
                binding.spinnerBalance.setAdapter(categorias);
            });
        }).start();
    }

    private void saveDbBalance(BigDecimal saldo, String descricao, String status, String categoria, String banco){
        TransacoesDbBalance transacoesDbBalance = new TransacoesDbBalance();
        transacoesDbBalance.saldo = saldo;
        transacoesDbBalance.descricao = descricao;
        transacoesDbBalance.status = status;
        transacoesDbBalance.data = data;
        transacoesDbBalance.mes = mes;
        transacoesDbBalance.ano = ano;
        FinanceDatabase.databaseWriteExecutor.execute(() -> {
            FinanceDatabase db = FinanceDatabase.getDatabase(this);
            CategoriasDao categoriasDao = db.categoriasDao();
            BalanceDao balanceDao = db.balanceDao();
            transacoesDbBalance.categoriaId = categoriasDao.getId(categoria);
            transacoesDbBalance.cartaoId = categoriasDao.getId(banco);
            balanceDao.insert(transacoesDbBalance);

        });
    }
}
