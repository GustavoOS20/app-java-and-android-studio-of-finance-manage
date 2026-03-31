package com.example.appfinanceiro.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.appfinanceiro.database.Entity.TransacoesDbCartao;
import com.example.appfinanceiro.logicAndUi.adapter.data.ExtratoData;

import java.util.List;

@Dao
public interface CreditCardDao {
    @Insert
    void insert(TransacoesDbCartao creditCard);
    @Query("SELECT data, descricao, CAST (valor AS INTEGER) AS valor, categorias.nome AS categoria FROM transacoesCartoes INNER JOIN categorias ON categoria_id = categorias.id ORDER BY data DESC LIMIT :limit OFFSET :offset")
    List<ExtratoData> extratoCredit(int limit, int offset);
    @Query("SELECT CAST (valor AS INTEGER) AS valor FROM transacoesCartoes WHERE mes = :mes AND ano = :ano")
    List<Integer> getCreditPorData(int mes, int ano);
}
