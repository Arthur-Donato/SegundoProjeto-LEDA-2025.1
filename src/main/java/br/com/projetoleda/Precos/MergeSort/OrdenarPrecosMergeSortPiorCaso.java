package br.com.projetoleda.Precos.MergeSort;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import br.com.projetoleda.EstruturasDeDados.Fila;
import br.com.projetoleda.EstruturasDeDados.ListaDuplamenteEncadeada;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class OrdenarPrecosMergeSortPiorCaso { 
    private static final String caminhoArquivoParaSerLido = "./Dados/games_price_Invertido.csv";
    private static final String CAMINHO_ARQUIVO_GERADO = "./Dados/games_price_MergeSort_piorCaso.csv";
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

            mergeSort(lista, 0, lista.length - 1);

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

    public static void mergeSort(CSVRecord[] lista, int primeiroIndice, int ultimoIndice){
        
        if(primeiroIndice < ultimoIndice){
            int indiceDoMeio = primeiroIndice + (ultimoIndice - primeiroIndice) / 2;

            mergeSort(lista, primeiroIndice, indiceDoMeio);
            mergeSort(lista, indiceDoMeio + 1, ultimoIndice);

            merge(lista, primeiroIndice, indiceDoMeio, ultimoIndice);
            
        }
        
    }

    public static void merge(CSVRecord[] lista, int primeiroIndice, int indiceDoMeio, int ultimoIndice) throws NumberFormatException{

        int tamanhoArrayLeft = indiceDoMeio - primeiroIndice + 1;
        int tamanhoArrayRight = ultimoIndice - indiceDoMeio;
        
        CSVRecord[] arrayLeft = new CSVRecord[tamanhoArrayLeft];
        CSVRecord[] arrayRight = new CSVRecord[tamanhoArrayRight];

        for(int i = 0; i < tamanhoArrayLeft; i++){
            arrayLeft[i] = lista[primeiroIndice + i];
        }

        for(int  j = 0; j < tamanhoArrayRight; j++){
            arrayRight[j] = lista[indiceDoMeio + 1 + j];
        }

        int indiceArrayLeft = 0;
        int indiceArrayRight = 0;
        int indiceArrayFinal = primeiroIndice;

        while(indiceArrayLeft < tamanhoArrayLeft && indiceArrayRight < tamanhoArrayRight){
            double precoArrayEsquerda = extrairValor(arrayLeft, indiceArrayLeft);
            double precoArrayDireita = extrairValor(arrayRight, indiceArrayRight);
            

            if(precoArrayEsquerda <= precoArrayDireita){
                lista[indiceArrayFinal] = arrayLeft[indiceArrayLeft];
                indiceArrayLeft++;
            }
            else{
                lista[indiceArrayFinal] = arrayRight[indiceArrayRight];
                indiceArrayRight++;
            }
            indiceArrayFinal++;
        }

        while(indiceArrayLeft < tamanhoArrayLeft){
            lista[indiceArrayFinal] = arrayLeft[indiceArrayLeft];
            indiceArrayFinal++;
            indiceArrayLeft++;
        }

        while(indiceArrayRight < tamanhoArrayRight){
            lista[indiceArrayFinal] = arrayRight[indiceArrayRight];
            indiceArrayFinal++;
            indiceArrayRight++;
        }
    }

    public static double extrairValor(CSVRecord[] lista, int indice){
        try{
            return Double.parseDouble(lista[indice].get(6));
        } catch(Exception e){
            return 0.0;
        }
    }
}