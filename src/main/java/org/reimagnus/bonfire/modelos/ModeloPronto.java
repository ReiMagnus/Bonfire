package org.reimagnus.bonfire.modelos;

import java.util.Objects;

public class ModeloPronto implements Comparable<ModeloPronto> {

    public Modelo modelo;

    public ModeloPronto(ProjetoModelo pm) { // Refazer esse cara aqui
        modelo = new Modelo(pm.modelo);
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s.%s.%s",
            modelo.getNomeModelo(),
            modelo.getCriadorModelo(),
            modelo.getVersaoModelo()[0],
            modelo.getVersaoModelo()[1],
            modelo.getVersaoModelo()[2]
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModeloPronto that = (ModeloPronto) o;
        return Objects.equals(modelo.getIdModelo(), that.modelo.getIdModelo());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(modelo.getIdModelo());
    }

    @Override
    public int compareTo(ModeloPronto o) { // Serve mais para verificar se é possivel atualizar a ficha para uma nova
        int n = -1; //Não achou um id igual

        if(modelo.getIdModelo().equals(o.modelo.getIdModelo())) {
            n = 1; //Achou um id igual, mas um versão igual ou menor
            String[] myVersao    = modelo.getVersaoModelo();
            String[] otherVersao = o.modelo.getVersaoModelo();
            for(byte i = 0; i < 3; i++) {
                if(Integer.parseInt(myVersao[i]) > Integer.parseInt(otherVersao[i])) {
                    n = 0; //Achou um id igual, versão desatualizada, bora atualizar
                    break;
                }
            }
        }

        return n;
    }
}
