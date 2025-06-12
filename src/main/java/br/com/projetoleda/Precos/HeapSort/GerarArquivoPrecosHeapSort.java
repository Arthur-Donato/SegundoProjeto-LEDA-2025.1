package br.com.projetoleda.Precos.HeapSort;

public class GerarArquivoPrecosHeapSort {
    public static void main(String[] args) {
        OrdenarPrecosHeapSortMedioCaso.gerarArquivo();
        OrdenarPrecosHeapSortMelhorCaso.gerarArquivo();
        OrdenarPrecosHeapSortPiorCaso.gerarArquivo();

        System.out.println("Ordenação do campo preço usando o heap sort concluída!");
    }
}
