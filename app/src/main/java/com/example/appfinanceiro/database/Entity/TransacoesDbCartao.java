package com.example.appfinanceiro.database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(
        tableName = "transacoesCartoes",
        foreignKeys = {
                @ForeignKey(entity = CategoriasDb.class, parentColumns = "id", childColumns = "categoria_id", onDelete = ForeignKey.SET_NULL),
                @ForeignKey(entity = CartoesDb.class, parentColumns = "id", childColumns = "cartao_id", onDelete = ForeignKey.SET_NULL)
        }
)
public class TransacoesDbCartao {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public BigDecimal valor;
    public LocalDate data;
    @ColumnInfo (name = "categoria_id")
    public int categoriaId;
    @ColumnInfo (name = "cartao_id")
    public int cartaoId;
    public String descricao;
    public int dia;
    public int mes;
    public int ano;
    public int parcelas;

}
