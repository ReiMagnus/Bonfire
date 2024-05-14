package org.reimagnus.bonfire;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.reimagnus.bonfire.modelos.ModeloPronto;
import org.reimagnus.bonfire.modelos.Personagem;
import org.reimagnus.bonfire.modelos.ProjetoModelo;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ControlTelaInicial implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private FlowPane flowPane;
    @FXML
    private Button bmCriarPersonagem, bmVerPersonagem, bmCriarModelo, bmVerModelo, bmGerenciarModelo;

    private final Map<Integer, ProjetoModelo> listaProjetos = new HashMap<>();
    private final Map<Integer, ModeloPronto> listaModelos = new HashMap<>();
    private final Map<Integer, Personagem> listaPersonagens = new HashMap<>();

    private boolean janelinha = false; //Indica se a janelinha está aparecendo
    private boolean verPane = true; //True= ver personagens, False = ver projetos

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Métodos para carregar projetos, modelos e personagens salvos
        mostrarPersonagens();
    }

    // Botões da Tela Inicial --------------------------------------------
    public void buttonCriarPersonagem(ActionEvent event) {
        if(listaPersonagens.size() < 15) {
            Personagem p = new Personagem();
            colocarListaPersonagens(p);
            mostrarPersonagens();
        }
    }

    public void buttonVerPersonagem(ActionEvent event) {
        if(!verPane) {
            mostrarPersonagens();
        } else {
            System.out.println("Você já está vendo os personagens");
        }
    }

    public void buttonCriarModelo(ActionEvent event) {
        if(listaProjetos.size() < 15) {
            criandoProjeto("nome", "criador");
        }
    }

    public void buttonVerModelo(ActionEvent event) {
        if(verPane) {
            mostrarProjetos();
        } else {
            System.out.println("Você já está vendo os projetos");
        }
    }

    public void buttonGerenciarModelo(ActionEvent event) {
        teste(event);
    }

    // Manipulando as listas ---------------------------------------------
    private void colocarListaProjetos(ProjetoModelo pm) {
        listaProjetos.put(listaProjetos.size(), pm);
        System.out.println("Criei um projeto");
    }

    private void colocarListaPersonagens(Personagem p) {
        listaPersonagens.put(listaPersonagens.size(), p);
        System.out.println("Criei um personagem");
    }

    // Métodos que modificam a interface ---------------------------------
    private Pane criarIcon(String nome, Image image) {
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
        button.setOnAction(this::mudarTela);
        button.setId(String.format("%d", flowPane.getChildren().size()));

        //Nome do modelo/personagem
        label.setText(nome);
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

    private void mostrarPersonagens() {
        verPane = true;
        flowPane.getChildren().removeAll(flowPane.getChildren());
        for(int i = 0; i < listaPersonagens.size(); i++) {
            String nome = "persona" + (i+1);
            Image imagem = new Image("/gustavo.jpg");

            flowPane.getChildren().add(criarIcon(nome, imagem));
        }
        //System.out.println("Monstrando os personagens");
    }

    private void mostrarProjetos() {
        verPane = false;
        flowPane.getChildren().removeAll(flowPane.getChildren());
        for(int i = 0; i < listaProjetos.size(); i++) {
            String nome = listaProjetos.get(i).modelo.getNomeModelo();
            Image imagem = new Image("/grafite.jpg");

            flowPane.getChildren().add(criarIcon(nome, imagem));
        }
        //System.out.println("Monstrando os projetos de modelos");
    }

//    private void mostrarJanelinha() {
//
//        int w, h;
//        w = 250;
//        h = 300;
//
//        VBox janelinha = new VBox();
//        janelinha.setPrefWidth(w);
//        janelinha.setPrefHeight(h);
//        janelinha.setLayoutX(anchorPane.getWidth()/2 - w/2);
//        janelinha.setLayoutY(anchorPane.getHeight()/2 - h/2);
//        System.out.println(anchorPane.getPrefHeight());
//
//        janelinha.getChildren().addAll();
//        anchorPane.getChildren().add(janelinha);
//    }

    // Métodos de fundo --------------------------------------------------
    private void criandoProjeto(String nome, String criador) {
        ProjetoModelo projeto = new ProjetoModelo(nome, criador);

        colocarListaProjetos(projeto);
        mostrarProjetos();
    }

    private void mudarTela(ActionEvent event) {
        String[] o = String.valueOf(event.getSource()).split("");
        String id = "";
        id += o[10];
        id += o[11];
        try {
            Integer.parseInt(id);
        } catch (Exception e) {
        } finally {
            String[] a = id.split("");
            id = a[0];
        }
        System.out.println(Integer.parseInt(id));
    }

    public void teste(ActionEvent event) {
        System.out.println(event.getSource());
    }

}
