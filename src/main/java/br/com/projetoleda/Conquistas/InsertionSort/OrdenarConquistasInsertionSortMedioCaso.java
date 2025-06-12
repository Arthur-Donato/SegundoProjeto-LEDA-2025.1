package br.com.projetoleda.Conquistas.InsertionSort;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;

import br.com.projetoleda.EstruturasDeDados.ArvoreAVL;
import br.com.projetoleda.EstruturasDeDados.Fila;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class OrdenarConquistasInsertionSortMedioCaso {
    private static final String caminhoArquivoParaSerLido = "./Dados/games_formated_release_data.csv";
    private static final String CAMINHO_ARQUIVO_GERADO = "./Dados/games_achievements_InsertionSort_medioCaso.csv";
    public static void gerarArquivo() {

        try{

            Comparator<CSVRecord> comparadorPorId = (record1, record2) -> {
                String idStr1 = record1.get(0);
                String idStr2 = record2.get(0);

                Integer id1 = Integer.parseInt(idStr1.trim());
                Integer id2 = Integer.parseInt(idStr2.trim());

                return id1.compareTo(id2);
            };

            FileReader leitorFinal = new FileReader(caminhoArquivoParaSerLido);
            CSVPrinter escritorDeArquivo = new CSVPrinter(new FileWriter(CAMINHO_ARQUIVO_GERADO, true), CSVFormat.DEFAULT);
            CSVParser parser = CSVFormat.RFC4180.parse(leitorFinal);

            ArvoreAVL<CSVRecord> arvore = new ArvoreAVL<>(comparadorPorId);


            for(CSVRecord record : parser) {
                if(record.getRecordNumber() == 1){
                    escritorDeArquivo.printRecord(record);
                }
                else if(record.size() > 2){
                    arvore.inserir(record);
                }

            }

            CSVRecord[] lista = arvore.toArray(CSVRecord.class);
            int[] conquistas = new int[lista.length];
            int i = 0;

            for(CSVRecord record : lista){
                try{
                    conquistas[i] = Integer.parseInt(record.get(26));
                } catch(Exception e){
                    conquistas[i] = 0;
                }
                i++;

            }

            insertionSort(lista, conquistas, 0, lista.length - 1);

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
