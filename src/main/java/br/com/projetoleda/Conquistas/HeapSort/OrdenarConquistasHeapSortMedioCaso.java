package br.com.projetoleda.Conquistas.HeapSort;

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

public class OrdenarConquistasHeapSortMedioCaso {
    private static final String caminhoArquivoParaSerLido = "./Dados/games_formated_release_data.csv";
    private static final String CAMINHO_ARQUIVO_GERADO = "./Dados/games_achievements_HeapSort_medioCaso.csv";

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

            heapSort(lista);

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

    static void heapify(CSVRecord[] lista, int n, int i) {

        int largest = i; 

        int l = 2 * i + 1; 

        int r = 2 * i + 2;


        if (l < n && extrairValor(lista, l) < extrairValor(lista, largest)) {
            largest = l;
        }

        if (r < n && extrairValor(lista, r) < extrairValor(lista, largest)) {
            largest = r;
        }

        if (largest != i) {
            CSVRecord temp = lista[i];
            lista[i] = lista[largest];
            lista[largest] = temp;

            heapify(lista, n, largest);
        }
    }

    static void heapSort(CSVRecord[] lista) {
        int n = lista.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(lista, n, i);
        }

        for (int i = n - 1; i > 0; i--) {

            CSVRecord temp = lista[0]; 
            lista[0] = lista[i];
            lista[i] = temp;

            heapify(lista, i, 0);
        }
    }

    public static void trocarElementos(CSVRecord[] lista, int primeiroIndice, int segundoIndice){
        CSVRecord numeroTemporario = lista[primeiroIndice];
        lista[primeiroIndice] = lista[segundoIndice];
        lista[segundoIndice] = numeroTemporario;
    }

    public static int extrairValor(CSVRecord[] lista, int indice){
        try{
            return Integer.parseInt(lista[indice].get(26));
        } catch(Exception e){
            return 0;
        }
    }
}
