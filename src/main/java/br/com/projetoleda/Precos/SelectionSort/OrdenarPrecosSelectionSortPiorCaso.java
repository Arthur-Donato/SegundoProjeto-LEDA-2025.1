package br.com.projetoleda.Precos.SelectionSort;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class OrdenarPrecosSelectionSortPiorCaso {
    private static final String caminhoArquivoParaSerLido = "./Dados/games_price_Invertido.csv";
    private static final String CAMINHO_ARQUIVO_GERADO = "./Dados/games_price_SelectionSort_piorCaso.csv";
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
            Double[] precos = new Double[contadorLinhas];

            int i = 0;
            for(CSVRecord record : parser) {
                if(record.getRecordNumber() == 1){
                    escritorDeArquivo.printRecord(record);
                }
                else if(record.size() > 2){
                    lista[i] = record;
                    try{
                        precos[i] = Double.parseDouble(record.get(6));
                    } catch(Exception e ){
                        precos[i] = 0.0;
                    }
                    
                    i++;
                }
                
            }
            
            selectionSort(lista, precos, 0, lista.length - 1);

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

    public static void selectionSort(CSVRecord[] lista, Double[] precos, int primeiroIndice, int ultimoIndice){
        for(int i = primeiroIndice; i < ultimoIndice; i++){
            int minimo = i;
            for(int j = i + 1; j < ultimoIndice; j++){

                if (precos[j] != null && precos[minimo] != null && precos[j] < precos[minimo]){
                    minimo = j;
                }
            }
            trocarElementos(lista, precos, i, minimo);
        }
    }

    public static void trocarElementos(CSVRecord[] lista, Double[] precos, int primeiroIndice, int segundoIndice){
        CSVRecord recordTemporario = lista[primeiroIndice];
        lista[primeiroIndice] = lista[segundoIndice];
        lista[segundoIndice] = recordTemporario;

        Double precoTemporario = precos[primeiroIndice];
        precos[primeiroIndice] = precos[segundoIndice];
        precos[segundoIndice] = precoTemporario;
    }
}
