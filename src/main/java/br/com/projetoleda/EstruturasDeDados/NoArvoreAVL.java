package br.com.projetoleda.EstruturasDeDados;

public class NoArvoreAVL<T> {
    private T value;
    private NoArvoreAVL<T> filhoEsquerdo;
    private NoArvoreAVL<T> filhoDireito;
    private int alturaNo;

    public NoArvoreAVL(T value){
        this.value = value;
        this.alturaNo = 1;
    }

    public T getValue(){
        return this.value;
    }

    public void setValue(T value){
        this.value = value;
    }

    public int getAltura(){
        return this.alturaNo;
    }

    public void setAltura(int altura){
        this.alturaNo = altura;
    }

    public boolean isNull(){
        return this.value == null;
    }

    public NoArvoreAVL<T> getFilhoEsquerdo() {
        return this.filhoEsquerdo;
    }

    public void setFilhoEsquerdo(NoArvoreAVL<T> filhoEsquerdo) {
        this.filhoEsquerdo = filhoEsquerdo;
    }

    public NoArvoreAVL<T> getFilhoDireito() {
        return this.filhoDireito;
    }

    public void setFilhoDireito(NoArvoreAVL<T> filhoDireito) {
        this.filhoDireito = filhoDireito;
    }
}
