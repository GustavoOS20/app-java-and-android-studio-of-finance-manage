package com.example.appfinanceiro.model;

import java.util.List;

public class Categorias {
    private static final List<String> categoriasDespesas = List.of( "Moradia", "Transporte", "Alimentação", "Saúde", "Lazer", "Educação", "Assinaturas/Streaming", "Investimentos");

    public static List<String> getCategoriasDespesas() {
        return categoriasDespesas;
    }

    private static final List<String> categoriasbalance = List.of(
            "Salário (Principal)", "Renda Extra", "Rendimentos de Investimentos", "Freelance/Projetos",  "Outros/Reembolsos"
    );

    public static List<String> getCategoriasBalance() {
        return categoriasbalance;
    }
}
