package br.com.projetoleda.Precos.QuickSortMediana3;

public class GerarArquivoPrecosQuickSortMediana3 {
    public static void main(String[] args) {
        OrdenarPrecosQuickSortMediana3.gerarArquivo();
        OrdenarPrecosQuickSortMediana3MelhorCaso.gerarArquivo();
        OrdenarPrecosQuickSortMediana3PiorCaso.gerarArquivo();

        System.out.println("Ordenação do campo preço usando o quick sort mediana de 3 concluída!");
    }
}
