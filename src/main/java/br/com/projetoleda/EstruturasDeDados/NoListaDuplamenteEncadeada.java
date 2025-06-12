package br.com.projetoleda.EstruturasDeDados;

import org.apache.commons.csv.CSVRecord;

public class NoListaDuplamenteEncadeada<T> {

    private T value;
    private NoListaDuplamenteEncadeada<T> proximo;
    private NoListaDuplamenteEncadeada<T> anterior;

    public NoListaDuplamenteEncadeada(T value){
        this.value = value;
        this.proximo = null;
        this.anterior = null;
    }

    public T getValue(){
        return this.value;
    }

    public void setValue(T value){
        this.value = value;
    }

    public NoListaDuplamenteEncadeada<T> getProximo(){
        return this.proximo;
    }

    public void setProximo(NoListaDuplamenteEncadeada<T> proximo){
        this.proximo = proximo;
    }

    public NoListaDuplamenteEncadeada<T> getAnterior() {
        return this.anterior;
    }

    public void setAnterior(NoListaDuplamenteEncadeada<T> anterior) {
        this.anterior = anterior;
    }

    public boolean isNull(){
        return this.value == null;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o){
            return true;
        }

        if(o == null || getClass() != o.getClass()){
            return false;
        }

        NoListaDuplamenteEncadeada<?> that = (NoListaDuplamenteEncadeada<?>) o;

        if(value instanceof CSVRecord){
            if(!(that.getValue() instanceof CSVRecord)){
                return false;
            }

            CSVRecord primeiroRegistro = (CSVRecord) this.value;
            CSVRecord segundoRegistro = (CSVRecord) that.getValue();

            if(primeiroRegistro.size() == 0 || segundoRegistro.size() == 0){
                return false;
            }

            String primeiroIndicePrimeiroRegistro = primeiroRegistro.get(0);
            String primeiroIndiceSegundoRegistro = segundoRegistro.get(0);

            return java.util.Objects.equals(primeiroIndicePrimeiroRegistro, primeiroIndiceSegundoRegistro);

        }

        return java.util.Objects.equals(this.value, that.getValue());
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
