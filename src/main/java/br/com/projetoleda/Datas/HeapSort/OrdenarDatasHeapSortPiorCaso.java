package br.com.projetoleda.Datas.HeapSort;

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

public class OrdenarDatasHeapSortPiorCaso {
    private static final String caminhoArquivoParaSerLido = "./Dados/games_releaseData_Invertido.csv";
    private static final String CAMINHO_ARQUIVO_GERADO = "./Dados/games_release_date_HeapSort_piorCaso.csv";

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
            
            heapSort(lista);

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

    static void heapify(CSVRecord[] lista, int n, int i) {

        int largest = i; 

        int l = 2 * i + 1; 

        int r = 2 * i + 2;

        
        Date dataMaior = extrairValor(lista, largest);

        if(l < n){
            Date dataNoEsquerdo = extrairValor(lista, l);
            if(dataNoEsquerdo.compareTo(dataMaior) > 0){
                largest = l;
                dataMaior = dataNoEsquerdo;
            }
        }

        if(r < n){
            Date dataNoDireito = extrairValor(lista, r);
            if(dataNoDireito.compareTo(dataMaior) > 0){
                largest = r;
            }
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

    public static Date extrairValor(CSVRecord[] lista, int indice){
        try{

            String data = lista[indice].get(2);
            SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
            dataFormatada.setLenient(false);

            return dataFormatada.parse(data);

        }catch(ParseException | NullPointerException e ){

            try{
                return new SimpleDateFormat("dd/MM/yyyy").parse("01/01/0001");
            } catch(ParseException ex){
                return new Date(0);
            }
        }
    }
}
