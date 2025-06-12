package br.com.projetoleda.EstruturasDeDados;

import org.apache.commons.csv.CSVRecord;

public class RegistroComparavel implements Comparable<RegistroComparavel>{

    final static Integer CHAVE_DE_COMPARACAO = 0;
    private final CSVRecord registroOriginal;

    public RegistroComparavel(CSVRecord registroOriginal) {
        this.registroOriginal = registroOriginal;
    }

    public String get(Integer numeroColuna) {
        return this.registroOriginal.get(numeroColuna);
    }

    @Override
    public int compareTo(RegistroComparavel o) {
        String valorPrimeiroRecord = this.registroOriginal.get(CHAVE_DE_COMPARACAO);
        String valorSegundoRecord = o.get(CHAVE_DE_COMPARACAO);

        try{
            Integer idPrimeiroRecord = Integer.parseInt(valorPrimeiroRecord);
            Integer idSegundoRecord = Integer.parseInt(valorSegundoRecord);

            return idPrimeiroRecord.compareTo(idSegundoRecord);
        } catch(NumberFormatException e){
            return valorPrimeiroRecord.compareTo(valorSegundoRecord);
        }
    }

    @Override
    public String toString() {
        if (this.registroOriginal != null) {
            return this.registroOriginal.toMap().toString();
        }
        return "Registro Inválido";
    }
}
