package br.com.projetoleda.Precos.InsertionSort;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import br.com.projetoleda.EstruturasDeDados.Fila;
import br.com.projetoleda.EstruturasDeDados.ListaDuplamenteEncadeada;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class OrdenarPrecosInsertionSortMedioCaso{
    private static final String caminhoArquivoParaSerLido = "./Dados/games_formated_release_data.csv";
    private static final String CAMINHO_ARQUIVO_GERADO = "./Dados/games_price_InsertionSort_medioCaso.csv";

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
            Double[] precos = new Double[lista.length];

            int i = 0;
            for(CSVRecord record : lista){
                try{
                    precos[i] = Double.parseDouble(record.get(6));
                }catch (Exception e){
                    precos[i] = 0.0;
                }
                i++;
            }

            insertionSort(lista, precos, 0, lista.length - 1);

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

    public static void insertionSort(CSVRecord[] lista, Double[] precos, int primeiroIndice, int ultimoIndice){
        for(int j = primeiroIndice + 1; j < ultimoIndice; j++){
            double conquistasPosicaoJ = precos[j];
            CSVRecord chave = lista[j];
            double chaveConquista = precos[j];
            int i = j - 1;
            while(i >= primeiroIndice && precos[i] > conquistasPosicaoJ){
                lista[i + 1] = lista[i];
                precos[i + 1] = precos[i];
                i--;
            }
            lista[i + 1] = chave;
            precos[i + 1] = chaveConquista;
        }

    }
}
