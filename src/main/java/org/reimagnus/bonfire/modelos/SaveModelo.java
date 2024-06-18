package org.reimagnus.bonfire.modelos;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.reimagnus.bonfire.Save;
import org.reimagnus.bonfire.nodes.SaveControl;

import java.io.Serializable;
import java.util.*;

public class SaveModelo implements Serializable {

    private final byte faseModelo; // 1 = projeto, 2 = ModeloPronto, 3 = Personagem

    // -- Variáveis descritivas de um modelo --
    private final String idModelo;
    private final String nomeModelo;
    private final String[] versaoModelo;
    private final String criadorModelo;
    private final String imagemModelo; //URL da imagem

    // -- Variáveis das páginas e conteúdo --
    private byte numPaginas = 1;

    private List<Set<SaveControl>> paginas;
//    private Image[] imagesBG = new Image[3];

    public SaveModelo(Modelo m) {
        this.faseModelo = m.getFaseModelo();
        this.idModelo = m.getIdModelo();
        this.nomeModelo = m.getNomeModelo();
        this.versaoModelo = m.getVersaoModelo();
        this.criadorModelo = m.getCriadorModelo();
        this.imagemModelo = m.getImagemModelo().getUrl();

        this.numPaginas = m.getNumPaginas();

        paginas = new ArrayList<>();
        salvarControls(m.getPaginas());
        //Images do background

    }

    public byte getFaseModelo() {return faseModelo;}
    public String getIdModelo() {return idModelo;}
    public String getNomeModelo() {return nomeModelo;}
    public String[] getVersaoModelo() {return versaoModelo;}
    public String getCriadorModelo() {return criadorModelo;}
    public String getImagemModelo() {return imagemModelo;}
    public byte getNumPaginas() {return numPaginas;}
    public List<Set<SaveControl>> getPaginas() {return paginas;}

    private void salvarControls(Pane[] panes) {
        paginas = new ArrayList<>();
        for(Pane pane : panes) {
            Set<SaveControl> controls = new LinkedHashSet<>();
            for(Node node : pane.getChildren()) {
                controls.add(new SaveControl(node));
            }
            paginas.add(controls);
        }
    }

}
