package org.reimagnus.bonfire.nodes;

import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.Serializable;

public class SaveControl implements Serializable {

    private byte tipoNode;

    private String text;
    private String nomeNode; //Para o Template
    private String textPrompt;
    private double tamFont;

    private double tamWid;
    private double tamHei;
    private final double posX;
    private final double posY;

    public SaveControl(Node node) {
        Control control = (Control) node;
        String[] nClass = String.valueOf(control.getClass()).split("\\.");
        String tipo = nClass[nClass.length-1];

        switch(tipo) {
            case "Label":
                tipoNode = 0;
                text = ((Label)control).getText();
                tamFont = ((Label)control).getFont().getSize();
                tamWid = control.getPrefWidth();
                tamHei = control.getPrefHeight();
                break;
            case "TextField":
                tipoNode = 1;
                text = ((TextField)control).getText();
                textPrompt = ((TextField)control).getPromptText();
                tamFont = ((TextField)control).getFont().getSize();
                tamWid = control.getPrefWidth();
                tamHei = control.getPrefHeight();
                break;
            case "NumInteiro":
                tipoNode = 2;
                text = ((NumInteiro)control).getText();
                textPrompt = ((NumInteiro)control).getPromptText();
                tamFont = ((NumInteiro)control).getFont().getSize();
                tamWid = control.getPrefWidth();
                tamHei = control.getPrefHeight();
                break;
            case "NumRacional":
                tipoNode = 3;
                text = ((NumRacional)control).getText();
                textPrompt = ((NumRacional)control).getPromptText();
                tamFont = ((NumRacional)control).getFont().getSize();
                tamWid = control.getPrefWidth();
                tamHei = control.getPrefHeight();
                break;
            case "Template":
                Template template = (Template) control;
                tipoNode   = template.tipoNode;
                nomeNode   = template.nomeNode;
                text       = template.text;
                textPrompt = template.textPrompt;
                tamFont    = template.tamFont;
                tamWid = control.getPrefWidth();
                tamHei = control.getPrefHeight();
                break;
        }
        posX = control.getLayoutX();
        posY = control.getLayoutY();
    }

    public byte getTipoNode() {return tipoNode;}
    public String getNomeNode() {return nomeNode;}
    public String getText() {return text;}
    public String getTextPrompt() {return textPrompt;}
    public double getTamFont() {return tamFont;}
    public double getTamWid() {return tamWid;}
    public double getTamHei() {return tamHei;}
    public double getPosX() {return posX;}
    public double getPosY() {return posY;}

}
