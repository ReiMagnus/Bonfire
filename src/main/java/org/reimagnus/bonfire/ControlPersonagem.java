package org.reimagnus.bonfire;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    private int idPersonagem;
    private Personagem personagem;

    @FXML
    private VBox mainPane;
    @FXML
    private Pane folha;
    @FXML
    private FlowPane tela;
    @FXML
    private ImageView imageBG;
    @FXML
    private Button bTelaInicial, bSalvar, bPagina1, bPagina2, bPagina3;

    private final int tamFlowPane = 964;

    private byte atualPag = 1;
    private Pane[] pags = {new Pane(), new Pane(), new Pane()};
    private Image[] imagesBG;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        folha.setOnScroll(this::scrollPane);
        bTelaInicial.setOnAction(this::buttonTelaInicial);
        bSalvar.setOnAction(this::buttonSalvar);

        bPagina1.setOnAction(this::trocarPagina);
        bPagina2.setOnAction(this::trocarPagina);
        bPagina3.setOnAction(this::trocarPagina);
    }

    //Botões da interface ------------------------------------------------
    private void buttonTelaInicial(ActionEvent event) {
        salvandoPersonagem(true);
        Save.salvarArquivos();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaInicial.fxml"));

            root = loader.load();
            stage = (Stage) mainPane.getScene().getWindow();
            scene = new Scene(root);

            stage.setTitle("Bonfire");

            stage.resizableProperty().set(false);
            stage.setWidth(1280);
            stage.setHeight(720);

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Erro em carregar a proxima cena.\n---------------------------------------------");
            e.printStackTrace();
        }
    }

    private void buttonSalvar(ActionEvent event) {salvandoPersonagem(false);}

    public void trocarPagina(ActionEvent event) {
        Button b = (Button) event.getTarget();
        System.out.println(b);
        //attFolha(Byte.parseByte(b.getId().split("")[7]));
    }

    //Manipulando personagem ---------------------------------------------
    public void carregandoPersonagem(Personagem p, int id) {
        idPersonagem = id;
        personagem = p;

        imagesBG = p.modelo.getImagesBG();
        pags = p.modelo.getPaginas();

        folha.getChildren().addAll(pags[atualPag-1].getChildren());

        switch(personagem.modelo.getNumPaginas()) { // Números de páginas
            case 1:
                bPagina2.setDisable(true);
                bPagina3.setDisable(true);
                break;
            case 2:
                bPagina3.setDisable(true);
                break;
        }
        System.out.println("Personagem carregado...");
    }

    private void salvandoPersonagem(boolean fechar) {

        pags[atualPag-1].getChildren().addAll(folha.getChildren());
        folha.getChildren().add(pags[atualPag-1].getChildren().getFirst()); //recolocando o ImageView

        //Salvando folhas
        personagem.modelo.setPaginas(pags);
        Save.listaPersonagens.replace(idPersonagem, personagem);
        System.out.println("Salvando personagem...");

        if(!fechar) {
            folha.getChildren().addAll(pags[atualPag-1].getChildren());
        }
    }

    private void attFolha(byte proxPag) {
        //Guardando e carregando nova folha
        pags[atualPag-1].getChildren().clear();
        pags[atualPag-1].getChildren().addAll(folha.getChildren());
        folha.getChildren().add(pags[atualPag-1].getChildren().getFirst());

        atualPag = proxPag;
        System.out.println("Indo para folha " + atualPag);

        //Adicionando novo BG
        imageBG.setImage(imagesBG[atualPag-1]);

        //Adicionando nodes da nova folha
        folha.getChildren().addAll(pags[atualPag-1].getChildren());
    }

    private void scrollPane(ScrollEvent event) {
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

}
