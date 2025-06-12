package br.com.projetoleda.Conquistas.HeapSort;

public class GerarArquivoConquistasHeapSort {
    public static void main(String[] args) {
        OrdenarConquistasHeapSortMedioCaso.gerarArquivo();
        OrdenarConquistasHeapSortMelhorCaso.gerarArquivo();
        OrdenarConquistasHeapSortPiorCaso.gerarArquivo();

        System.out.println("Ordenação do campo conquistas usando o heap sort concluída!");
    }
}
