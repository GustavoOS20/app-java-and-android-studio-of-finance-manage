package com.example.appfinanceiro.database.Entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "categorias",
        indices = {@Index(value = {"nome"}, unique = true)})
public class CategoriasDb {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String nome;
    public String tipo;

}
