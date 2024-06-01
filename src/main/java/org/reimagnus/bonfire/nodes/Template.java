package org.reimagnus.bonfire.nodes;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class Template extends Label {

    public byte tipoNode;
    public String nomeNode;

    public String text = "";
    public String textPrompt = "";

    public double tamFont;
    public double tamWid;
    public double tamHei;
    public double posX;
    public double posY;

    public Template(int tipoNode) {
        this.tipoNode = (byte) tipoNode;
        String txt = "";
        switch(tipoNode) {
            case 0:
                txt = "Texto";
                break;
            case 1:
                txt = "Texto Editável";
                break;
            case 2:
                txt = "Número Inteiro";
                break;
            case 3:
                txt = "Número Racional";
                break;
        }
        this.setText(txt);
        nomeNode = txt;
    }

    public void initInfos(double tamFont, double tamWid, double tamHei, double posX, double posY) {
        this.tamFont = tamFont;
        this.tamWid  = tamWid;
        this.tamHei  = tamHei;
        this.posX    = posX;
        this.posY    = posY;
    }

    @Override
    public String toString() {
        return String.format("%s", nomeNode);
    }

}
