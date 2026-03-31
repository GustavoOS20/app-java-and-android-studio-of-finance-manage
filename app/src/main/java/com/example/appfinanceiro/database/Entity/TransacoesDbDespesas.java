package com.example.appfinanceiro.database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(
        tableName = "transacoesDespesas",
        foreignKeys = {
                @ForeignKey (entity = CategoriasDb.class, parentColumns = "id", childColumns = "categoria_id", onDelete = ForeignKey.CASCADE),
                @ForeignKey (entity = CategoriasDb.class, parentColumns = "id", childColumns = "cartao_id", onDelete = ForeignKey.CASCADE)
        }
)
public class TransacoesDbDespesas {
    public TransacoesDbDespesas(){}
    @PrimaryKey(autoGenerate = true)
    public int id;
    public BigDecimal valor;
    public String descricao;
    public LocalDate data;
    @ColumnInfo(name = "cartao_id")
    public int contaOrigem;
    @ColumnInfo(name = "categoria_id")
    public int categoriaId;
    public int mes;
    public int ano;



}
