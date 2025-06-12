package br.com.projetoleda.EstruturasDeDados;

import java.lang.reflect.Array;

public class ListaDuplamenteEncadeada<T> {

    private NoListaDuplamenteEncadeada<T> cabeca;
    private NoListaDuplamenteEncadeada<T> cauda;
    final NoListaDuplamenteEncadeada<T> NILL = new NoListaDuplamenteEncadeada<>(null);
    private int quantidadeDeElementos;

    public ListaDuplamenteEncadeada(){
        this.cabeca = NILL;
        this.cauda = NILL;
        this.quantidadeDeElementos = 0;
    }

    public void insert(T elemento){
        NoListaDuplamenteEncadeada<T> newNode = new NoListaDuplamenteEncadeada<>(elemento);

        if(isEmpty()){
            this.cabeca = newNode;
            this.cabeca.setAnterior(NILL);
            this.cauda = newNode;
            this.cauda.setProximo(NILL);
            this.quantidadeDeElementos++;
            return;
        }

        this.cauda.setProximo(newNode);
        newNode.setAnterior(this.cauda);
        newNode.setProximo(NILL);
        this.cauda = newNode;
        this.quantidadeDeElementos++;
    }

    public T remove(T elemento){
        if(this.cabeca.isNull()){
            return null;
        }

        if(this.cabeca.getValue().equals(elemento)){
            if(this.cabeca.getProximo() == NILL){
                this.cabeca = NILL;
                this.cauda = NILL;
            }
            else{
                this.cabeca.getProximo().setAnterior(NILL);
                this.cabeca = this.cabeca.getProximo();
            }
            this.quantidadeDeElementos--;
            return elemento;
        }

        if(this.cauda.getValue().equals(elemento)){
            this.cauda.getAnterior().setProximo(NILL);
            this.cauda = this.cauda.getAnterior();

            this.quantidadeDeElementos--;
            return elemento;
        }

        NoListaDuplamenteEncadeada<T> current = this.cabeca.getProximo();
        NoListaDuplamenteEncadeada<T> previous = this.cabeca;
        while(!current.isNull()){
            if(current.getValue().equals(elemento)){
                previous.setProximo(current.getProximo());
                current.getProximo().setAnterior(previous);

                this.quantidadeDeElementos--;
                return elemento;
            }
            previous = current;
            current = current.getProximo();
        }

        return null;
    }

    public T get(T elemento){
        if(isEmpty()){
            return null;
        }

        NoListaDuplamenteEncadeada<T> noAtual = this.cabeca;

        while(!noAtual.getProximo().isNull()){
            if(noAtual.getValue().equals(elemento)){
                return elemento;
            }

            noAtual = noAtual.getProximo();
        }

        return null;
    }

    public boolean isEmpty(){
        return this.cabeca.isNull();
    }

    public int size(){
        return this.quantidadeDeElementos;
    }

    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] arrayParametro){

        if(arrayParametro.length <  this.quantidadeDeElementos){
            arrayParametro = (T[]) Array.newInstance(arrayParametro.getClass().getComponentType(), this.quantidadeDeElementos);
        }

        NoListaDuplamenteEncadeada<T> noAtual = (NoListaDuplamenteEncadeada<T>) this.cabeca;
        Object[] arrayRetorno = arrayParametro;
        int indice = 0;

        while(!noAtual.isNull()){
            arrayRetorno[indice] = noAtual.getValue();
            indice++;
            noAtual = noAtual.getProximo();
        }

        if (arrayParametro.length > this.quantidadeDeElementos) {
            arrayParametro[this.quantidadeDeElementos] = null;
        }

        return arrayParametro;
    }
}
