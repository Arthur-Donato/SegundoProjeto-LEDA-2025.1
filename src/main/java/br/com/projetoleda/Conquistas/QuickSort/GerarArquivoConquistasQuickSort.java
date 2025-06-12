package br.com.projetoleda.Conquistas.QuickSort;

public class GerarArquivoConquistasQuickSort {
    public static void main(String[] args) {
        OrdenarConquistasQuickSortMedioCaso.gerarArquivo();
        OrdenarConquistasQuickSortMelhorCaso.gerarArquivo();
        OrdenarConquistasQuickSortPiorCaso.gerarArquivo();

        System.out.println("Ordenação do campo conquistas usando o quick sort concluída!");
    }
}
