package com.example.appfinanceiro;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.SearchView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appfinanceiro.adapter.AdapterExtrato;
import com.example.appfinanceiro.data.AddBalanceData;
import com.example.appfinanceiro.data.AddCreditCard;
import com.example.appfinanceiro.data.AddDespesasData;
import com.example.appfinanceiro.data.ExtratoData;
import com.example.appfinanceiro.databinding.ActivityExtratoBinding;
import com.example.appfinanceiro.interfaces.AddBalanceInterface;
import com.example.appfinanceiro.interfaces.AddCreditCardInterface;
import com.example.appfinanceiro.interfaces.AddDespesasInterface;
import com.example.appfinanceiro.service.ServiceBalance;
import com.example.appfinanceiro.service.ServiceCreditCard;
import com.example.appfinanceiro.service.ServiceDespesas;
import com.example.appfinanceiro.utilitiesClass.ViewUtilities;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExtratoActivity extends AddBalanceActivity {
    private ActivityExtratoBinding binding;
    private AdapterExtrato adapterExtrato;

    private final AddBalanceInterface addBalanceInterface = new AddBalanceData();
    private final ServiceBalance serviceBalance = new ServiceBalance(addBalanceInterface);
    private final AddDespesasInterface addDespesasInterface = new AddDespesasData();
    private final ServiceDespesas serviceDespesas = new ServiceDespesas(addDespesasInterface);
    private final AddCreditCardInterface addCreditCardInterface = new AddCreditCard();
    private final ServiceCreditCard serviceCreditCard = new ServiceCreditCard(addCreditCardInterface);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inicializarLayout();
        ViewUtilities.setMargins(binding.getRoot());
        menuNavegation();
        actionBar();
        adicionarElementos();
    }

    private void actionBar() {
        setSupportActionBar(binding.toolbar6);
        ViewUtilities.actionBar(this, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        });
        return true;
    }

    private void inicializarLayout() {
        binding = ActivityExtratoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void adicionarElementos() {
        adapterExtrato = new AdapterExtrato();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapterExtrato);
        
        atualizarFiltro();
        
        binding.chipCartao.setOnCheckedChangeListener((chip, isChecked) -> atualizarFiltro());
    }

    private void atualizarFiltro() {
        List<ExtratoData> listaFiltrada;
        if (binding.chipCartao.isChecked()) {
            listaFiltrada = filtrarListasCredit();
        } else {
            listaFiltrada = filtrarListasAlls();
        }

        if (adapterExtrato != null) {
            adapterExtrato.setList(listaFiltrada);
        }
    }

    private List<ExtratoData> filtrarListasAlls() {
        Stream<ExtratoData> balanceStream = serviceBalance.getBalances().values().stream()
                .map(b -> new ExtratoData(b.getData(), b.getDescricao(), b.getSaldo().intValue(), b.getCategoria()));

        Stream<ExtratoData> expenseStream = serviceDespesas.getDespesas().values().stream()
                .map(e -> new ExtratoData(e.getData(), e.getDescricao(), e.getValor().negate().intValue(), e.getCategoria()));


        return Stream.of(balanceStream, expenseStream)
                .flatMap(s -> s)
                .sorted(Comparator.comparing(ExtratoData::getData).reversed())
                .collect(Collectors.toList());
    }

    private List<ExtratoData> filtrarListasCredit() {
        return serviceCreditCard.getCreditCards().values().stream()
                .map(c -> new ExtratoData(c.getData(), c.getDescricao(), c.getValor().intValue(), c.getCategoria()))
                .sorted(Comparator.comparing(ExtratoData::getData).reversed())
                .collect(Collectors.toList());
    }

    private void menuNavegation(){
        binding.bottomNavigationEx.setSelectedItemId(R.id.nav_extrato);
        binding.bottomNavigationEx.setOnItemSelectedListener(v -> {
            if (binding.bottomNavigationEx.getSelectedItemId() != v.getItemId()) {
                Intent intent = new Intent(ExtratoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
            return true;
        });
    }
}
