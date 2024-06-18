package org.reimagnus.bonfire.nodes;

import javafx.scene.control.TextField;

public class NumRacional extends TextField {

    private Double valor;

    public NumRacional() {
        if(this.getText() != null) {
            verificarNum(this.getText());
        }
        this.setOnAction(event -> verificarNum(this.getText()));
    }

    public Double getValor() {return valor;}

    private void verificarNum(String num) {
        try {
            valor = Double.parseDouble(num);
            this.setText(valor.toString());
        } catch (NumberFormatException e) {
            valor = 0.0;
            this.setText(valor.toString());
        }
    }

}
