package com.example.appfinanceiro.database;

import java.math.BigDecimal;
import java.time.LocalDate;
import androidx.room.TypeConverter;


public class TypeConverterDb {
    @TypeConverter
    public static LocalDate toLocalDate(String valor){
        return valor == null ? null : LocalDate.parse(valor);
    }

    @TypeConverter
    public static String toString(LocalDate data){
        return data == null ? null : data.toString();
    }

    @TypeConverter
    public static BigDecimal toBigDecimal(String valor){
        return valor == null ? null : new BigDecimal(valor);
    }

    @TypeConverter
    public static String BDecimalToString(BigDecimal valor){
        return valor == null ? null : valor.toString();
    }
}
