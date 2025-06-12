package br.com.projetoleda.Conquistas.QuickSortMediana3;

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

public class OrdenarConquistasQuickSortMediana3MedioCaso {
     private static final String caminhoArquivoParaSerLido = "./Dados/games_formated_release_data.csv";
    private static final String CAMINHO_ARQUIVO_GERADO = "./Dados/games_achievements_QuickSortMediana3_medioCaso.csv";

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

            quickSort(lista, 0, lista.length - 1);

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

    public static void quickSort(CSVRecord[] lista, int primeiroIndice, int ultimoIndice){
        if(primeiroIndice < ultimoIndice){
            int pivot = particaoPivotQuickSort(lista, primeiroIndice, ultimoIndice);
            quickSort(lista, primeiroIndice, pivot);
            quickSort(lista, pivot + 1, ultimoIndice);
        }
    }

    public static int particaoPivotQuickSort(CSVRecord[] lista, int primeiroIndice, int ultimoIndice) {
    int indiceMeio = (primeiroIndice + ultimoIndice) / 2;
    int posicaoPivot = medianaDe3(lista, primeiroIndice, indiceMeio, ultimoIndice);

    // Move o pivot escolhido para o início da partição
    trocarElementos(lista, primeiroIndice, posicaoPivot);
    double pivotValor = extrairValor(lista, primeiroIndice);

    int i = primeiroIndice - 1;
    int j = ultimoIndice + 1;

    while (true) {
        do {
            i++;
        } while (extrairValor(lista, i) > pivotValor);

        do {
            j--;
        } while (extrairValor(lista, j) < pivotValor);

        if (i >= j) {
            return j;
        }

        trocarElementos(lista, i, j);
    }
}

    public static int medianaDe3(CSVRecord[] lista, int primeiroIndice, int indiceMeio, int ultimoIndice){
        int valorPrimeiroIndice = extrairValor(lista, primeiroIndice);
        int valorIndiceMeio = extrairValor(lista, indiceMeio);
        int valorUltimoIndice = extrairValor(lista, ultimoIndice);

        if(valorPrimeiroIndice > valorIndiceMeio){
            trocarElementos(lista, primeiroIndice, indiceMeio);
        }
        if(valorPrimeiroIndice > valorUltimoIndice){
            trocarElementos(lista, primeiroIndice, ultimoIndice);
        }
        if(valorIndiceMeio > valorUltimoIndice){
            trocarElementos(lista, indiceMeio, ultimoIndice);
        }

        return indiceMeio;
    }

    public static void trocarElementos(CSVRecord[] lista, int primeiroIndice, int segundoIndice){
        CSVRecord recordTemporario = lista[primeiroIndice];
        lista[primeiroIndice] = lista[segundoIndice];
        lista[segundoIndice] = recordTemporario;
    }

    public static int extrairValor(CSVRecord[] lista, int indice){
        try{
            return Integer.parseInt(lista[indice].get(26));
        }catch(Exception e){
            return 0;
        }
    }
}
