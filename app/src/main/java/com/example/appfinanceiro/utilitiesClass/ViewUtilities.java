package com.example.appfinanceiro.utilitiesClass;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.View;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appfinanceiro.ExtratoActivity;
import com.example.appfinanceiro.databinding.ActivityExtratoBinding;
import com.example.appfinanceiro.MainActivity;
import com.example.appfinanceiro.databinding.ActivityExtratoBinding;
import com.example.appfinanceiro.databinding.ActivityMainBinding;

import java.util.UUID;

public class ViewUtilities {
    public static void setMargins(View view){
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public static String IdValue(){
        return UUID.randomUUID().toString();
    }

}
