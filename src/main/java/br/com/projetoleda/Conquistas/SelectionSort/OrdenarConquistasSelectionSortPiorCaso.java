package br.com.projetoleda.Conquistas.SelectionSort;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;

import br.com.projetoleda.EstruturasDeDados.ArvoreAVL;
import br.com.projetoleda.EstruturasDeDados.Fila;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class OrdenarConquistasSelectionSortPiorCaso {
    private static final String caminhoArquivoParaSerLido = "./Dados/games_achievements_Invertido.csv";
    private static final String CAMINHO_ARQUIVO_GERADO = "./Dados/games_achievements_SelectionSort_piorCaso.csv";
    public static void gerarArquivo() {

        try{

            Comparator<CSVRecord> comparadorPorId = (record1, record2) -> {
                String idStr1 = record1.get(0);
                String idStr2 = record2.get(0);

                Integer id1 = Integer.parseInt(idStr1.trim());
                Integer id2 = Integer.parseInt(idStr2.trim());

                return id1.compareTo(id2);
            };

            FileReader leitorFinal = new FileReader(caminhoArquivoParaSerLido);
            CSVPrinter escritorDeArquivo = new CSVPrinter(new FileWriter(CAMINHO_ARQUIVO_GERADO, true), CSVFormat.DEFAULT);
            CSVParser parser = CSVFormat.RFC4180.parse(leitorFinal);

            ArvoreAVL<CSVRecord> arvore = new ArvoreAVL<>(comparadorPorId);


            for(CSVRecord record : parser) {
                if(record.getRecordNumber() == 1){
                    escritorDeArquivo.printRecord(record);
                }
                else if(record.size() > 2){
                    arvore.inserir(record);
                }

            }

            CSVRecord[] lista = arvore.toArray(CSVRecord.class);
            int[] conquistas = new int[lista.length];
            int i = 0;

            for(CSVRecord record : lista){
                try{
                    conquistas[i] = Integer.parseInt(record.get(26));
                } catch(Exception e){
                    conquistas[i] = 0;
                }
                i++;

            }

            selectionSort(lista, conquistas, 0, lista.length - 1);

            Fila<CSVRecord> filaEscrita = new Fila<>(lista.length);

            for(int j = 0; j < lista.length; j++){
                filaEscrita.enfileirar(lista[j]);
            }

            for(int k = 0; k < filaEscrita.getQuantidadeDeElementos(); k++){
                escritorDeArquivo.printRecord(filaEscrita.desenfileirar());
            }

            escritorDeArquivo.flush();
            escritorDeArquivo.close();
            leitorFinal.close();

        }catch(IOException  e){
            e.printStackTrace();
        }
    }

    public static void selectionSort(CSVRecord[] lista, int[] conquistas, int primeiroIndice, int ultimoIndice){
        for(int i = primeiroIndice; i < ultimoIndice; i++){
            int minimo = i;
            for(int j = i + 1; j < ultimoIndice; j++){

                if(conquistas[j] > conquistas[minimo]){
                    minimo = j;
                }
            }
            trocarElementos(lista,conquistas, i, minimo);
        }
    }

    public static void trocarElementos(CSVRecord[] lista, int[] conquistas, int primeiroIndice, int segundoIndice){
        CSVRecord recordTemporario = lista[primeiroIndice];
        lista[primeiroIndice] = lista[segundoIndice];
        lista[segundoIndice] = recordTemporario;

        int conquistaTemporaria = conquistas[primeiroIndice];
        conquistas[primeiroIndice] = conquistas[segundoIndice];
        conquistas[segundoIndice] = conquistaTemporaria;
    }
}
