package com.example.appfinanceiro.database.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Cartoes")
public class CartoesDb {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String nome;
}
