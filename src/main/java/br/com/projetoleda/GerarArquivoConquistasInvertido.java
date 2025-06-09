package br.com.projetoleda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class GerarArquivoConquistasInvertido { 
    private static final String caminhoArquivoParaSerLido = "./Dados/games_formated_release_data.csv";
    private static final String CAMINHO_ARQUIVO_GERADO = "./Dados/games_achievements_Invertido.csv";
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
            
            mergeSort(lista, 0, lista.length - 1);

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

    public static void mergeSort(CSVRecord[] lista, int primeiroIndice, int ultimoIndice){
        
        if(primeiroIndice < ultimoIndice){
            int indiceDoMeio = primeiroIndice + (ultimoIndice - primeiroIndice) / 2;

            mergeSort(lista, primeiroIndice, indiceDoMeio);
            mergeSort(lista, indiceDoMeio + 1, ultimoIndice);

            merge(lista, primeiroIndice, indiceDoMeio, ultimoIndice);
            
        }
        
    }

    public static void merge(CSVRecord[] lista, int primeiroIndice, int indiceDoMeio, int ultimoIndice) {

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

    public static int extrairValor(CSVRecord[] lista, int indice){
        try{
            return Integer.parseInt(lista[indice].get(26));
        }catch(Exception e ){
            return 0;
        }
    }
}