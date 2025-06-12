package br.com.projetoleda.Conquistas.SelectionSort;

public class GerarArquivoConquistasSelectionSort {
    public static void main(String[] args) {
        OrdenarConquistasSelectionSort.gerarArquivo();
        OrdenarConquistasSelectionSortMelhorCaso.gerarArquivo();
        OrdenarConquistasSelectionSortPiorCaso.gerarArquivo();

        System.out.println("Ordenação do campo conquistas usando o selection sort concluída!");
    }
}
