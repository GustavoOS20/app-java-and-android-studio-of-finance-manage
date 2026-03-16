package com.example.appfinanceiro.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.appfinanceiro.database.Entity.TransacoesDbBalance;
import com.example.appfinanceiro.logicAndUi.adapter.data.ExtratoData;

import java.util.List;

@Dao
public interface BalanceDao {
    @Insert
    void insert(TransacoesDbBalance balance);
    @Query("SELECT data, descricao, saldo AS INTEGER, categoria_id FROM transacoesBalance ORDER BY data DESC LIMIT :limit OFFSET :offset")
    List<ExtratoData> extratoBalance(int limit, int offset);
    @Query("SELECT saldo AS INTEGER FROM transacoesBalance WHERE mes = :mes AND ano = :ano")
    List<Integer> getBalancePorData(int mes, int ano);
}
