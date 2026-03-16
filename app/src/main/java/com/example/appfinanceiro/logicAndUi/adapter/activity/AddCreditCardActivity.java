package com.example.appfinanceiro.logicAndUi.adapter.activity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appfinanceiro.database.DAO.BalanceDao;
import com.example.appfinanceiro.database.DAO.CategoriasDao;
import com.example.appfinanceiro.database.DAO.CreditCardDao;
import com.example.appfinanceiro.database.Entity.CategoriasDb;
import com.example.appfinanceiro.database.Entity.TransacoesDbBalance;
import com.example.appfinanceiro.database.Entity.TransacoesDbCartao;
import com.example.appfinanceiro.database.FinanceDatabase;
import com.example.appfinanceiro.logicAndUi.adapter.adapter.AdapterSpinner;
import com.example.appfinanceiro.logicAndUi.adapter.adapter.AdapterSpinnerAddAndRemove;
import com.example.appfinanceiro.logicAndUi.adapter.data.AddCreditCard;
import com.example.appfinanceiro.databinding.ActivityCardCreditBinding;
import com.example.appfinanceiro.logicAndUi.adapter.interfaces.AddCreditCardInterface;
import com.example.appfinanceiro.logicAndUi.adapter.model.Categorias;
import com.example.appfinanceiro.logicAndUi.adapter.model.ModelCreditCard;
import com.example.appfinanceiro.logicAndUi.adapter.model.Parcela;
import com.example.appfinanceiro.logicAndUi.adapter.service.ServiceCreditCard;
import com.example.appfinanceiro.logicAndUi.adapter.utilitiesClass.ViewUtilities;
import com.example.appfinanceiro.logicAndUi.adapter.verifications.VerificationsAdd;
import com.google.android.material.snackbar.Snackbar;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AddCreditCardActivity extends AppCompatActivity {
    private LocalDate data;
    private String dia;
    private String mes;
    private String ano;
    private BigDecimal bDValor;
    private int diaInt;
    private int mesInt;
    private int anoInt;
    private String dataF;
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
        for(String categoria : Categorias.getCategoriasBanco()) {
            saveDatabase(categoria, "Banco");
        }
        for (String categoria : Categorias.getCategoriasDespesas()) {
            saveDatabase(categoria, "Despesa");
        }
        AdapterSpinner adapterSpinner = new AdapterSpinner(this, serviceCreditCard.getListaDeParcelas());
        binding.spinner.setAdapter(adapterSpinner);
        binding.addCardButton.setOnClickListener(v -> {
            if(VerificationsAdd.verifyAddCard(binding, dataF, binding.spinner)){
                String valor = Objects.requireNonNull(binding.ValorCartao.getText()).toString();
                String descricao = Objects.requireNonNull(binding.idDescricaoCard.getText()).toString();
                String categoria = (String) binding.spinnerCredit.getSelectedItem();
                String banco = (String) binding.spinnerCreditBanco.getSelectedItem();
                Parcela parcela = (Parcela) binding.spinner.getSelectedItem();
                formatarValores(valor);
                //ModelCreditCard modelCreditCard = new ModelCreditCard(bDValor, banco, parcela.getNumero(), descricao, categoria, diaInt, mesInt, anoInt);
               //serviceCreditCard.addCreditCard(ViewUtilities.IdValue(), modelCreditCard);
                saveDbcredit(bDValor, descricao, parcela.getNumero(), categoria, banco);
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            dia = String.valueOf(dayOfMonth);
            mes = String.valueOf(month + 1);
            ano = String.valueOf(year);
            dataF = dayOfMonth + "/" + (month + 1) + "/" + year;
            data = LocalDate.parse(dataF, formatter);
            binding.valorCalendarCard.setText(dataF);
        });
    }

    public void formatarValores(String valor){
        String valorLimpo = valor.replace(".", "").replace(",", ".");
        bDValor = new BigDecimal(valorLimpo);
        diaInt = Integer.parseInt(dia);
        mesInt = Integer.parseInt(mes);
        anoInt = Integer.parseInt(ano);
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
                AdapterSpinnerAddAndRemove categoriaa = new AdapterSpinnerAddAndRemove(this, listaString);binding.spinnerCreditBanco.setAdapter(categoriaa);
                List<String> listaString2 = lista.stream().filter(c -> c.tipo.equalsIgnoreCase("Despesa")).map(n -> n.nome).collect(Collectors.toList());
                AdapterSpinnerAddAndRemove categorias = new AdapterSpinnerAddAndRemove(this, listaString2);
                binding.spinnerCredit.setAdapter(categorias);
            });
        }).start();
        }

    private void saveDbcredit(BigDecimal saldo, String descricao, int parcelas, String categoria, String banco){
        TransacoesDbCartao transacoesDbCredit = new TransacoesDbCartao();
        transacoesDbCredit.valor = saldo;
        transacoesDbCredit.descricao = descricao;
        transacoesDbCredit.parcelas = parcelas;
        transacoesDbCredit.dia = diaInt;
        transacoesDbCredit.mes = mesInt;
        transacoesDbCredit.ano = anoInt;
        transacoesDbCredit.data = data;
        FinanceDatabase.databaseWriteExecutor.execute(() -> {
            FinanceDatabase db = FinanceDatabase.getDatabase(this);
            CategoriasDao categoriasDao = db.categoriasDao();
            CreditCardDao creditCardDao = db.creditDao();
            transacoesDbCredit.categoriaId = categoriasDao.getId(categoria);
            transacoesDbCredit.cartaoId = categoriasDao.getId(banco);
            creditCardDao.insert(transacoesDbCredit);
        });
    }
    }
