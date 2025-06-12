package br.com.projetoleda;

public class Main {
    public static void main(String[] args) {
        

        GerarArquivoComDataFormatada.escrevendoArquivoComDatasFormatadas();
        GerarArquivoLinux.escrevendoArquivoComSuporteLinux();
        GerarArquivoComSuporteParaLinguaPortuguesa.escrevendoArquivoComSuporteParaLinguaPortuguesa();

        GerarArquivoConquistasInvertido.gerarArquivo();
        GerarArquivoDataInverso.gerarArquivo();
        GerarArquivoPrecoInvertido.gerarArquivo();

        System.out.println("Arquivos transformados e arquivos de ordem inversa gerados com sucesso !");
    }
}