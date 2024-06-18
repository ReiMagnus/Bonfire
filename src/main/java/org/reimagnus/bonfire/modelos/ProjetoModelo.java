package org.reimagnus.bonfire.modelos;

import java.io.Serializable;
import java.util.Random;

public class ProjetoModelo {

    public Modelo modelo;

    public ProjetoModelo() {
        modelo = new Modelo(geradorID());
    }

    public ProjetoModelo(SaveModelo sm) {
        modelo = new Modelo(sm);
    }

    private String geradorID() {
        Random random = new Random();
        String[] letras = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        String id = "";
        for(byte i = 0; i < 10; i++) {
            int ale = random.nextInt(letras.length);
            id += letras[ale];
        }
        return id;
    }

}
