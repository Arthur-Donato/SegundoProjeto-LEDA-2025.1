package br.com.projetoleda.Conquistas.InsertionSort;

public class GerarArquivoConquistasInsertionSort {
    public static void main(String[] args) {
        OrdenarConquistasInsertionSortMedioCaso.gerarArquivo();
        OrdenarConquistasInsertionSortMelhorCaso.gerarArquivo();
        OrdenarConquistasInsertionSortPiorCaso.gerarArquivo();

        System.out.println("Ordenação do campo conquistas usando o insertion sort concluída!");
    }
}