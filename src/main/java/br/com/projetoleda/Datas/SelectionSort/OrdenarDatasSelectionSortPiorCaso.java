package br.com.projetoleda.Datas.SelectionSort;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class OrdenarDatasSelectionSortPiorCaso {
    private static final String caminhoArquivoParaSerLido = "./Dados/games_releaseData_Invertido.csv";
    private static final String CAMINHO_ARQUIVO_GERADO = "./Dados/games_release_date_SelectionSort_piorCaso.csv";
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
            Date[] datas = new Date[contadorLinhas];

            SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
            dataFormatada.setLenient(false);

            int i = 0;
            for(CSVRecord record : parser) {
                if(record.getRecordNumber() == 1){
                    escritorDeArquivo.printRecord(record);
                }
                else if(record.size() > 2){
                    lista[i] = record;
                    try{
                        datas[i] = dataFormatada.parse(record.get(2));
                    } catch(ParseException | NullPointerException e){
                        
                        datas[i] = new Date(0);
                    }
                    i++;
                }
                
            }
            
            selectionSort(lista, datas, 0, lista.length - 1);

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

    public static void selectionSort(CSVRecord[] lista ,Date[] datas, int primeiroIndice, int ultimoIndice){
        boolean houveTroca = false;
        for(int i = primeiroIndice; i < ultimoIndice; i++){
            int minimo = i;
            for(int j = i + 1; j <= ultimoIndice; j++){

                if(datas[j] != null && datas[minimo] != null && datas[j].compareTo(datas[minimo]) < 0){
                    minimo = j;
                    houveTroca = true;
                }
            }
            if(!houveTroca){
                return;
            }
            trocarElementos(lista, datas, i, minimo);
        }
    }

    public static void trocarElementos(CSVRecord[] lista,Date[] datas, int primeiroIndice, int segundoIndice){
        CSVRecord recordTemporario = lista[primeiroIndice];
        lista[primeiroIndice] = lista[segundoIndice];
        lista[segundoIndice] = recordTemporario;

        Date dataTemporaria = datas[primeiroIndice];
        datas[primeiroIndice] = datas[segundoIndice];
        datas[segundoIndice] = dataTemporaria;
    }

}
