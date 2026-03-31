package com.example.appfinanceiro.logicAndUi.adapter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfinanceiro.R;
import com.example.appfinanceiro.database.DAO.BalanceDao;
import com.example.appfinanceiro.database.DAO.CreditCardDao;
import com.example.appfinanceiro.database.DAO.DespesasDao;
import com.example.appfinanceiro.database.Entity.TransacoesDbBalance;
import com.example.appfinanceiro.database.FinanceDatabase;
import com.example.appfinanceiro.logicAndUi.adapter.adapter.AdapterExtrato;
import com.example.appfinanceiro.logicAndUi.adapter.data.AddBalanceData;
import com.example.appfinanceiro.logicAndUi.adapter.data.AddCreditCard;
import com.example.appfinanceiro.logicAndUi.adapter.data.AddDespesasData;
import com.example.appfinanceiro.logicAndUi.adapter.data.ExtratoData;
import com.example.appfinanceiro.databinding.ActivityExtratoBinding;
import com.example.appfinanceiro.logicAndUi.adapter.interfaces.AddBalanceInterface;
import com.example.appfinanceiro.logicAndUi.adapter.interfaces.AddCreditCardInterface;
import com.example.appfinanceiro.logicAndUi.adapter.interfaces.AddDespesasInterface;
import com.example.appfinanceiro.logicAndUi.adapter.service.ServiceBalance;
import com.example.appfinanceiro.logicAndUi.adapter.service.ServiceCreditCard;
import com.example.appfinanceiro.logicAndUi.adapter.service.ServiceDespesas;
import com.example.appfinanceiro.logicAndUi.adapter.utilitiesClass.ViewUtilities;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExtratoActivity extends AddBalanceActivity {
    private ActivityExtratoBinding binding;
    private AdapterExtrato adapterExtrato;
    private int quantidadePorPagina = 20;
    private int paginaAtual = 0;
    private boolean carregando = false;
    private boolean temMais = true;

    private static List<ExtratoData> extratoBalance;
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
        configurarScrollListener();
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
        CompletableFuture.supplyAsync(() -> {
            if (binding.chipCartao.isChecked()) {
                return filtrarListasCredit();
            } else {

                return filtrarListasAlls();

            }
        }).thenAccept(lista -> {
            runOnUiThread(() -> {
                if(extratoBalance.isEmpty()){
                    temMais = false;
                }else {
                    if (paginaAtual == 0) {
                        // Se for a primeira página, substituímos a lista inteira
                        if (adapterExtrato != null) {
                            adapterExtrato.setList(lista); // lista já contém o Balance + Despesas
                        }
                    } else {
                        // Se for página > 0, pegamos a lista ATUAL do adapter e apenas ADICIONAMOS os novos itens a ela
                        List<ExtratoData> listaAtual = adapterExtrato.getList();
                        int tamanhoAntigo = listaAtual.size();
                        
                        listaAtual.addAll(lista); // adiciona os itens novos (Balance + Despesas novos)
                        
                        // Avisamos o adapter que inserimos itens a partir da posição "tamanhoAntigo"
                        adapterExtrato.notifyItemRangeInserted(tamanhoAntigo, lista.size());
                    }
                    paginaAtual++;
                }
                carregando = false;
            });
        });
    }

    private List<ExtratoData> filtrarListasAlls() {
        carregando = true;
        int offset = paginaAtual * quantidadePorPagina;
        FinanceDatabase db = FinanceDatabase.getDatabase(this);
        BalanceDao balanceDao = db.balanceDao();
        DespesasDao despesasDao = db.despesasDao();
        List<ExtratoData> extratoDespesas = despesasDao.extratoDespesas( quantidadePorPagina, offset);
        extratoBalance = balanceDao.extratoBalance(quantidadePorPagina, offset);

        return Stream.of(extratoBalance, extratoDespesas)
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(ExtratoData::getData).reversed())
                .collect(Collectors.toList());
    }

    private List<ExtratoData> filtrarListasCredit() {
        carregando = true;
        int offset = paginaAtual * quantidadePorPagina;
        FinanceDatabase db = FinanceDatabase.getDatabase(this);
        CreditCardDao creditCardDao = db.creditDao();
        List<ExtratoData> extratoCredit = creditCardDao.extratoCredit(quantidadePorPagina, offset);

        return extratoCredit.stream()
                .map(c -> new ExtratoData(c.getData(), c.getDescricao(), c.getValor(), c.getCategoria()))
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

    private void configurarScrollListener(){
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy){
                super.onScrolled(recyclerView, dx, dy);
                if(dy>0) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if(layoutManager !=null){
                        int visibleItemCount = layoutManager.getChildCount();
                        int totalItemCount = layoutManager.getItemCount();
                        int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                        if(!carregando && temMais && (visibleItemCount + firstVisibleItem) >= totalItemCount - 5) {
                            atualizarFiltro();
                        }
                    }
                }
            }
        });
    }
   }
