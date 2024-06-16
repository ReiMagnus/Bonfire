package org.reimagnus.bonfire.modelos;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import org.reimagnus.bonfire.nodes.NumInteiro;
import org.reimagnus.bonfire.nodes.NumRacional;
import org.reimagnus.bonfire.nodes.Template;

import java.util.Arrays;

public class Modelo {

    // -- Variáveis descritivas de um modelo --
    private final String idModelo;
    private String nomeModelo;
    private String[] versaoModelo;
    private String criadorModelo;
    private Image imagemModelo;

    // -- Variáveis das páginas e conteúdo --
    private byte numPaginas = 1;
    private Pane[] paginas = {new Pane(), new Pane(), new Pane()};
    private Image[] imagesBG = new Image[3];

    //Criando um modelo novo (ProjetoModelo)
    public Modelo(String id) {
        idModelo      = id;
        nomeModelo    = "Ficha de RPG";
        versaoModelo  = new String[]{"1", "0", "0"};
        criadorModelo = "UserName";
        imagemModelo  = new Image("/IconProjeto.png");
    }
    //Recriando um modelo (ModeloPronto)
    public Modelo(Modelo m) {
        idModelo      = m.getIdModelo();
        nomeModelo    = m.getNomeModelo();
        versaoModelo  = m.getVersaoModelo().clone();
        criadorModelo = m.getCriadorModelo();
        imagemModelo  = m.getImagemModelo();

        numPaginas = m.getNumPaginas();
        converterTemplate(m.paginas);
        imagesBG = m.getImagesBG().clone();
    }

    //
    public void converterTemplate(Pane[] pags) {
        for(int i = 0; i < pags.length; i++) {
            for(Node template : pags[i].getChildren()) {
                Template t = (Template) template;
                switch(t.tipoNode) {
                    case 0:
                        Label label = new Label(t.text);

                        label.setFont(Font.font(t.tamFont));
                        label.setPrefWidth(t.tamWid);
                        label.setPrefHeight(t.tamHei);
                        label.setLayoutX(t.posX);
                        label.setLayoutY(t.posY);

                        System.out.println("AAA");
                        paginas[i].getChildren().add(label);
                        break;
                    case 1:
                        TextField textField = new TextField();

                        textField.setPromptText(t.textPrompt);
                        textField.setFont(Font.font(t.tamFont));
                        textField.setPrefWidth(t.tamWid);
                        textField.setPrefHeight(t.tamHei);
                        textField.setLayoutX(t.posX);
                        textField.setLayoutY(t.posY);

                        paginas[i].getChildren().add(textField);
                        break;
                    case 2:
                        NumInteiro numInteiro = new NumInteiro();

                        numInteiro.setPromptText(t.textPrompt);
                        numInteiro.setFont(Font.font(t.tamFont));
                        numInteiro.setPrefWidth(t.tamWid);
                        numInteiro.setPrefHeight(t.tamHei);
                        numInteiro.setLayoutX(t.posX);
                        numInteiro.setLayoutY(t.posY);

                        paginas[i].getChildren().add(numInteiro);
                        break;
                    case 3:
                        NumRacional numRacional = new NumRacional();

                        numRacional.setPromptText(t.textPrompt);
                        numRacional.setFont(Font.font(t.tamFont));
                        numRacional.setPrefWidth(t.tamWid);
                        numRacional.setPrefHeight(t.tamHei);
                        numRacional.setLayoutX(t.posX);
                        numRacional.setLayoutY(t.posY);

                        paginas[i].getChildren().add(numRacional);
                        break;
                }
            }
        }
    }

    // -- Gets --
    public String getIdModelo() { return idModelo; }
    public String getNomeModelo() { return nomeModelo; }
    public String[] getVersaoModelo() { return versaoModelo; }
    public String getCriadorModelo() { return criadorModelo; }
    public Image getImagemModelo() { return imagemModelo; }
    public byte getNumPaginas() { return numPaginas; }
    public Pane[] getPaginas() {return paginas.clone();}
    public Image[] getImagesBG() {return imagesBG.clone();}

    // -- Sets --
    public void setNomeModelo(String nomeModelo) {this.nomeModelo = nomeModelo;}
    public void setVersaoModelo(String[] versaoModelo) {this.versaoModelo = versaoModelo.clone();}
    public void setCriadorModelo(String criadorModelo) {this.criadorModelo = criadorModelo;}
    public void setImagemModelo(Image imagemModelo) {this.imagemModelo = imagemModelo;}
    public void setNumPaginas(byte numPaginas) {this.numPaginas = numPaginas;}
    public void setPaginas(Pane[] paginas) {this.paginas = paginas.clone();}
    public void setImagesBG(Image[] imagesBG) {this.imagesBG = imagesBG.clone();}

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
