package br.com.projetoleda.Datas.InsertionSort;

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

public class OrdenarDatasInsertionSortMedioCaso {
    private static final String caminhoArquivoParaSerLido = "./Dados/games_formated_release_data.csv";
    private static final String CAMINHO_ARQUIVO_GERADO = "./Dados/games_release_date_InsertionSort_medioCaso.csv";
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
                        
                        try{
                            datas[i]  = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/0001");
                        } catch(ParseException ex){
                            datas[i] =  new Date(0);
                        }
                    }
                    i++;
                }
                
            }
            
            insertionSort(lista, datas, 0, lista.length - 1);

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

    public static void insertionSort(CSVRecord[] lista, Date[] conquistas, int primeiroIndice, int ultimoIndice){
        for(int j = primeiroIndice + 1; j < ultimoIndice; j++){
            Date dataPosicaoJ = conquistas[j];
            CSVRecord chave = lista[j];
            Date chaveData = conquistas[j];
            int i = j - 1;
            while(i >= primeiroIndice && conquistas[i].compareTo(dataPosicaoJ) > 0){
                lista[i + 1] = lista[i];
                conquistas[i + 1] = conquistas[i];
                i--;
            }
            lista[i + 1] = chave;
            conquistas[i + 1] = chaveData;
        }

    }
}
