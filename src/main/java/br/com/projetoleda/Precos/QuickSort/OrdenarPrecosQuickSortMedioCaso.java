package br.com.projetoleda.Precos.QuickSort;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import br.com.projetoleda.EstruturasDeDados.Fila;
import br.com.projetoleda.EstruturasDeDados.ListaDuplamenteEncadeada;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class OrdenarPrecosQuickSortMedioCaso {
    private static final String caminhoArquivoParaSerLido = "./Dados/games_formated_release_data.csv";
    private static final String CAMINHO_ARQUIVO_GERADO = "./Dados/games_price_QuickSort_medioCaso.csv";

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

            quickSort(lista, 0, lista.length - 1);

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

    public static void quickSort(CSVRecord[] lista, int primeiroIndice, int ultimoIndice){
        if(primeiroIndice < ultimoIndice){
            int pivot = particaoPivotQuickSort(lista, primeiroIndice, ultimoIndice);
            quickSort(lista, primeiroIndice, pivot - 1);
            quickSort(lista, pivot + 1, ultimoIndice);
        }
    }

    public static int particaoPivotQuickSort(CSVRecord[] lista, int primeiroIndice, int ultimoIndice) {
    double pivot = extrairValor(lista, primeiroIndice);
    int i = primeiroIndice - 1;
    int j = ultimoIndice + 1;

    while (true) {
        do {
            i++;
        } while (extrairValor(lista, i) < pivot);

        do {
            j--;
        } while (extrairValor(lista, j) > pivot);

        if (i >= j) {
            return j;
        }

        trocarElementos(lista, i, j);
    }
}

    public static void trocarElementos(CSVRecord[] lista, int primeiroIndice, int segundoIndice){
        CSVRecord recordTemporario = lista[primeiroIndice];
        lista[primeiroIndice] = lista[segundoIndice];
        lista[segundoIndice] = recordTemporario;
    }

    public static double extrairValor(CSVRecord[] lista, int indice){
        try{
            return Double.parseDouble(lista[indice].get(6));
        }catch(Exception e){
            return 0.0;
        }
    }
}
