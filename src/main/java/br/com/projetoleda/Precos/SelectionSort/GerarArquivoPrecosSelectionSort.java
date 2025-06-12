package br.com.projetoleda.Precos.SelectionSort;

public class GerarArquivoPrecosSelectionSort {
    public static void main(String[] args) {
        OrdenarPrecosSelectionSortMedioCaso.gerarArquivo();
        OrdenarPrecosSelectionSortMelhorCaso.gerarArquivo();
        OrdenarPrecosSelectionSortPiorCaso.gerarArquivo();

        System.out.println("Ordenação do campo preço usando o selection sort concluída!");
    }
}
