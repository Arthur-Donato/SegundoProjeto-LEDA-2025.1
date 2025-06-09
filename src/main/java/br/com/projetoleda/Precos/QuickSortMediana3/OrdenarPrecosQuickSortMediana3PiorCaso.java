package br.com.projetoleda.Precos.QuickSortMediana3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class OrdenarPrecosQuickSortMediana3PiorCaso {
    private static final String caminhoArquivoParaSerLido = "./Dados/games_price_Invertido.csv";
    private static final String CAMINHO_ARQUIVO_GERADO = "./Dados/games_price_QuickSortMediana3_piorCaso.csv";

    public static void gerarArquivo() {
        
        try{
            FileReader leitorDoArquivo = new FileReader(caminhoArquivoParaSerLido);
            BufferedReader reader = new BufferedReader(leitorDoArquivo);


            int contadorLinhas = 0;
            while(reader.readLine() != null){
                contadorLinhas++;
            }

            leitorDoArquivo.close();
            reader.close();

            FileReader leitorFinal = new FileReader(caminhoArquivoParaSerLido);
            CSVPrinter escritorDeArquivo = new CSVPrinter(new FileWriter(CAMINHO_ARQUIVO_GERADO, true), CSVFormat.DEFAULT);
            CSVParser parser = CSVFormat.RFC4180.parse(leitorFinal);
            
            CSVRecord[] lista = new CSVRecord[contadorLinhas];

            int i = 0;
            for(CSVRecord record : parser) {
                if(record.getRecordNumber() == 1){
                    escritorDeArquivo.printRecord(record);
                }
                else if(record.size() > 2){
                    lista[i] = record;
                    i++;
                }
                
            }
            
            quickSort(lista, 0, lista.length - 1);

            for(CSVRecord record : lista){
                escritorDeArquivo.printRecord(record);
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
            quickSort(lista, primeiroIndice, pivot);
            quickSort(lista, pivot + 1, ultimoIndice);
        }
    }

    public static int particaoPivotQuickSort(CSVRecord[] lista, int primeiroIndice, int ultimoIndice) {
    int indiceMeio = (primeiroIndice + ultimoIndice) / 2;
    int posicaoPivot = medianaDe3(lista, primeiroIndice, indiceMeio, ultimoIndice);

    // Move o pivot escolhido para o início da partição
    trocarElementos(lista, primeiroIndice, posicaoPivot);
    double pivotValor = extrairValor(lista, primeiroIndice);

    int i = primeiroIndice - 1;
    int j = ultimoIndice + 1;

    while (true) {
        do {
            i++;
        } while (extrairValor(lista, i) < pivotValor);

        do {
            j--;
        } while (extrairValor(lista, j) > pivotValor);

        if (i >= j) {
            return j;
        }

        trocarElementos(lista, i, j);
    }
}

    public static int medianaDe3(CSVRecord[] lista, int primeiroIndice, int indiceMeio, int ultimoIndice){
        double valorPrimeiroIndice = extrairValor(lista, primeiroIndice);
        double valorIndiceMeio = extrairValor(lista, indiceMeio);
        double valorUltimoIndice = extrairValor(lista, ultimoIndice);

        if(valorPrimeiroIndice > valorIndiceMeio){
            trocarElementos(lista, primeiroIndice, indiceMeio);
        }
        if(valorPrimeiroIndice > valorUltimoIndice){
            trocarElementos(lista, primeiroIndice, ultimoIndice);
        }
        if(valorIndiceMeio > valorUltimoIndice){
            trocarElementos(lista, indiceMeio, ultimoIndice);
        }

        return indiceMeio;
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
