package org.reimagnus.bonfire;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import org.reimagnus.bonfire.modelos.Personagem;

import java.net.URL;
import java.util.ResourceBundle;

public class ControlPersonagem implements Initializable {

    public Personagem personagem;

    @FXML
    private Pane folha;
    @FXML
    private FlowPane tela;

    DraggableMaker draggableMaker = new DraggableMaker();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        draggableMaker.makeDraggable(tela);
    }

    public void scrollPane(ScrollEvent event) {
        double zoomFactor = 1.10;
        double deltaY = event.getDeltaY();

        if(deltaY < 0) {zoomFactor = 2.0 - zoomFactor;}

        folha.setScaleX(folha.getScaleX() * zoomFactor);
        folha.setScaleY(folha.getScaleY() * zoomFactor);
    }

}
