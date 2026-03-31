package com.example.appfinanceiro.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.appfinanceiro.database.DAO.BalanceDao;
import com.example.appfinanceiro.database.DAO.CategoriasDao;
import com.example.appfinanceiro.database.DAO.CreditCardDao;
import com.example.appfinanceiro.database.DAO.DespesasDao;
import com.example.appfinanceiro.database.Entity.CategoriasDb;
import com.example.appfinanceiro.database.Entity.TransacoesDbBalance;
import com.example.appfinanceiro.database.Entity.TransacoesDbCartao;
import com.example.appfinanceiro.database.Entity.TransacoesDbDespesas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {CategoriasDb.class, TransacoesDbBalance.class, TransacoesDbDespesas.class, TransacoesDbCartao.class}, version = 1)
@TypeConverters({TypeConverterDb.class})
public abstract class FinanceDatabase extends RoomDatabase{
    public abstract CategoriasDao categoriasDao();
    public abstract BalanceDao balanceDao();
    public abstract DespesasDao despesasDao();
    public abstract CreditCardDao creditDao();

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);
    private static volatile FinanceDatabase INSTANCE;

    public static FinanceDatabase getDatabase(final Context context){
        if(INSTANCE == null) {
            synchronized (FinanceDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), FinanceDatabase.class, "finance_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
