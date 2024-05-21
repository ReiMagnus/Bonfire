package org.reimagnus.bonfire.modelos;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.Arrays;

public class Modelo {

    // -- Variáveis descritivas de um modelo --
    private String idModelo;
    private String nomeModelo;
    private String[] versaoModelo;
    private String criadorModelo;
    private Image imagemModelo;

    // -- Variáveis das páginas e conteúdo --
    private byte numPaginas = 1;
    private Pane[] paginas = {new Pane(), new Pane(), new Pane()};
    //private Map<Integer, ? extends Control> listaControls;
    //private Map<Integer, String> listaInfos;

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
    //public Map<Integer, ? extends Control> getListaControls() { return listaControls; }
    //public Map<Integer, String> getListaInfos() { return listaInfos; }

    // -- Sets --
    public void setNomeModelo(String nomeModelo) {this.nomeModelo = nomeModelo;}
    public void setVersaoModelo(String[] versaoModelo) {this.versaoModelo = versaoModelo.clone();}
    public void setCriadorModelo(String criadorModelo) {this.criadorModelo = criadorModelo;}
    public void setImagemModelo(Image imagemModelo) {this.imagemModelo = imagemModelo;}
    public void setNumPaginas(byte numPaginas) {this.numPaginas = numPaginas;}
    public void setPaginas(Pane[] paginas) {this.paginas = paginas.clone();}

    @Override
    public String toString() {
        String[] img = imagemModelo.getUrl().split("/");
        return String.format(
            "ID: %s, Nome: %s, Versão: %s, Criador: %s, Imagem: %s",
            idModelo,
            nomeModelo,
            Arrays.toString(versaoModelo),
            criadorModelo,
            img[img.length-1]
        );
    }

}
