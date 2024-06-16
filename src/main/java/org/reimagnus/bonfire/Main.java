package org.reimagnus.bonfire;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private final Image icon = new Image("/IconBonfire.png");

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaInicial.fxml"));
        Parent root = loader.load();

        int width = 1280;
        int height = 720;

        Scene scene = new Scene(root, width, height);

        stage.resizableProperty().set(false);
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
