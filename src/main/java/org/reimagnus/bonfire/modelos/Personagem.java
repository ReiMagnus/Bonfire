package org.reimagnus.bonfire.modelos;

import javafx.scene.image.Image;

public class Personagem {

    public Modelo modelo;

    private String nomePersonagem;
    private Image iconPersonagem;

    public Personagem(ModeloPronto mp) {
        modelo = new Modelo(mp.modelo);
        nomePersonagem = "Personagem";

        String[] url = modelo.getImagemModelo().getUrl().split("/");
        if(("/" + url[url.length - 1]).equals("/IconProjeto.png")) {
            iconPersonagem = new Image("/IconPersonagem.png");
        } else {
            iconPersonagem =  modelo.getImagemModelo();
        }
    }

    public Personagem(SaveModelo sm) {
        modelo = new Modelo(sm);

        modelo.setNomeModelo(modelo.getNomeModelo());
        nomePersonagem = modelo.getNomeModelo();

        String[] url = modelo.getImagemModelo().getUrl().split("/");
        if(("/" + url[url.length - 1]).equals("/IconProjeto.png")) {
            iconPersonagem = new Image("/IconPersonagem.png");
        } else {
            iconPersonagem =  modelo.getImagemModelo();
        }

    }

    //Métodos tela inicial ----------------------
    public void setNomePersonagem(String nomePersonagem) {
        modelo.setNomeModelo(nomePersonagem);
        this.nomePersonagem = nomePersonagem;
    }
    public void setIconPersonagem(Image iconPersonagem) {
        modelo.setImagemModelo(iconPersonagem);
        this.iconPersonagem = iconPersonagem;
    }

    public String getNomePersonagem() {return nomePersonagem;}
    public Image getIconPersonagem() {return iconPersonagem;}


}
