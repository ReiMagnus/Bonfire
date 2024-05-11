package org.reimagnus.bonfire;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
public class ControlTelaInicial {

    private Stage stage;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private FlowPane flowPane;
    @FXML
    private Button bmCriarPersonagem, bmVerPersonagem, bmCriarModelo, bmVerModelo, bmGerenciarModelo;

    public void criarPersonagem(ActionEvent event) {

        Pane personagem = criarIconPersonagem("Grafite");

        flowPane.getChildren().add(personagem);
        System.out.println(flowPane.getChildren().size());
        //teste(event);
    }

    public void verPersonagem(ActionEvent event) {
        teste(event);
    }

    public void criarModelo(ActionEvent event) {
        teste(event);
    }

    public void verModelo(ActionEvent event) {
        teste(event);
    }

    public void gerenciarModelo(ActionEvent event) {
        teste(event);
    }

    public void teste(ActionEvent event) {
        System.out.println(event.getSource());
    }

    private Pane criarIconPersonagem(String name) {
        Pane personagem = new Pane();
        Button button = new Button();
        Label label = new Label();


        label.setText(String.format("%s %d", name, (flowPane.getChildren().size()+1)));
        label.setPrefWidth(192);
        label.setPrefHeight(32);
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setLayoutY(188);

        personagem.getChildren().add(button);
        personagem.getChildren().add(label);

        return personagem;
    }

}
