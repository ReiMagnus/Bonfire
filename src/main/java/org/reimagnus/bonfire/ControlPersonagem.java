package org.reimagnus.bonfire;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
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
    private VBox vBox;
    @FXML
    private Pane folha;
    @FXML
    private FlowPane tela;

    int[] tamanhosFlowPane = {964, 0, 0};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //draggableMaker.makeDraggable(tela);
    }

    public void scrollPane(ScrollEvent event) {
        if(event.isControlDown()) {
            double zoomFactor = 0.1;
            double deltaY = event.getDeltaY();

            if(deltaY < 0) {zoomFactor *= -1;}

            if(folha.getScaleX() >= 0.8 && folha.getScaleX() <= 1.2) {
                folha.setScaleX(Math.clamp((folha.getScaleX() + zoomFactor), 0.8, 1.2 ));
                folha.setScaleY(Math.clamp((folha.getScaleY() + zoomFactor), 0.8, 1.2 ));
                tela.setPrefHeight( tamanhosFlowPane[0]*folha.getScaleX() );
            }
        }
    }

    //Botões da interface ------------------------------------------------
    public void buttonTelaInicial() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaInicial.fxml"));

            root = loader.load();
            stage = (Stage) vBox.getScene().getWindow();
            scene = new Scene(root);

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
