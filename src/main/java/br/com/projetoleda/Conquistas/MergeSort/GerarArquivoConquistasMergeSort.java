package br.com.projetoleda.Conquistas.MergeSort;

public class GerarArquivoConquistasMergeSort {
    public static void main(String[] args) {
        OrdenarConquistasMedioCaso.gerarArquivo();
        OrdenarConquistasMelhorCaso.gerarArquivo();
        OrdenarConquistasPiorCaso.gerarArquivo();

        System.out.println("Ordenação do campo conquistas usando o merge sort concluída!");
    }
}
