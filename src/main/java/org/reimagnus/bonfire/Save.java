package org.reimagnus.bonfire;

import javafx.scene.control.Alert;
import org.reimagnus.bonfire.modelos.*;

import java.io.*;
import java.util.*;

public class Save {

    private static final String saveDirectory = "C:\\Users\\Cliente\\AppData\\Local\\Bonfire";
    private static final String downloadPath  = "C:\\Users\\Cliente\\Downloads";
    private static final String nameSave = "\\modelos.save";

    public static Map<Integer, ProjetoModelo> listaProjetos;
    public static Set<ModeloPronto>           listaModelos;
    public static Map<Integer, Personagem>    listaPersonagens;

    public static void carregandoArquivos() {
        System.out.println("Carregando arquivos salvos...");

        File file = new File(saveDirectory + nameSave);

        listaProjetos = new HashMap<>();
        listaModelos = new HashSet<>();
        listaPersonagens = new HashMap<>();

        if(file.exists() && file.getParentFile().exists()) {
            try (FileInputStream fin = new FileInputStream(saveDirectory + nameSave)) {
                ObjectInputStream ois = new ObjectInputStream(fin);

                Integer numModelos = (Integer) ois.readObject();
                for(int i = 0; i < numModelos; i++) {
                    SaveModelo saveModelo = (SaveModelo) ois.readObject();
                    switch(saveModelo.getFaseModelo()) {
                        case 1:
                            ProjetoModelo projetoModelo = new ProjetoModelo(saveModelo);
                            listaProjetos.put(listaProjetos.size(), projetoModelo);
                            break;
                        case 2:
                            ModeloPronto modeloPronto = new ModeloPronto(saveModelo);
                            listaModelos.add(modeloPronto);
                            break;
                        case 3:
                            Personagem personagem = new Personagem(saveModelo);
                            listaPersonagens.put(listaPersonagens.size(), personagem);
                            break;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void salvarArquivos() {
        new File(saveDirectory).mkdir();

        try (FileOutputStream fout = new FileOutputStream(saveDirectory + nameSave)) {
            ObjectOutputStream oos = new ObjectOutputStream(fout);

            Integer numModelos = listaProjetos.size()+listaModelos.size()+listaPersonagens.size();
            oos.writeObject(numModelos);

            // Salvando projetos de ficha
            for(Integer pm : listaProjetos.keySet()) {oos.writeObject(new SaveModelo(listaProjetos.get(pm).modelo));}
            // Salvando Modelos pronto
            for(ModeloPronto mp : listaModelos) {oos.writeObject(new SaveModelo(mp.modelo));}
            // Salvando Modelos pronto
            for(Integer p : listaPersonagens.keySet()) {oos.writeObject(new SaveModelo(listaPersonagens.get(p).modelo));}

            oos.close();
            System.out.println("Done");

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void exportarFicha(ModeloPronto mp) {

        String nomeArquivo = String.format(
                "\\%s_%s-%s-%s.bfp",
                mp.modelo.getNomeModelo(),
                mp.modelo.getVersaoModelo()[0],
                mp.modelo.getVersaoModelo()[1],
                mp.modelo.getVersaoModelo()[2]
        );

        try (FileOutputStream fout = new FileOutputStream(downloadPath + nomeArquivo)) {
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(new SaveModelo(mp.modelo));
            oos.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ficha criada");
            alert.setContentText("Arquivo da ficha criado em Download");
            alert.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
