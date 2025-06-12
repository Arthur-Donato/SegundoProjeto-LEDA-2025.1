package br.com.projetoleda.Conquistas.QuickSortMediana3;

public class GerarArquivoConquistasQuickSortMediana3 {
    public static void main(String[] args) {
        OrdenarConquistasQuickSortMediana3MedioCaso.gerarArquivo();
        OrdenarConquistasQuickSortMediana3MelhorCaso.gerarArquivo();
        OrdenarConquistasQuickSortMediana3PiorCaso.gerarArquivo();

        System.out.println("Ordenação do campo conquistas usando o quick sort mediana de 3 concluída!");
    }
}
