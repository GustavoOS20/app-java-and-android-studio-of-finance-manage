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
    @Query("SELECT data, descricao, CAST (valor AS INTEGER) AS valor, categorias.nome AS categoria FROM transacoesBalance INNER JOIN categorias ON categoria_id = categorias.id ORDER BY data DESC LIMIT :limit OFFSET :offset")
    List<ExtratoData> extratoBalance(int limit, int offset);
    @Query("SELECT CAST (valor AS INTEGER) AS valor FROM transacoesBalance WHERE mes = :mes AND ano = :ano")
    List<Integer> getBalancePorData(int mes, int ano);
}
