package com.example.appfinanceiro;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appfinanceiro.adapter.AdapterExtrato;
import com.example.appfinanceiro.databinding.ActivityExtratoBinding;
import com.example.appfinanceiro.utilitiesClass.ViewUtilities;

public class ExtratoActivity extends AddBalanceActivity{
    private ActivityExtratoBinding binding;
    private final ColorStateList buttonColorDisabled = ColorStateList.valueOf(Color.parseColor("#571895D8"));
    private final ColorStateList buttonColorEnabled = ColorStateList.valueOf(Color.parseColor("#925FBC"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inicializarLayout();
        ViewUtilities.setMargins(binding.getRoot());
        menuNavegation();
        actionBar();
        adicionarElementos();
    }

    private void actionBar(){
        setSupportActionBar(binding.toolbar6);
        ViewUtilities.actionBar(this, false);
        onCreateOptionsMenu(binding.toolbar6.getMenu());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    private void inicializarLayout(){
        binding = ActivityExtratoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void adicionarElementos(){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new AdapterExtrato());
    }

    private void menuNavegation(){
        binding.bottomNavigationEx.setSelectedItemId(R.id.nav_extrato);
        binding.bottomNavigationEx.setOnItemSelectedListener(v-> {
            if (binding.bottomNavigationEx.getSelectedItemId() != v.getItemId()) {
                Intent intent = new Intent(ExtratoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
            return true;
        }
        );
    }
}
