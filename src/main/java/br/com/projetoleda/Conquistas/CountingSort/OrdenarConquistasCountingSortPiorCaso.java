package br.com.projetoleda.Conquistas.CountingSort;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class OrdenarConquistasCountingSortPiorCaso {
    private static final String caminhoArquivoParaSerLido = "./Dados/games_achievements_Invertido.csv";
    private static final String CAMINHO_ARQUIVO_GERADO = "./Dados/games_achievements_CountingSort_piorCaso.csv";
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
            
            CSVRecord[] listaFinal = countingSort(lista);

            for(CSVRecord record : listaFinal){
                escritorDeArquivo.printRecord(record);
            }
            
            escritorDeArquivo.flush();
            escritorDeArquivo.close();
            leitorFinal.close();

        }catch(IOException  e){
            e.printStackTrace();
        }
    }

    public static CSVRecord[] countingSort(CSVRecord[] lista){
        int tamanhoArraySaida = lista.length;
        int tamanhoArrayAuxiliar = 0;

        for(int i = 0; i < tamanhoArraySaida; i++){
            tamanhoArrayAuxiliar = Math.max(tamanhoArrayAuxiliar, extrairValor(lista, i));
        }

        int[] listaAuxiliar = new int[tamanhoArrayAuxiliar + 1];

        for(int i = 0; i < tamanhoArraySaida; i++){
            listaAuxiliar[extrairValor(lista, i)]++;
        }

        for(int i = 1; i <= tamanhoArrayAuxiliar; i++){
            listaAuxiliar[i] += listaAuxiliar[i - 1];
        }

        CSVRecord[] arraySaida = new CSVRecord[tamanhoArraySaida];

        for(int i = tamanhoArraySaida - 1; i >= 0; i--){
            int valor = extrairValor(lista, i);
            int posicao = tamanhoArraySaida - listaAuxiliar[valor];
            arraySaida[posicao] = lista[i];
            listaAuxiliar[valor]--;
        }

        return arraySaida;
    }

    public static int extrairValor(CSVRecord[] lista, int indice){
        try{
            return Integer.parseInt(lista[indice].get(26));
        } catch(Exception e){
            return 0;
        }
    }
    
}
