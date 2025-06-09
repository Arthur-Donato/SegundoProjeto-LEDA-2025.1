package br.com.projetoleda.Conquistas.InsertionSort;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class OrdenarConquistasInsertionSortMedioCaso {
    private static final String caminhoArquivoParaSerLido = "./Dados/games_formated_release_data.csv";
    private static final String CAMINHO_ARQUIVO_GERADO = "./Dados/games_achievements_InsertionSort_medioCaso.csv";
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
            
            insertionSort(lista, conquistas, 0, lista.length - 1);

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
    public static void insertionSort(CSVRecord[] lista,int[] conquistas, int primeiroIndice, int ultimoIndice){
        for(int j = primeiroIndice + 1; j < ultimoIndice; j++){
            int conquistasPosicaoJ = conquistas[j];
            CSVRecord chave = lista[j];
            int chaveConquista = conquistas[j];
            int i = j - 1;
            while(i >= primeiroIndice && conquistas[i] < conquistasPosicaoJ){
                lista[i + 1] = lista[i];
                conquistas[i + 1] = conquistas[i];
                i--;
            }
            lista[i + 1] = chave;
            conquistas[i + 1] = chaveConquista;
        }

    }
}
