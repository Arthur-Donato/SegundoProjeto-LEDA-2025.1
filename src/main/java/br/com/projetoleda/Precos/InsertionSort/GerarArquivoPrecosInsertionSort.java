package br.com.projetoleda.Precos.InsertionSort;

public class GerarArquivoPrecosInsertionSort {
    public static void main(String[] args) {
        OrdenarPrecosInsertionSortMedioCaso.gerarArquivo();
        OrdenarPrecosInsertionSortMelhorCaso.gerarArquivo();
        OrdenarPrecosInsertionSortPiorCaso.gerarArquivo();

        System.out.println("Ordenação do campo preço usando o insertion sort concluída!");
    }
}
