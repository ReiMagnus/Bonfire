package org.reimagnus.bonfire;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.reimagnus.bonfire.modelos.ModeloPronto;
import org.reimagnus.bonfire.modelos.ProjetoModelo;
import org.reimagnus.bonfire.nodes.Template;

import java.net.URL;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

public class ControlEditorProjeto implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private AnchorPane mainPane;

    //Botões superiores da tela -----------------
    @FXML Button bSalvar, bExportar, bPagina1, bPagina2, bPagina3,  bTelaInicial;

    //Informações da ficha
    @FXML
    private TextField nomeModelo, criadorModelo, versao1Modelo, versao2Modelo, versao3Modelo;
    @FXML
    private Label idModelo;
    @FXML
    private Button imagemModelo, bImageBG;
    @FXML
    private RadioButton rb1, rb2, rb3;

    //Hierarquia --------------------------------
    @FXML
    private TreeView<String> listHierarquia;

    //Nodes -------------------------------------
    @FXML
    private TreeView<String> listNodes;

    //Propriedades ------------------------------
    @FXML
    private VBox vbPros;
    @FXML
    private Label labelPropriedade;
    @FXML
    private TextField tfComprimento, tfAltura, tfPosX, tfPosY, tfTamFont, tfTextPrompt, tfText;
    @FXML
    private Button bRemoverNode;

    //Folha -------------------------------------
    @FXML
    private Pane folha;
    @FXML
    private ImageView imageBG;

    @FXML
    private ScrollPane nivelScroll;

    private int idProjeto;
    private ProjetoModelo projetoModelo;

    private Template selectControl;

    private double mouseAnchorX, mouseAnchorY;
    private double tamW, tamH;
    private byte numPags = 1;
    private byte atualPag = 1;
    private Pane[] pags = {new Pane(), new Pane(), new Pane()};
    private Image[] imagesBG;

    private final String[] itens = {"Texto", "Texto Editável", "Número Inteiro", "Número Racional"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        attMenuPropriedades();
        createListNodes();
        tamW = folha.getPrefWidth();
        tamH = folha.getPrefHeight();
        bRemoverNode.setOnAction(this::removerNode);

        rb1.setOnAction(this::attNumPaginas);
        rb2.setOnAction(this::attNumPaginas);
        rb3.setOnAction(this::attNumPaginas);

        listHierarquia.setOnMousePressed(this::selecionandoItemHierarquia);
        listHierarquia.setShowRoot(false);

        listNodes.setOnMousePressed(this::selecionandoItemNode);
        listNodes.setShowRoot(false);

//        Image bg = null;
//        bg = new Image("FichaT20.png", tamW, tamH, true, true);
//        imageBG.setImage(bg);
//        ImageView imageView = new ImageView(imageFolha);

        folha.setOnMousePressed(event -> {
            if(event.isSecondaryButtonDown()) {
                if(selectControl != null) {
                    selectControl.setStyle("-fx-border-color: black");
                    selectControl = null;
                    listHierarquia.getSelectionModel().select(null); //Desmarcando item da hierarquia
                    listNodes.getSelectionModel().select(null); //Desmarcando item da lista de nodes
                    attMenuPropriedades();
                }
            }
        });
    }

    //Botões da interface superior ---------------------------------------
    public void buttonTelaInicial() {
        salvandoProjeto(true);
        Save.salvarArquivos();
        voltarTelaInicial();
    }

    public void buttonSalvar() {
        salvandoProjeto(false);
    }

    public void buttonExportar() {
        try {
            salvandoProjeto(true);
            ModeloPronto modeloPronto = new ModeloPronto(projetoModelo);

            int num = -1;
            for(ModeloPronto mp : Save.listaModelos) {
                if(num <= modeloPronto.compareTo(mp)) {
                    num = modeloPronto.compareTo(mp);
                }
            }

            if(num == -1) {
                Save.listaModelos.add(modeloPronto);
                Save.exportarFicha(modeloPronto);
            } else if(num == 0) {
                Save.listaModelos.remove(modeloPronto); //Removendo quando o id igual
                Save.listaModelos.add(modeloPronto); //Adicionando quando a versão mais nova
                Save.exportarFicha(modeloPronto);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Já existe uma versão mais atualizada no sistema.");
                alert.show();
            }

            // Aumentando o número da versão
            String[] novaVersao = projetoModelo.modelo.getVersaoModelo();
            novaVersao[2] = String.valueOf(Integer.parseInt(novaVersao[2])+1);
            projetoModelo.modelo.setVersaoModelo(novaVersao);

            //Coloquando novo modelo
            versao3Modelo.setText(projetoModelo.modelo.getVersaoModelo()[2]); //Versão pequena
            folha.getChildren().addAll(pags[atualPag-1].getChildren());
        } catch (NoSuchElementException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao exportar o projeto.");
            alert.show();
        }
    }

    public void trocarPagina(ActionEvent event) {
        Button b = (Button) event.getTarget();
        attFolha(Byte.parseByte(b.getId().split("")[7]));
        System.out.println(folha.getChildren());
    }

    //Manipulação do projeto ---------------------------------------------
    public void carregandoProjeto(ProjetoModelo pm, int id) {
        idProjeto = id;
        projetoModelo = pm;

        //Informações essenciais
        nomeModelo.setText(pm.modelo.getNomeModelo()); // ------ Nome
        criadorModelo.setText(pm.modelo.getCriadorModelo()); //  Criador
        versao1Modelo.setText(pm.modelo.getVersaoModelo()[0]); //Versão grande
        versao2Modelo.setText(pm.modelo.getVersaoModelo()[1]); //Versão media
        versao3Modelo.setText(pm.modelo.getVersaoModelo()[2]); //Versão pequena
        idModelo.setText(pm.modelo.getIdModelo()); // ---------- ID

        String[] nomeImg = pm.modelo.getImagemModelo().getUrl().split("/"); //Icon modelo
        imagemModelo.setText(nomeImg[nomeImg.length-1]); // ---- Icon modelo

        //Outras informações
        numPags = pm.modelo.getNumPaginas();
        switch(numPags) { // Números de páginas
            case 1:
                rb1.setSelected(true);
                bPagina2.setDisable(true);
                bPagina3.setDisable(true);
                break;
            case 2:
                rb2.setSelected(true);
                bPagina3.setDisable(true);
                break;
            case 3:
                rb3.setSelected(true);
                break;
        }

        imagesBG = pm.modelo.getImagesBG();
        String[] nomeBG = String.valueOf(imagesBG[numPags-1]).split("/");
        if(nomeBG[0].equals("null")) {
            bImageBG.setText("Sem imagem");
        } else {
            bImageBG.setText(nomeBG[nomeBG.length-1]);
        }

        pags = pm.modelo.getPaginas();

        folha.getChildren().addAll(pags[atualPag-1].getChildren());

        for(int i = 1; i < folha.getChildren().size(); i++) {
            clicandoNodes((Template) folha.getChildren().get(i));
            selecionadoTemplate((Template) folha.getChildren().get(i));
        }

        attHierarquia();
        System.out.println("Carregando projeto...");
    }

    private void salvandoProjeto(boolean fechar) {

        String[] versao = {versao1Modelo.getText(), versao2Modelo.getText(), versao3Modelo.getText()};

        projetoModelo.modelo.setNomeModelo(nomeModelo.getText());
        projetoModelo.modelo.setCriadorModelo(criadorModelo.getText());
        projetoModelo.modelo.setVersaoModelo(versao);
        //projetoModelo.modelo.setImagemModelo();

        projetoModelo.modelo.setNumPaginas(numPags);

        pags[atualPag-1].getChildren().addAll(folha.getChildren());
        folha.getChildren().add(pags[atualPag-1].getChildren().getFirst()); //recolocando o ImageView

        //Salvando folhas
        projetoModelo.modelo.setPaginas(pags);
        Save.listaProjetos.replace(idProjeto, projetoModelo);
        System.out.println("Salvando projeto...");

        if(!fechar) {
            folha.getChildren().addAll(pags[atualPag-1].getChildren());
        }
    }

    private void voltarTelaInicial() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaInicial.fxml"));

            root = loader.load();
            stage = (Stage) mainPane.getScene().getWindow();
            scene = new Scene(root);

            stage.setTitle("Bonfire");

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Erro em carregar a proxima cena.\n---------------------------------------------");
            e.printStackTrace();
        }
    }

    //Aba informações da ficha -------------------------------------------
    private void attNumPaginas(ActionEvent event) {
        ToggleGroup group = rb1.getToggleGroup();
        byte num = Byte.parseByte(group.getSelectedToggle().toString().split("'")[1]);

        switch(num) {
            case 1:
                bPagina2.setDisable(true);
                bPagina3.setDisable(true);
                break;
            case 2:
                bPagina2.setDisable(false);
                bPagina3.setDisable(true);
                break;
            case 3:
                bPagina2.setDisable(false);
                bPagina3.setDisable(false);
                break;
        }

        numPags = num;
        atualPag = 1;
    }

    //Aba Hierarquia -----------------------------------------------------
    private void selecionandoItemHierarquia(MouseEvent event) {
        if(event.isPrimaryButtonDown()) {
            if(listHierarquia.getSelectionModel().getSelectedItem() != null ) {
                TreeItem item = listHierarquia.getSelectionModel().getSelectedItem();
                Template template = (Template) folha.getChildren().get(listHierarquia.getRow(item)+1);
                selecionadoTemplate(template);
            }
        } else if(event.isSecondaryButtonDown()) {
            listHierarquia.getSelectionModel().select(null);
        }
    }

    private void attHierarquia() {
        TreeItem<String> root = new TreeItem<>();
        byte[] numItens = new byte[itens.length];

        for(byte i = 1; i < folha.getChildren().size(); i++) {
            Template t = (Template) folha.getChildren().get(i);
            numItens[t.tipoNode]++;

            String txt = String.format("%s %s", t, numItens[t.tipoNode]);
            root.getChildren().add(new TreeItem<>(txt));
        }
        listHierarquia.setRoot(root);
    }

    //Aba Nodes ----------------------------------------------------------
    private void createListNodes() {
        TreeItem<String> root = new TreeItem<>();
        for(String n : itens) {
            root.getChildren().add(new TreeItem<>(n));
        }
        listNodes.setRoot(root);
    }

    private void selecionandoItemNode(MouseEvent event) {
        if(event.isPrimaryButtonDown()) {
            if(listNodes.getSelectionModel().getSelectedItem() != null) {
                createTemplate(listNodes.getSelectionModel().getSelectedIndices().getFirst());
            }
        } else if(event.isSecondaryButtonDown()) {
            listNodes.getSelectionModel().select(null); //Desmarcando item da lista de nodes
        }
    }

    private void createTemplate(int tipoNode) {
        Template c = new Template(tipoNode);
        double tamSize = 18;
        double tamW = 250; //Tam padrão
        double tamH = 40;  //Tam padrão
        switch(c.nomeNode) {
            case "Número Inteiro":
                tamW = 100;
                break;
            case "Número Racional":
                tamW = 100;
                break;
        }

        double posX = folha.getPrefWidth()/2 - tamW/2;
        double posY = folha.getPrefHeight()/2 - tamH/2;

        c.setFont(new Font("Arial Black", tamSize));
        c.setPrefWidth(tamW);
        c.setPrefHeight(tamH);
        c.setLayoutX(posX);
        c.setLayoutY(posY);

        c.initInfos(tamSize, tamW, tamH, posX, posY);
        clicandoNodes(c);
        selecionadoTemplate(c);
        folha.getChildren().add(c);
        attHierarquia();

    }

    //Aba Propriedades ---------------------------------------------------
    private void attMenuPropriedades() {
        String txt = "Propriedades: ";
        if(selectControl == null) {
            txt += "Vazio";
            vbPros.setDisable(true);

            tfText.setText(null);
            tfTextPrompt.setText(null);

            tfTamFont.setText(null);
            tfComprimento.setText(null);
            tfAltura.setText(null);
            tfPosX.setText(null);
            tfPosY.setText(null);

        } else {
            txt += selectControl.getText();
            vbPros.setDisable(false);

            switch(selectControl.tipoNode) {
                case 0:
                    tfText.setDisable(false);

                    tfTextPrompt.setText(null);
                    tfText.setText(selectControl.text);

                    attPropriedade(tfText);

                    tfTextPrompt.setDisable(true);
                    break;

                case 1, 2, 3:
                    tfTextPrompt.setDisable(false);

                    tfText.setText(null);
                    tfTextPrompt.setText(selectControl.textPrompt);

                    attPropriedade(tfTextPrompt);

                    tfText.setDisable(true);
                    break;
            }

            tfTamFont.setText(String.valueOf((selectControl.tamFont)));
            tfComprimento.setText(String.valueOf((selectControl.tamWid)));
            tfAltura.setText(String.valueOf((selectControl.tamHei)));
            tfPosX.setText(String.valueOf((selectControl.posX)));
            tfPosY.setText(String.valueOf((selectControl.posY)));

            attPropriedade(tfTamFont);
            attPropriedade(tfComprimento);
            attPropriedade(tfAltura);
            attPropriedade(tfPosX);
            attPropriedade(tfPosY);
        }
        labelPropriedade.setText(txt);
    }

    private void attPropriedade(TextField tf) {

        tf.setOnAction(event -> {
            switch(tf.getId()) {
                case "tfText":
                    selectControl.text = tf.getText();
                    selectControl.setText(tf.getText());
                    break;

                case "tfTextPrompt":
                    selectControl.textPrompt = tf.getText();
                    break;

                case "tfTamFont":
                    double tamFont = Math.clamp(Double.parseDouble(tf.getText()), 12, 48);
                    selectControl.tamFont = tamFont;
                    selectControl.setFont( new Font("Arial Black", tamFont));
                    tfTamFont.setText(String.valueOf(tamFont));
                    break;

                case "tfComprimento":
                    double w = Math.clamp(Double.parseDouble(tf.getText()), 30, tamW);
                    selectControl.tamWid = w;
                    selectControl.setPrefWidth(w);
                    tfComprimento.setText(String.valueOf(w));

                    //Reposicionando node
                    selectControl.setLayoutX(Math.clamp(selectControl.getLayoutX(), 0, tamW-selectControl.getPrefWidth()));
                    break;

                case "tfAltura":
                    double h = Math.clamp(Double.parseDouble(tf.getText()), 30, 160);
                    selectControl.tamHei = h;
                    selectControl.setPrefHeight(h);
                    tfAltura.setText(String.valueOf(h));

                    //Reposicionando node
                    selectControl.setLayoutY(Math.clamp(selectControl.getLayoutY(), 0, tamH-selectControl.getPrefHeight()));
                    break;

                case "tfPosX":
                    double posX = Math.clamp(Double.parseDouble(tf.getText()), 0, tamW-selectControl.getPrefWidth());
                    selectControl.posX = posX;
                    selectControl.setLayoutX(posX);
                    tfPosX.setText(String.valueOf(posX));
                    break;

                case "tfPosY":
                    double posY = Math.clamp(Double.parseDouble(tf.getText()), 0, tamW-selectControl.getPrefWidth());
                    selectControl.posY = posY;
                    selectControl.setLayoutY(posY);
                    tfPosY.setText(String.valueOf(posY));
                    break;
            }
        });
    }

    //Geral --------------------------------------------------------------
    private void attFolha(byte proxPag) {
        //Guardando e carregando novo BG
//        imagesBG[atualPag-1] = imageBG.getImage();

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

        for(int i = 1; i < folha.getChildren().size(); i++) {
            clicandoNodes((Template) folha.getChildren().get(i));
            selecionadoTemplate((Template) folha.getChildren().get(i));
        }

        attHierarquia();
        selectControl = null;
        listHierarquia.getSelectionModel().select(null); //Desmarcando item da hierarquia
        listNodes.getSelectionModel().select(null); //Desmarcando item da lista de nodes
    }

    private void clicandoNodes(Template node) {

        node.setOnMousePressed(event -> {
            selecionadoTemplate(node);
            //Posição do clique
            mouseAnchorX = event.getX();
            mouseAnchorY = event.getY();

        });
        //Arrastando o node na folha
        node.setOnMouseDragged(event -> {
            if(event.isPrimaryButtonDown()) {
                double pivoX = event.getSceneX() - node.getParent().getLayoutX();
                double pivoY = event.getSceneY() - (node.getParent().getLayoutY()*2) + (274* nivelScroll.getVvalue());

                double posX = Math.clamp(pivoX, 0, tamW) - mouseAnchorX;
                double posY = Math.clamp(pivoY, 0, tamH) - mouseAnchorY;

                node.setLayoutX(posX);
                node.setLayoutY(posY);
            }
        });
        //Reposicionando o node quando soltar
        node.setOnMouseReleased(event -> {
            if(selectControl != null) {
                double pivoX = event.getSceneX() - node.getParent().getLayoutX();
                double pivoY = event.getSceneY() - (node.getParent().getLayoutY()*2) + (274* nivelScroll.getVvalue());
                double posX  = Math.clamp(pivoX - mouseAnchorX, 0, tamW - node.getPrefWidth());
                double posY  = Math.clamp(pivoY - mouseAnchorY, 0, tamH - node.getPrefHeight());
                node.setLayoutX(posX);
                node.setLayoutY(posY);

                selectControl.posX = posX;
                selectControl.posY = posY;
                tfPosX.setText(String.valueOf(posX));
                tfPosY.setText(String.valueOf(posY));
            }
        });
    }

    private void removerNode(ActionEvent event) {
        folha.getChildren().remove(selectControl);
        selectControl = null;
        attMenuPropriedades();
        attHierarquia();
    }

    private void selecionadoTemplate(Template node) {
        if(selectControl != null) {selectControl.setStyle("-fx-border-color: black");}
        node.setStyle("-fx-border-color: red");

        selectControl = node;
        attMenuPropriedades();
    }

}
