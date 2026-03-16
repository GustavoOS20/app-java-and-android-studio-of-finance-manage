package com.example.appfinanceiro.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.appfinanceiro.database.Entity.TransacoesDbDespesas;
import com.example.appfinanceiro.logicAndUi.adapter.data.ExtratoData;

import java.util.List;

@Dao
public interface DespesasDao {
    @Insert
    void insert(TransacoesDbDespesas despesas);
    @Query("SELECT data, descricao, valor AS INTEGER, categoria_id FROM transacoesDespesas ORDER BY data DESC LIMIT :limit OFFSET :offset")
    List<ExtratoData> extratoDespesas(int limit, int offset);
    @Query("SELECT valor AS INTEGER FROM transacoesDespesas WHERE mes = :mes AND ano = :ano")
    List<Integer> getDespesasPorData(int mes, int ano);
}
