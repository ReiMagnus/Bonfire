package org.reimagnus.bonfire.modelos;

import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.Map;

public class Modelo {

    // -- Variáveis descritivas de um modelo --
    private String idModelo;
    private String nomeModelo;
    private String[] versaoModelo;
    private String criadorModelo;
    private Image imagemModelo;

    // -- Variáveis das páginas e conteúdo --
    private byte numPaginas;
    private final Pane[] paginas = {new Pane(), new Pane(), new Pane()};
    private Map<Integer, ? extends Control> listaControls;
    private Map<Integer, String> listaInfos;

    public Modelo(String id) {
        idModelo = id;
        nomeModelo = "Ficha de RPG";
        versaoModelo = new String[]{"1", "0", "0"};
        criadorModelo = "UserName";
        imagemModelo = new Image("/IconBonfire.png");
    }

    // -- Gets --
    public String getIdModelo() { return idModelo; }
    public String getNomeModelo() { return nomeModelo; }
    public String[] getVersaoModelo() { return versaoModelo; }
    public String getCriadorModelo() { return criadorModelo; }
    public Image getImagemModelo() { return imagemModelo; }
    public byte getNumPaginas() { return numPaginas; }
    public Pane[] getPaginas() {return paginas;}
    public Map<Integer, ? extends Control> getListaControls() { return listaControls; }
    public Map<Integer, String> getListaInfos() { return listaInfos; }

    @Override
    public String toString() {
        return String.format(
            "ID: %s, Nome: %s, Versão: %s, Criador: %s",
            idModelo,
            nomeModelo,
            versaoModelo,
            criadorModelo
        );
    }

}
