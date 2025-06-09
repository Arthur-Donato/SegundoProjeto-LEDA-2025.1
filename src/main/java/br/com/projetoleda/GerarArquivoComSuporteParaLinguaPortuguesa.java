package br.com.projetoleda;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class GerarArquivoComSuporteParaLinguaPortuguesa {

    private static final String caminhoDoArquivoParaSerLido = "./Dados/games.csv";
    private static final String ARQUIVO_GERADO = "./Dados/portuguese_supported_games.csv";

     public static void escrevendoArquivoComSuporteParaLinguaPortuguesa(){
        try {

            FileReader leitorDoArquivo = new FileReader(caminhoDoArquivoParaSerLido);
            CSVPrinter escritorDeArquivo = new CSVPrinter(new FileWriter(ARQUIVO_GERADO, true), CSVFormat.DEFAULT);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(leitorDoArquivo);
            

            for(CSVRecord record : records){
                if(record.getRecordNumber() == 1){
                    escritorDeArquivo.printRecord(record);
                }
                else if(record.get(10).contains("'Portuguese - Brazil'")){
                    escritorDeArquivo.printRecord(formatarDados(record));
                }
            }
            escritorDeArquivo.flush();
            escritorDeArquivo.close();
            leitorDoArquivo.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object[] formatarDados(CSVRecord record){
        String[] dadosDeJogosQueSuportamLinguaPortuguesa = new String[record.size()];

        if(record.get(10).contains("'Portuguese - Brazil'")){
            for(int i = 0; i < record.size(); i++){
                dadosDeJogosQueSuportamLinguaPortuguesa[i] = record.get(i);
            }
        }
        
        return dadosDeJogosQueSuportamLinguaPortuguesa;
    }
    
}
