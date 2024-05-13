package org.reimagnus.bonfire;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.reimagnus.bonfire.modelos.*;

import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static java.awt.Color.RED;

public class ControlTelaInicial implements Initializable {

    private Stage stage;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private FlowPane flowPane;
    @FXML
    private Button bmCriarPersonagem, bmVerPersonagem, bmCriarModelo, bmVerModelo, bmGerenciarModelo;

    private Map<Integer, ProjetoModelo> listaProjetos = new HashMap<>();
    private Map<Integer, ModeloPronto> listaModelos = new HashMap<>();
    private Map<Integer, Personagem> listaPersonagens = new HashMap<>();

    private boolean verPane = true; //True= ver personagens, False = ver projetos

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Métodos para carregar projetos, modelos e personagens salvos
    }

    // Botões da Tela Inicial -----------------------------------------------------------
    public void criarPersonagem(ActionEvent event) {
        Personagem p = new Personagem();
        colocarListaPersonagens(p);
    }

    public void verPersonagem(ActionEvent event) {
        System.out.println(verPane);
        mostrarPersonagens();
    }

    public void criarModelo(ActionEvent event) {
        ProjetoModelo projeto = new ProjetoModelo("nome", "criador");
        colocarListaProjetos(projeto);
    }

    public void verModelo(ActionEvent event) {
        System.out.println(verPane);
        mostrarProjetos();
    }

    public void gerenciarModelo(ActionEvent event) {
        teste(event);
    }

    // Manipulando as listas
    private void colocarListaProjetos(ProjetoModelo pm) {
        if(listaProjetos.size() < 15) {
            listaProjetos.put(listaProjetos.size(), pm);
        }
        System.out.println("Criei um projeto");
    }

    private void colocarListaPersonagens(Personagem p) {
        if(listaPersonagens.size() < 15) {
            listaPersonagens.put(listaPersonagens.size(), p);
        }
        System.out.println("Criei um personagem");
    }

    // Métodos de fundo ------------------------
    private Pane criarIcon(String name, Image image) {
        Button button = new Button();
        ImageView imageView = new ImageView();
        Label label = new Label();

        int tamPane = 192;

        //Imagem do modelo/personagem
        Image icon = new Image(image.getUrl(), tamPane, tamPane, true, true);
        imageView.setImage(icon);

        //Botão pra entrar do projeto/personagem
        button.setPrefWidth(tamPane);
        button.setPrefHeight(tamPane);
        button.setOnAction(this::teste);
        button.setId(String.format("%s%d", "item", flowPane.getChildren().size()+1));

        //Nome do modelo/personagem
        label.setText(String.format("%s %d", name, (flowPane.getChildren().size()+1)));
        label.setPrefWidth(tamPane);
        label.setPrefHeight(32);
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setLayoutY(tamPane-4);

        Pane pane = new Pane(imageView, button, label);
        pane.setPrefWidth(tamPane);
        pane.setPrefHeight(tamPane);

        return pane;
    }

    private void mostrarProjetos() {
        if(verPane) {
            verPane = false;
            flowPane.getChildren().removeAll(flowPane.getChildren());
            for(int i = 0; i < listaProjetos.size(); i++) {

                String nome = listaProjetos.get(i).modelo.getNomeModelo();
                Image imagem = new Image("/grafite.jpg");

                flowPane.getChildren().add(criarIcon(nome, imagem));
            }
        } else {
            System.out.println("Você já está vendo os projetos");
        }
    }

    private void mostrarPersonagens() {
        if(!verPane) {
            verPane = true;
            flowPane.getChildren().removeAll(flowPane.getChildren());
            for(int i = 0; i < listaPersonagens.size(); i++) {

                String nome = "persona" + (i+1);
                Image imagem = new Image("/gustavo.jpg");

                flowPane.getChildren().add(criarIcon(nome, imagem));
            }
        } else {
            System.out.println("Você já está vendo os personagens");
        }
    }

    public void teste(ActionEvent event) {
        System.out.println(event.getSource());
    }

}
