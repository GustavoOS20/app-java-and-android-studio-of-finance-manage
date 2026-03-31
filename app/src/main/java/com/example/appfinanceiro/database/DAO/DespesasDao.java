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
    @Query("SELECT data, descricao, CAST (valor AS INTEGER) AS valor, categorias.nome AS categoria FROM transacoesDespesas INNER JOIN categorias ON categoria_id = categorias.id ORDER BY data DESC LIMIT :limit OFFSET :offset")
    List<ExtratoData> extratoDespesas(int limit, int offset);
    @Query("SELECT CAST (valor AS INTEGER) AS valor FROM transacoesDespesas WHERE mes = :mes AND ano = :ano")
    List<Integer> getDespesasPorData(int mes, int ano);
}
