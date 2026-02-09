package com.example.appfinanceiro;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

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
    }

    private void inicializarLayout(){
        binding = ActivityExtratoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void menuNavegation(){
        binding.homeButton.setBackgroundTintList(buttonColorDisabled);

        binding.ExtratoButton.setOnClickListener(v-> {
                    binding.ExtratoButton.setBackgroundTintList(buttonColorDisabled);
                    binding.homeButton.setBackgroundTintList(buttonColorEnabled);
                }
        );
        binding.homeButton.setOnClickListener(v-> {
                    binding.homeButton.setBackgroundTintList(buttonColorDisabled);
                    binding.ExtratoButton.setBackgroundTintList(buttonColorEnabled);
                    Intent intent = new Intent(ExtratoActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
        );
    }
}
