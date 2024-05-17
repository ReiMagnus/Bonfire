package org.reimagnus.bonfire.modelos;

import java.util.Arrays;

public class ModeloPronto {

    public Modelo modelo;

    public ModeloPronto(ProjetoModelo pm) {
        modelo = pm.modelo;
    }

    @Override
    public String toString() {
        return String.format("%s %s.%s.%s",
                modelo.getNomeModelo(),
                modelo.getVersaoModelo()[0],
                modelo.getVersaoModelo()[1],
                modelo.getVersaoModelo()[2]
        );
    }
}
