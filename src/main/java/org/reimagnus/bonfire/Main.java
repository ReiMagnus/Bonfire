package org.reimagnus.bonfire;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class Main extends Application {

    private final Image icon = new Image("/IconBonfire.png");

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaInicial.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1280, 720);

        stage.setMinWidth(1280+16);
        stage.setMinHeight(720+39);
        stage.centerOnScreen();

        stage.setTitle("Bonfire");
        stage.getIcons().add(icon);

        stage.setScene(scene);
        stage.initStyle(StageStyle.DECORATED);
        stage.show();
    }

    public static void main(String[] args) {
        Save.carregandoArquivos();
        launch();
    }

}
