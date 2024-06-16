package org.reimagnus.bonfire;

import org.reimagnus.bonfire.modelos.*;

import java.util.*;

public class Save {

    public static Map<Integer, ProjetoModelo> listaProjetos;
    public static Set<ModeloPronto>           listaModelos;
    public static Map<Integer, Personagem>    listaPersonagens;

    public static void carregandoArquivos() {
        System.out.println("Carregando arquivos salvos...");

        listaProjetos = new HashMap<>();
        listaModelos = new HashSet<>();
        listaPersonagens = new HashMap<>();

    }

}
