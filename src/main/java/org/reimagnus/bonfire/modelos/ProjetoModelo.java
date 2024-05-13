package org.reimagnus.bonfire.modelos;

import java.util.List;
import java.util.Random;

public class ProjetoModelo {

    public Modelo modelo;

    public ProjetoModelo(String nome, String criador) {
        modelo = new Modelo(geradorID(), nome, criador);
    }

    private String geradorID() {
        Random random = new Random();
        String[] letras = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        String id = "";
        for(byte i = 0; i < 10; i++) {
            int ale = random.nextInt(36);
            id += letras[ale];
        }
        return id;
    }

}
