package br.com.projetoleda.Datas.InsertionSort;

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

public class OrdenarDatasInsertionSortPiorCaso {
    private static final String caminhoArquivoParaSerLido = "./Dados/games_releaseData_Invertido.csv";
    private static final String CAMINHO_ARQUIVO_GERADO = "./Dados/games_release_date_InsertionSort_piorCaso.csv";
    public static void gerarArquivo() {
        try {
            FileReader leitorDoArquivo = new FileReader(caminhoArquivoParaSerLido);
            CSVParser parserContagem = CSVFormat.RFC4180.parse(leitorDoArquivo);

            int registrosValidos = 0;
            for (CSVRecord record : parserContagem) {
                if (record.getRecordNumber() != 1 && record.size() > 2) {
                    registrosValidos++;
                }
            }
            leitorDoArquivo.close();

            CSVRecord[] lista = new CSVRecord[registrosValidos];
            Date[] datas = new Date[registrosValidos];

            FileReader leitorFinal = new FileReader(caminhoArquivoParaSerLido);
            CSVParser parser = CSVFormat.RFC4180.parse(leitorFinal);
            CSVPrinter escritorDeArquivo = new CSVPrinter(new FileWriter(CAMINHO_ARQUIVO_GERADO, false), CSVFormat.DEFAULT);

            SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
            dataFormatada.setLenient(false);

            int i = 0;
            for (CSVRecord record : parser) {
                if (record.getRecordNumber() == 1) {
                    escritorDeArquivo.printRecord(record); // Cabeçalho
                } else if (record.size() > 2) {
                    lista[i] = record;
                    try {
                        datas[i] = dataFormatada.parse(record.get(2));
                    } catch (ParseException | NullPointerException e) {
                        datas[i] = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/0001");
                    }
                    i++;
                }
            }

            insertionSort(lista, datas, 0, lista.length - 1);

            for (CSVRecord record : lista) {
                escritorDeArquivo.printRecord(record);
            }

            escritorDeArquivo.flush();
            escritorDeArquivo.close();
            leitorFinal.close();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void insertionSort(CSVRecord[] lista, Date[] conquistas, int primeiroIndice, int ultimoIndice){
        for(int j = primeiroIndice + 1; j <= ultimoIndice; j++){
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
