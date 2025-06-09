package br.com.projetoleda;

//import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
public class GerarArquivoComDataFormatada {
    private static final String caminhoDoArquivoParaSerLido = "Dados/games.csv";
    private static final String ARQUIVO_GERADO = "Dados/games_formated_release_data.csv";


    public static void escrevendoArquivoComDatasFormatadas(){
        try {
            
            FileReader leitorDoArquivo = new FileReader(caminhoDoArquivoParaSerLido);
            CSVPrinter escritorDeArquvo = new CSVPrinter(new FileWriter(ARQUIVO_GERADO, true), CSVFormat.DEFAULT);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(leitorDoArquivo);

            for(CSVRecord record : records){
                escritorDeArquvo.printRecord(formatarDados(record));
            }
            escritorDeArquvo.flush();
            escritorDeArquvo.close();
            leitorDoArquivo.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object[] formatarDados(CSVRecord record){
        String[] dadosFormatados = new String[record.size()];

        for(int i = 0; i < record.size(); i++){

            String linhaASerLida = record.get(i);

            if(i == 2){
                dadosFormatados[i] = FormatarData.formatarData(linhaASerLida);
            }
            else{
                dadosFormatados[i] = linhaASerLida;
            }
        }

        return dadosFormatados;
    }
}
