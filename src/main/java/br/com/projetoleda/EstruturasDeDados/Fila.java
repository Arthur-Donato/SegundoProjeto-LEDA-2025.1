package br.com.projetoleda.EstruturasDeDados;

import org.apache.commons.csv.CSVRecord;

public class Fila<T> {
    private final T[] array;
    private int cabeca;
    private int cauda;
    private int quantidadeDeElementos;


    @SuppressWarnings("unchecked")
    public Fila(int tamanho){
        this.array = (T[]) new Object[tamanho];
        this.cabeca = 0;
        this.cauda = 0;
        this.quantidadeDeElementos = 0;
    }

    public void enfileirar(T element){
        if(this.isFull()){
            return;
        }

        this.array[this.cauda] = element;
        this.cauda = (this.cauda + 1) % this.array.length;
        this.quantidadeDeElementos++;
    }

    public T desenfileirar(){
        if(this.isEmpty()){
            return null;
        }
        else{
            T elementoRemovido = this.array[this.cabeca];
            this.array[this.cabeca] = null;

            this.cabeca = (this.cabeca + 1) % this.array.length;


            return elementoRemovido;
        }
    }

    public T head(){
        if(isEmpty()){
            return null;
        }
        return this.array[cabeca];
    }

    public T tail(){
        if(isEmpty()){
            return null;
        }
        return this.array[cauda - 1];
    }

    public boolean isEmpty(){
        return this.quantidadeDeElementos == 0;
    }

    public boolean isFull(){
        return this.quantidadeDeElementos == this.array.length;
    }

    public int getCauda() {
        return this.cauda;
    }

    public int getQuantidadeDeElementos(){
        return this.quantidadeDeElementos;
    }

    public void setCauda(int cauda) {
        this.cauda = cauda;
    }
}
