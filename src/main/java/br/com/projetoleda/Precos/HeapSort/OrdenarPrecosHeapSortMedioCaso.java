package br.com.projetoleda.Precos.HeapSort;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import br.com.projetoleda.EstruturasDeDados.Fila;
import br.com.projetoleda.EstruturasDeDados.ListaDuplamenteEncadeada;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class OrdenarPrecosHeapSortMedioCaso {
    private static final String caminhoArquivoParaSerLido = "./Dados/games_formated_release_data.csv";
    private static final String CAMINHO_ARQUIVO_GERADO = "./Dados/games_price_HeapSort_medioCaso.csv";

    public static void gerarArquivo() {

        try{
            FileReader leitorFinal = new FileReader(caminhoArquivoParaSerLido);
            CSVPrinter escritorDeArquivo = new CSVPrinter(new FileWriter(CAMINHO_ARQUIVO_GERADO, true), CSVFormat.DEFAULT);
            CSVParser parser = CSVFormat.RFC4180.parse(leitorFinal);

            ListaDuplamenteEncadeada<CSVRecord> listaDuplamenteEncadeada = new ListaDuplamenteEncadeada<>();
            for(CSVRecord record : parser) {
                if(record.getRecordNumber() == 1){
                    escritorDeArquivo.printRecord(record);
                }
                else if(record.size() > 2){
                    listaDuplamenteEncadeada.insert(record);

                }

            }

            CSVRecord[] lista = listaDuplamenteEncadeada.toArray(new CSVRecord[listaDuplamenteEncadeada.size()]);

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


        if (l < n && extrairPreco(lista, l) > extrairPreco(lista, largest)) {
            largest = l;
        }

        if (r < n && extrairPreco(lista, r) > extrairPreco(lista, largest)) {
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

    public static double extrairPreco(CSVRecord[] lista, int indice){
        try{
            return Double.parseDouble(lista[indice].get(6));
        } catch(Exception e){
            return 0.0;
        }
    }
}
