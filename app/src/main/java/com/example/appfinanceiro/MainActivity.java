package com.example.appfinanceiro;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appfinanceiro.databinding.ActivityMainBinding;
import com.example.appfinanceiro.databinding.ActivityAddBalanceBinding;
import com.example.appfinanceiro.utilitiesClass.ViewUtilities;

public class MainActivity extends AppCompatActivity{
    private ActivityMainBinding binding;
    private ActivityAddBalanceBinding bindingAdd;
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
    }

    private void inicializarLayout(){
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void fabAddAndRemove() {
        binding.AddSaldo.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddBalanceActivity.class);
            startActivity(intent);
        });
        binding.addDespesas.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddDespesasActivity.class);
            startActivity(intent);
        });
    }

    private void alterValues(){
        binding.main.setOnClickListener(view -> {
            binding.AddSaldo.setVisibility(GONE);
            binding.addDespesas.setVisibility(GONE);
        });
        binding.AddSaldo.setVisibility(GONE);
        binding.addDespesas.setVisibility(GONE);
        binding.AddValues.setOnClickListener(view -> {
            binding.AddSaldo.setVisibility(VISIBLE);
            binding.addDespesas.setVisibility(VISIBLE);
        });
    }

    private void menuNavegation(){
        binding.ExtratoButton.setBackgroundTintList(buttonColorDisabled);

        binding.homeButton.setOnClickListener(v-> {
                    binding.ExtratoButton.setBackgroundTintList(buttonColorDisabled);
                    binding.homeButton.setBackgroundTintList(buttonColorEnabled);
                }
        );
        binding.ExtratoButton.setOnClickListener(v-> {
                    binding.homeButton.setBackgroundTintList(buttonColorDisabled);
                    binding.ExtratoButton.setBackgroundTintList(buttonColorEnabled);
                    Intent intent = new Intent(MainActivity.this, ExtratoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
        );
    }
}