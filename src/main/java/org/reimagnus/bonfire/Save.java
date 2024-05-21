package org.reimagnus.bonfire;

import org.reimagnus.bonfire.modelos.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Save {

    public static Map<Integer, ProjetoModelo> listaProjetos = new HashMap<>();
    public static Set<ModeloPronto> listaModelos = new HashSet<>();//Map<Integer, ModeloPronto> listaModelos = new HashMap<>();
    public static Map<Integer, Personagem> listaPersonagens = new HashMap<>();

    //public static int numProjetos = 0;
    //public static int numModelosP = 0;
    //public static int numPersonas = 0;

    public static void carregandoArquivos() {
        System.out.println("Carregando arquivos salvos...");

        listaModelos.add(new ModeloPronto(new ProjetoModelo()));
        listaModelos.add(new ModeloPronto(new ProjetoModelo()));
        listaModelos.add(new ModeloPronto(new ProjetoModelo()));

    }

}
