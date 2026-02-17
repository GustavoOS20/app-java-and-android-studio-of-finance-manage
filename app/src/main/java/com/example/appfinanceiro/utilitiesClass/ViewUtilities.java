package com.example.appfinanceiro.utilitiesClass;


import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appfinanceiro.R;
import java.util.UUID;

public class ViewUtilities {
    public static void setMargins(View view){
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public static void actionBar(AppCompatActivity activity, boolean onOrOff){
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(onOrOff);
            actionBar.setDisplayShowHomeEnabled(onOrOff);
        }
    }



    public static String IdValue(){
        return UUID.randomUUID().toString();
    }

}
