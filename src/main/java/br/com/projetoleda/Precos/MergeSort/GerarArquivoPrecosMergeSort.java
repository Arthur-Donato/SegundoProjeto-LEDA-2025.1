package br.com.projetoleda.Precos.MergeSort;

public class GerarArquivoPrecosMergeSort {
    public static void main(String[] args) {
        OrdenarPrecosMergeSortMedioCaso.gerarArquivo();
        OrdenarPrecosMergeSortMelhorCaso.gerarArquivo();
        OrdenarPrecosMergeSortPiorCaso.gerarArquivo();

        System.out.println("Ordenação do campo preço usando o merge sort concluída!");
    }
}
