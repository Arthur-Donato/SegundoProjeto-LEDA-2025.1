package br.com.projetoleda.Precos.QuickSort;

public class GerarArquivoPrecosQuickSort{
    public static void main(String[] args) {
        OrdenarPrecosQuickSortMedioCaso.gerarArquivo();
        OrdenarPrecosQuickSortMelhorCaso.gerarArquivo();
        OrdenarPrecosQuickSortPiorCaso.gerarArquivo();

        System.out.println("Ordenação do campo preço usando o quick sort concluída!");
    }
}