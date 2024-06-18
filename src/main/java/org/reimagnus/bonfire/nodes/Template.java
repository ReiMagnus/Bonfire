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
                this.setText(txt);
                break;
            case 1:
                txt = "Texto Editável";
                this.setText(txt);
                break;
            case 2:
                txt = "Número Inteiro";
                this.setText("Inteiro");
                break;
            case 3:
                txt = "Número Racional";
                this.setText("Racional");
                break;
        }
        text = txt;
        nomeNode = txt;
        this.setPadding(new Insets(0, 0, 0, 5));
    }

    public Template(SaveControl sc) {
        tipoNode = sc.getTipoNode();
        nomeNode = sc.getNomeNode();
        if(tipoNode > 1) {text = sc.getText().split(" ")[0];} else {text = sc.getText();}
        textPrompt = sc.getTextPrompt();
        tamFont = sc.getTamFont();
        tamWid = sc.getTamWid();
        tamHei = sc.getTamHei();
        posX = sc.getPosX();
        posY = sc.getPosY();

        this.setText(text);
        //
        this.setFont(Font.font("Arial Black", tamFont));
        this.setPrefWidth(tamWid);
        this.setPrefHeight(tamHei);
        this.setLayoutX(posX);
        this.setLayoutY(posY);

        this.setPadding(new Insets(0, 0, 0, 5));
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
