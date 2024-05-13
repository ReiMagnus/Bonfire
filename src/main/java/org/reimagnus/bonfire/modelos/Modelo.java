package org.reimagnus.bonfire.modelos;

import javafx.scene.control.Control;
import javafx.scene.image.Image;

import java.util.Map;

public class Modelo {

    // -- Variáveis descritivas de um modelo --
    private String idModelo;
    private String nomeModelo;
    private String versaoModelo;
    private String criadorModelo;
    private Image imagemModelo;

    // -- Variáveis das páginas e conteúdo --
    private byte numPaginas;
    private Map<Integer, ? extends Control> listaControls;
    private Map<Integer, String> listaInfos;

    public Modelo(String id, String nome, String criador) {
        idModelo = id;
        nomeModelo = nome;
        versaoModelo = "1.0.0";
        criadorModelo = criador;
        //imagemModelo = new Image(String.valueOf(getClass().getResource("grafite.jpg")));
    }

    // -- Gets --
    public String getIdModelo() { return idModelo; }

    public String getNomeModelo() { return nomeModelo; }

    public String getVersaoModelo() { return versaoModelo; }

    public String getCriadorModelo() { return criadorModelo; }

    public Image getImagemModelo() { return imagemModelo; }

    public byte getNumPaginas() { return numPaginas; }

    public Map<Integer, ? extends Control> getListaControls() { return listaControls; }

    public Map<Integer, String> getListaInfos() { return listaInfos; }

    @Override
    public String toString() {
        return String.format(
            "ID: %s, Nome: %s, Versão: %s, Criador: %s",
            idModelo,
            nomeModelo,
            versaoModelo,
            criadorModelo)
        ;
    }

}
