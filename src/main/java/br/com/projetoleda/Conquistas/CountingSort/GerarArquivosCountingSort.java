package br.com.projetoleda.Conquistas.CountingSort;

public class GerarArquivosCountingSort {
    public static void main(String[] args) {
        OrdenarConquistasCountingSortMedioCaso.gerarArquivo();
        OrdenarConquistasCountingSortMelhorCaso.gerarArquivo();
        OrdenarConquistasCountingSortPiorCaso.gerarArquivo();

        System.out.println("Ordenação do campo conquistas usando o counting sort concluída!");
    }
}
