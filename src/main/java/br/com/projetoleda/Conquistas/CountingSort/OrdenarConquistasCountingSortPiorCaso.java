package br.com.projetoleda.Conquistas.CountingSort;

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

public class OrdenarConquistasCountingSortPiorCaso {
    private static final String caminhoArquivoParaSerLido = "./Dados/games_achievements_Invertido.csv";
    private static final String CAMINHO_ARQUIVO_GERADO = "./Dados/games_achievements_CountingSort_piorCaso.csv";
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

            CSVRecord[] listaFinal = countingSort(lista);

            Fila<CSVRecord> filaEscrita = new Fila<>(listaFinal.length);

            for(int j = 0; j < lista.length; j++){
                filaEscrita.enfileirar(listaFinal[j]);
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
