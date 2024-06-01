package org.reimagnus.bonfire;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.reimagnus.bonfire.modelos.*;

import java.net.URL;
import java.util.*;

public class ControlTelaInicial implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private AnchorPane mainPane;
    @FXML
    private FlowPane icones;
    @FXML
    private ListView<ModeloPronto> listFichas;
    @FXML
    private Button bmCriarPersonagem, bmVerPersonagem, bmCriarModelo, bmVerModelo, bmGerenciarModelo;

    private Node janelinha = null; //Indica se a janelinha está aparecendo
    public boolean verPane = true; //True= ver personagens, False = ver projetos

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Métodos para carregar projetos, modelos e personagens salvos
        listFichas.getItems().addAll(Save.listaModelos);
        mostrarPersonagens();
    }

    // Botões da Tela Inicial --------------------------------------------
    public void buttonCriarPersonagem(ActionEvent event) {
        if(Save.listaPersonagens.size() < 15) {
            criandoPersonagem();
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
        if(Save.listaProjetos.size() < 15) {
            criandoProjeto();
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
        Save.listaProjetos.put(Save.listaProjetos.size(), pm);
        System.out.println("Criei um projeto");
    }

    private void colocarListaPersonagens(Personagem p) {
        Save.listaPersonagens.put(Save.listaPersonagens.size(), p);
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
        button.setId(String.format("&%d&", icones.getChildren().size()));

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
        icones.getChildren().removeAll(icones.getChildren());

        Personagem p = null;
        for(int i = 0; i < Save.listaPersonagens.size(); i++) {
            p = Save.listaPersonagens.get(i);
            String nome = p.getNomePersonagem();
            Image imagem = p.getIconPersonagem();

            icones.getChildren().add(criarIcon(nome, imagem));
        }

        bmVerPersonagem.setStyle("-fx-background-color: #5a6988");
        bmVerModelo.setStyle("-fx-background-color: #262b44");
    }

    private void mostrarProjetos() {
        verPane = false;
        icones.getChildren().removeAll(icones.getChildren());
        for(int i = 0; i < Save.listaProjetos.size(); i++) {
            String nome = Save.listaProjetos.get(i).modelo.getNomeModelo();
            Image imagem = new Image("/grafite.jpg");

            icones.getChildren().add(criarIcon(nome, imagem));
        }
        //System.out.println("Monstrando os projetos de modelos");
        bmVerModelo.setStyle("-fx-background-color: #5a6988");
        bmVerPersonagem.setStyle("-fx-background-color: #262b44");
    }

    // Métodos de fundo --------------------------------------------------
    private void criandoProjeto() {
        ProjetoModelo projeto = new ProjetoModelo();

        colocarListaProjetos(projeto);
        mostrarProjetos();
    }

    private void criandoPersonagem() {
        Personagem p = new Personagem();

        colocarListaPersonagens(p);
        mostrarPersonagens();
    }

    private void mudarTela(ActionEvent event) {
        try {
            FXMLLoader loader;

            stage = (Stage) mainPane.getScene().getWindow();

            int idProjeto = Integer.parseInt(event.getSource().toString().split("&")[1]);

            if(verPane) { //Ir pra a tela de personagens
                ControlPersonagem cp;
                loader = new FXMLLoader(getClass().getResource("Personagem.fxml"));
                root = loader.load();

                //stage.setTitle(String.format("Bonfire - %s", Save.listaPersonagens.get(idProjeto).modelo.getNomeModelo()));
                stage.setTitle("Bonfire - Personagem");
                stage.centerOnScreen();
                stage.resizableProperty().set(true);

                stage.setMinWidth(660+16);
                stage.setMinHeight(720+39);
                stage.setMaxWidth(1280+16);

            } else { // Ir pra o editor de fichas
                ControlEditorProjeto cep;
                loader = new FXMLLoader(getClass().getResource("EditorProjeto.fxml"));
                root = loader.load();
                cep = loader.getController();
                cep.carregandoProjeto(Save.listaProjetos.get(idProjeto), idProjeto);

                stage.setTitle(String.format("Bonfire - %s", Save.listaProjetos.get(idProjeto).modelo.getNomeModelo()));
            }

            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.out.println("Erro em carregar a proxima cena.\n---------------------------------------------");
            e.printStackTrace();
        }
    }

    public void teste(ActionEvent event) {
        System.out.println(event.getSource());
    }

}
