package org.reimagnus.bonfire;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.reimagnus.bonfire.modelos.Personagem;

import java.net.URL;
import java.util.ResourceBundle;


public class ControlPersonagem implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public Personagem personagem;

    @FXML
    private VBox mainPane;
    @FXML
    private Pane folha;
    @FXML
    private FlowPane tela;

    int tamFlowPane = 964;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void scrollPane(ScrollEvent event) {
        if(event.isControlDown()) {
            double zoomFactor = 0.1;
            double deltaY = event.getDeltaY();
            float min = 1;
            float max = 1.3F;

            if(deltaY < 0) {zoomFactor *= -1;}

            if(folha.getScaleX() >= min && folha.getScaleX() <= max) {
                folha.setScaleX(Math.clamp((folha.getScaleX() + zoomFactor), min, max));
                folha.setScaleY(Math.clamp((folha.getScaleY() + zoomFactor), min, max));
                tela.setPrefHeight( tamFlowPane*folha.getScaleX() );
            }
        }
    }

    //Botões da interface ------------------------------------------------
    public void buttonTelaInicial() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaInicial.fxml"));

            root = loader.load();
            stage = (Stage) mainPane.getScene().getWindow();
            scene = new Scene(root);

            stage.resizableProperty().set(false);
            stage.setTitle("Bonfire");
            stage.setMaxWidth(Double.MAX_VALUE);

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Erro em carregar a proxima cena.\n---------------------------------------------");
            e.printStackTrace();
        }
    }

}
