package org.reimagnus.bonfire;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.reimagnus.bonfire.modelos.ProjetoModelo;

import java.net.URL;
import java.util.ResourceBundle;

public class ControlEditorProjeto implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private SplitPane splitPane;

    //informações da ficha
    @FXML
    private TextField nomeModelo, criadorModelo, versao1Modelo, versao2Modelo, versao3Modelo;
    @FXML
    private Label idModelo;
    @FXML
    private Button imagemModelo;
    @FXML
    private ListView<String> listNodes;

    //Botões superiores da tela
    @FXML Button buttonTelaInicial;

    private ProjetoModelo projetoModelo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //listNodes.getItems().addAll("AAAA", "AAAA", "AAAA", "AAAA", "AAAA", "AAAA", "AAAA", "AAAA", "AAAA", "AAAA", "AAAA", "AAAA", "AAAA", "AAAA", "AAAA", "AAAA", "AAAA", "AAAA");
    }

    //Botões da interface ------------------------------------------------
    public void buttonTelaInicial() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaInicial.fxml"));

            root = loader.load();
            stage = (Stage) splitPane.getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        } catch (Exception e) {
            System.out.println("Erro em carregar a proxima cena.\n---------------------------------------------");
            e.printStackTrace();
        }
    }

    //Manipulação do projeto ---------------------------------------------
    public void carregandoProjeto(ProjetoModelo pm) {
        projetoModelo = pm;

        nomeModelo.setText(pm.modelo.getNomeModelo());
        criadorModelo.setText(pm.modelo.getCriadorModelo());

        versao1Modelo.setText(pm.modelo.getVersaoModelo()[0]);
        versao2Modelo.setText(pm.modelo.getVersaoModelo()[1]);
        versao3Modelo.setText(pm.modelo.getVersaoModelo()[2]);

        idModelo.setText(pm.modelo.getIdModelo());

        String[] nomeImg = pm.modelo.getImagemModelo().getUrl().split("/");
        imagemModelo.setText(nomeImg[nomeImg.length-1]);
    }

}
