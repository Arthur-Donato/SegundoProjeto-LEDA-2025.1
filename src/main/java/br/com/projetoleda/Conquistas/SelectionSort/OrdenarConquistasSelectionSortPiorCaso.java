package br.com.projetoleda.Conquistas.SelectionSort;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class OrdenarConquistasSelectionSortPiorCaso {
    private static final String caminhoArquivoParaSerLido = "./Dados/games_achievements_Invertido.csv";
    private static final String CAMINHO_ARQUIVO_GERADO = "./Dados/games_achievements_SelectionSort_piorCaso.csv";
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
            int[] conquistas = new int[contadorLinhas];

            int i = 0;
            for(CSVRecord record : parser) {
                if(record.getRecordNumber() == 1){
                    escritorDeArquivo.printRecord(record);
                }
                else if(record.size() > 2){
                    lista[i] = record;
                    try{
                        conquistas[i] = Integer.parseInt(record.get(26));
                    } catch(Exception e ){
                        conquistas[i] = 0;
                    }
                    
                    i++;
                }
                
            }
            
            selectionSort(lista, conquistas, 0, lista.length - 1);

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
