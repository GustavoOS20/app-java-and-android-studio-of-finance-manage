package com.example.appfinanceiro.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.appfinanceiro.database.Entity.CategoriasDb;

import java.util.List;

@Dao
public interface CategoriasDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CategoriasDb categoria);
    @Query("SELECT * FROM categorias")
    List<CategoriasDb> getAll();
    @Query("SELECT id FROM categorias WHERE nome = :nomeCategoria LIMIT 1")
    int getId(String nomeCategoria);

}
