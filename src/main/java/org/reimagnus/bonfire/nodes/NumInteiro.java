package org.reimagnus.bonfire.nodes;

import javafx.scene.control.TextField;

public class NumInteiro extends TextField {

    private Integer valor;

    public NumInteiro() {
        if(this.getText() != null) {
            verificarNum(this.getText());
        }
        this.setOnAction(event -> verificarNum(this.getText()));
    }

    public Integer getValor() {return valor;}

    private void verificarNum(String num) {
        try {
            valor = Integer.parseInt(num);
            this.setText(valor.toString());
        } catch (NumberFormatException e) {
            valor = 0;
            this.setText(valor.toString());
        }
    }

}
