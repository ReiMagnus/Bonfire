package org.reimagnus.bonfire;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.reimagnus.bonfire.modelos.ModeloPronto;
import org.reimagnus.bonfire.modelos.Personagem;
import org.reimagnus.bonfire.modelos.ProjetoModelo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControlTelaInicial implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    //Geral -------
    @FXML
    private AnchorPane mainPane;
    @FXML
    private FlowPane icones;
    @FXML
    private Button bmCriarPersonagem, bmVerPersonagem, bmCriarModelo, bmVerModelo, bmGerenciarModelo;

    //Janelinha -----
    @FXML
    private FlowPane fpJanela;
    @FXML
    private Button bFecharJanela, bImportarFicha, bRemoverFicha;
    @FXML
    private ToolBar optionsJanela;
    @FXML
    ListView<ModeloPronto> listFichas;

    private byte janelinha = 0; //0 = Sem janela, 1 = Jan. Gereciar, 2 = Jan. Criar
    public boolean verPane = true; //True= ver personagens, False = ver projetos
    private boolean popupVisible = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Métodos para carregar projetos, modelos e personagens salvos
        listFichas.getItems().addAll(Save.listaModelos);
        mostrarPersonagens();

        listFichas.setOnMousePressed(event -> {
            if(event.isPrimaryButtonDown()) {
                if(event.getClickCount() == 2 && janelinha == 2) {
                    criandoPersonagem(listFichas.getSelectionModel().getSelectedItem());
                    attJanelinha(0);
                }
            } else if(event.isSecondaryButtonDown()) {
                listFichas.getSelectionModel().clearSelection();
                bRemoverFicha.setDisable(true);
            }

            if(listFichas.getSelectionModel().getSelectedItem() != null) {
                bRemoverFicha.setDisable(false);
            }

        });
        bFecharJanela.setOnAction(this::buttonFecharjanela);
        bImportarFicha.setOnAction(this::buttonImportarFicha);
        bRemoverFicha.setOnAction(this::buttonRemoverFicha);
    }

    // Botões da Tela Inicial --------------------------------------------
    public void buttonCriarPersonagem(ActionEvent event) {
        if(janelinha == 0) {
            if (Save.listaPersonagens.size() < 15) {attJanelinha( 2);}
        }
    }

    public void buttonVerPersonagem(ActionEvent event) {
        if(janelinha == 0) {
            if (!verPane) {mostrarPersonagens();}
        }
    }

    public void buttonCriarModelo(ActionEvent event) {
        if(janelinha == 0) {
            if (Save.listaProjetos.size() < 15) {criandoProjeto();}
        }
    }

    public void buttonVerModelo(ActionEvent event) {
        if(janelinha == 0) {
            if (verPane) {mostrarProjetos();}
        }
    }

    public void buttonGerenciarModelo(ActionEvent event) {
        if(janelinha == 0) {
            Save.salvarArquivos();
            attJanelinha(1);
        }
    }

    // Botões da janelinha -----------------------------------------------
    private void buttonFecharjanela(ActionEvent event) {attJanelinha(0);}

    private void buttonImportarFicha(ActionEvent event) {
        Save.importarFicha((Stage) mainPane.getScene().getWindow());
        listFichas.getItems().clear();
        listFichas.getItems().addAll(Save.listaModelos);
        Save.salvarArquivos();
    }

    private void buttonRemoverFicha(ActionEvent event) {
        ModeloPronto modeloPronto = listFichas.getSelectionModel().getSelectedItem();
        Save.listaModelos.remove(modeloPronto);

        listFichas.getSelectionModel().clearSelection();
        listFichas.getItems().clear();
        listFichas.getItems().addAll(Save.listaModelos);
        Save.salvarArquivos();
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
        button.setId(String.format("&%d&", icones.getChildren().size()));
        interagindoIcon(button);

        //Nome do modelo/personagem
        label.setText(nome);
        label.setPrefWidth(tamPane);
        label.setPrefHeight(32);
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setLayoutY(tamPane-32);

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

        bmVerPersonagem.setStyle("-fx-background-color: #36847b");
        bmVerModelo.setStyle("-fx-background-color: #470905");
    }

    private void mostrarProjetos() {
        verPane = false;
        icones.getChildren().removeAll(icones.getChildren());
        for(int i = 0; i < Save.listaProjetos.size(); i++) {

            String nome  = Save.listaProjetos.get(i).modelo.getNomeModelo();
            Image imagem = Save.listaProjetos.get(i).modelo.getImagemModelo();

            icones.getChildren().add(criarIcon(nome, imagem));
        }
        bmVerModelo.setStyle("-fx-background-color: #36847b");
        bmVerPersonagem.setStyle("-fx-background-color: #470905");
    }

    private void attJanelinha(int n) {
        janelinha = (byte) n;

        switch(janelinha) {
            case 0:
                fpJanela.setVisible(false);
                listFichas.getSelectionModel().clearSelection();
                break;
            case 1:
                fpJanela.setVisible(true);
                optionsJanela.setVisible(true);
                bRemoverFicha.setDisable(true);
                break;
            case 2:
                fpJanela.setVisible(true);
                optionsJanela.setVisible(false);
                break;

        }
    }

    // Métodos de fundo --------------------------------------------------
    private void criandoProjeto() {
        ProjetoModelo projeto = new ProjetoModelo();

        colocarListaProjetos(projeto);
        mostrarProjetos();
    }

    private void criandoPersonagem(ModeloPronto mp) {
        if(janelinha == 2) {
            Personagem p = new Personagem(mp);
            colocarListaPersonagens(p);
            mostrarPersonagens();
        }
    }

    private void interagindoIcon(Button button) {

        int idProjeto = Integer.parseInt(button.toString().split("&")[1]);

        button.setOnMousePressed(event -> {
            if(event.isPrimaryButtonDown()) {
                mudarTela(idProjeto);
            } else {
                contextMenuIcon(button, idProjeto);
            }
        });

    }

    private void contextMenuIcon(Button button, int idProjeto) {

        Popup popup = new Popup();
        popup.setOnHidden(event -> popupVisible = false);

        ContextMenu cm = new ContextMenu();

        // Só em personagem ----------
        if(verPane) {
            MenuItem renomear = new MenuItem("Renomear");
            renomear.setOnAction(event -> {
                if(popupVisible) {return;}

                TextField tf = new TextField();
                tf.setId("tfRenomear");
                tf.setPrefWidth(250);
                tf.setPrefHeight(40);
//                tf.setStyle("-fx-background-color: silver");
                tf.setFont(Font.font(22));
                tf.setPromptText("Nome do personagem");
                tf.setOnAction(event1 -> {
                    Save.listaPersonagens.get(idProjeto).setNomePersonagem(tf.getText());
                    Save.salvarArquivos();
                    mostrarPersonagens();
                    popup.hide();
                    popupVisible = false;
                });
                popup.getContent().add(tf);

                stage = (Stage) mainPane.getScene().getWindow();

                popup.setX(stage.getX() + ((double) Main.width /2) - tf.getPrefWidth()/2);
                popup.setY(stage.getY() + ((double) Main.height /2) - tf.getPrefHeight()/2);

                popup.show(stage);

                popupVisible = true;
            });
            cm.getItems().add(renomear);
        }

        // Em personagem e projetos ------
        MenuItem remover = new CheckMenuItem("Remover");
        remover.setOnAction(event -> {
            if(!verPane) { // projetos
                Save.listaProjetos.remove(idProjeto);
                mostrarProjetos();
            } else { // Personagem
                Save.listaPersonagens.remove(idProjeto);
                mostrarPersonagens();
            }
            Save.salvarArquivos();
        });
        cm.getItems().add(remover);

        button.setContextMenu(cm);
    }

    private void mudarTela(int idProjeto) {
        try {
            FXMLLoader loader;

            stage = (Stage) mainPane.getScene().getWindow();

            if(verPane) { //Ir pra a tela de personagens
                ControlPersonagem cp;
                loader = new FXMLLoader(getClass().getResource("Personagem.fxml"));
                root = loader.load();
                cp = loader.getController();
                cp.carregandoPersonagem(Save.listaPersonagens.get(idProjeto), idProjeto);

                stage.setTitle(String.format("Bonfire - %s", Save.listaPersonagens.get(idProjeto).getNomePersonagem()));
                stage.centerOnScreen();
                stage.resizableProperty().set(true);

                stage.setMaxWidth(1280+16);
                stage.setMinWidth(660+16);

                stage.setMinHeight(720+39);

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

        } catch (IOException e) {
            System.out.println("Erro em carregar a proxima cena.\n---------------------------------------------");
            e.printStackTrace();
        }
    }

}