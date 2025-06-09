package br.com.projetoleda;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatarData {

    public static String formatarData(String dataNaoFormatada){
        String dataFormatada = "";

        if(dataNaoFormatada.equals("Release date")){
            return dataNaoFormatada;
        }
        else{
            SimpleDateFormat formatoDeEntrada = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
            try {
                Date data = formatoDeEntrada.parse(dataNaoFormatada);

                SimpleDateFormat formatoDeSaida = new SimpleDateFormat("dd/MM/yyyy");

                dataFormatada = formatoDeSaida.format(data);

            } catch (ParseException e) {
               
            }
            
            return dataFormatada;
        }
        
    }
    
}
