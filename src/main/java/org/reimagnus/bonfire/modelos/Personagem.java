package org.reimagnus.bonfire.modelos;

import javafx.scene.image.Image;

public class Personagem {

    public Modelo modelo;

    private String nomePersonagem;
    private Image iconPersonagem = new Image("IconBonfire.png");

    public Personagem(/*ModeloPronto modeloPronto*/) {
        //modelo = modeloPronto.modelo;
        nomePersonagem = "Personagem";
    }

    //Métodos tela inicial ----------------------
    public void setNomePersonagem(String nomePersonagem) {this.nomePersonagem = nomePersonagem;}
    public void setIconPersonagem(Image iconPersonagem) {this.iconPersonagem = iconPersonagem;}

    public String getNomePersonagem() {return nomePersonagem;}
    public Image getIconPersonagem() {return iconPersonagem;}


}
