package org.reimagnus.bonfire.modelos;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import org.reimagnus.bonfire.nodes.NumInteiro;
import org.reimagnus.bonfire.nodes.NumRacional;
import org.reimagnus.bonfire.nodes.SaveControl;
import org.reimagnus.bonfire.nodes.Template;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class Modelo implements Serializable {

    private final byte faseModelo; // 1 = projeto, 2 = ModeloPronto, 3 = Personagem

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
        faseModelo    = 1;
        idModelo      = id;
        nomeModelo    = "Ficha de RPG";
        versaoModelo  = new String[]{"1", "0", "0"};
        criadorModelo = "UserName";
        imagemModelo  = new Image("/IconProjeto.png");
    }
    //Recriando um modelo (ModeloPronto e Personagem)
    public Modelo(Modelo m) {
        faseModelo    = (byte) (m.getFaseModelo() + 1);
        idModelo      = m.getIdModelo();
        nomeModelo    = m.getNomeModelo();
        versaoModelo  = m.getVersaoModelo().clone();
        criadorModelo = m.getCriadorModelo();
        imagemModelo  = m.getImagemModelo();

        numPaginas = m.getNumPaginas();
        converterTemplate(m.paginas);
        //imagesBG = m.getImagesBG().clone();
    }
    //Recarregando o modelo pro aplicativo (SaveModelo)
    public Modelo(SaveModelo sm) {
        faseModelo    = sm.getFaseModelo();
        idModelo      = sm.getIdModelo();
        nomeModelo    = sm.getNomeModelo();
        versaoModelo  = sm.getVersaoModelo().clone();
        criadorModelo = sm.getCriadorModelo();
        imagemModelo  = new Image(sm.getImagemModelo());
        numPaginas    = sm.getNumPaginas();

        recriarControls(sm.getPaginas());
    }

    // -- Gets --
    public byte getFaseModelo() {return faseModelo;}
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

    // Métoddos para recriar/converter Controls
    private void converterTemplate(Pane[] pags) throws NoSuchElementException {

        if(!pags[0].getChildren().isEmpty()) {
            if(pags[0].getChildren().getFirst().getClass() != Template.class) {
                for(int i = 0; i < numPaginas; i++) {
                    for(Node item : pags[i].getChildren()) {
                        String[] nClass = String.valueOf(item.getClass()).split("\\.");
                        String tipo = nClass[nClass.length-1];
                        switch(tipo) {
                            case "Label":
                                Label aLabel = (Label) item;
                                Label nLabel = new Label();

                                nLabel.setText(aLabel.getText());
                                nLabel.setFont(Font.font("Arial Black", aLabel.getFont().getSize()));
                                nLabel.setPrefWidth(aLabel.getPrefWidth());
                                nLabel.setPrefHeight(aLabel.getPrefHeight());
                                nLabel.setLayoutX(aLabel.getLayoutX());
                                nLabel.setLayoutY(aLabel.getLayoutY());

                                paginas[i].getChildren().add(nLabel);
                                break;
                            case "TextField":
                                TextField aTextField = (TextField) item;
                                TextField nTextField = new TextField();

                                nTextField.setPromptText(aTextField.getPromptText());
                                nTextField.setFont(Font.font(aTextField.getFont().getSize()));
                                nTextField.setPrefWidth(aTextField.getPrefWidth());
                                nTextField.setPrefHeight(aTextField.getPrefHeight());
                                nTextField.setLayoutX(aTextField.getLayoutX());
                                nTextField.setLayoutY(aTextField.getLayoutY());

                                paginas[i].getChildren().add(nTextField);
                                break;
                            case "NumInteiro":
                                NumInteiro aNumInteiro = (NumInteiro) item;
                                NumInteiro nNumInteiro = new NumInteiro();

                                nNumInteiro.setPromptText(aNumInteiro.getPromptText());
                                nNumInteiro.setFont(Font.font(aNumInteiro.getFont().getSize()));
                                nNumInteiro.setPrefWidth(aNumInteiro.getPrefWidth());
                                nNumInteiro.setPrefHeight(aNumInteiro.getPrefHeight());
                                nNumInteiro.setLayoutX(aNumInteiro.getLayoutX());
                                nNumInteiro.setLayoutY(aNumInteiro.getLayoutY());

                                paginas[i].getChildren().add(nNumInteiro);
                                break;
                            case "NumRacional":
                                NumRacional aNumRacional = (NumRacional) item;
                                NumRacional nNumRacional = new NumRacional();

                                nNumRacional.setPromptText(aNumRacional.getPromptText());
                                nNumRacional.setFont(Font.font(aNumRacional.getFont().getSize()));
                                nNumRacional.setPrefWidth(aNumRacional.getPrefWidth());
                                nNumRacional.setPrefHeight(aNumRacional.getPrefHeight());
                                nNumRacional.setLayoutX(aNumRacional.getLayoutX());
                                nNumRacional.setLayoutY(aNumRacional.getLayoutY());

                                paginas[i].getChildren().add(nNumRacional);
                                break;

                        }
                    }
                }

            } else {
                for(int i = 0; i < numPaginas; i++) {
                    for(Node template : pags[i].getChildren()) {
                        Template t = (Template) template;
                        switch(t.tipoNode) {
                            case 0:
                                Label label = new Label(t.text);

                                label.setFont(Font.font("Arial Black", t.tamFont));
                                label.setPrefWidth(t.tamWid);
                                label.setPrefHeight(t.tamHei);
                                label.setLayoutX(t.posX);
                                label.setLayoutY(t.posY);

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
        } else {
            throw new NoSuchElementException();
        }

    }

    private void recriarControls(List<Set<SaveControl>> list) {

        if(faseModelo == 1) {

            for(byte i = 0; i < paginas.length; i++) {
                for(SaveControl sc : list.get(i)) {
                    Template template = new Template(sc);
                    paginas[i].getChildren().add(template);
                }
            }
        } else {
            for(byte i = 0; i < list.size(); i++) {
                for(SaveControl sc : list.get(i)) {
                    switch(sc.getTipoNode()) {
                        case 0:
                            Label label = new Label();
                            label.setText(sc.getText());
                            label.setFont(Font.font("Arial Black", sc.getTamFont()));
                            label.setPrefWidth(sc.getTamWid());
                            label.setPrefHeight(sc.getTamHei());
                            label.setLayoutX(sc.getPosX());
                            label.setLayoutY(sc.getPosY());

                            paginas[i].getChildren().add(label);
                            break;
                        case 1:
                            TextField textField  = new TextField();
                            textField.setText(sc.getText());
                            textField.setFont(Font.font(sc.getTamFont()));
                            textField.setPrefWidth(sc.getTamWid());
                            textField.setPrefHeight(sc.getTamHei());
                            textField.setLayoutX(sc.getPosX());
                            textField.setLayoutY(sc.getPosY());

                            paginas[i].getChildren().add(textField);
                            break;
                        case 2:
                            NumInteiro numInteirol = new NumInteiro();
                            numInteirol.setText(sc.getText());
                            numInteirol.setFont(Font.font(sc.getTamFont()));
                            numInteirol.setPrefWidth(sc.getTamWid());
                            numInteirol.setPrefHeight(sc.getTamHei());
                            numInteirol.setLayoutX(sc.getPosX());
                            numInteirol.setLayoutY(sc.getPosY());

                            paginas[i].getChildren().add(numInteirol);
                            break;
                        case 3:
                            NumRacional numRacional = new NumRacional();
                            numRacional.setText(sc.getText());
                            numRacional.setFont(Font.font(sc.getTamFont()));
                            numRacional.setPrefWidth(sc.getTamWid());
                            numRacional.setPrefHeight(sc.getTamHei());
                            numRacional.setLayoutX(sc.getPosX());
                            numRacional.setLayoutY(sc.getPosY());

                            paginas[i].getChildren().add(numRacional);
                            break;
                    }
                }
            }
        }

    }

    //AAAAAA
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
