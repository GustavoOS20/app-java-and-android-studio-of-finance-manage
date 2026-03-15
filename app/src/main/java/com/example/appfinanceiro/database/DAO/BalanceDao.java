package com.example.appfinanceiro.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.appfinanceiro.database.Entity.TransacoesDbBalance;

@Dao
public interface BalanceDao {
    @Insert
    void insert(TransacoesDbBalance balance);
}
