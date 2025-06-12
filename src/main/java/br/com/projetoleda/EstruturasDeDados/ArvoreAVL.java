package br.com.projetoleda.EstruturasDeDados;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

public class ArvoreAVL<T> {
    private NoArvoreAVL<T> raiz;
    private int quantidadeDeNos;
    final NoArvoreAVL<T> NILL;
    private final Comparator<T> comparator;

    public ArvoreAVL(Comparator<T> comparator) {
        if (comparator == null) {
            throw new IllegalArgumentException("O Comparator não pode ser nulo.");
        }
        this.comparator = comparator;
        this.quantidadeDeNos = 0;

        // Inicializa o NILL uma única vez e configura sua altura.
        this.NILL = new NoArvoreAVL<>(null);
        this.NILL.setAltura(0);
        this.raiz = NILL;
    }
    public void inserir(T value){
        this.raiz = inserirRecursivo(this.raiz, value);
    }

    private NoArvoreAVL<T> inserirRecursivo(NoArvoreAVL<T> no, T value) {
        // 1. Caso base: Chegou em um nó NILL, local certo para inserir.
        if (no.isNull()) {
            // <<< CORREÇÃO: Cria o nó e incrementa o contador apenas aqui.
            this.quantidadeDeNos++;
            NoArvoreAVL<T> newNode = new NoArvoreAVL<>(value);
            newNode.setFilhoEsquerdo(NILL);
            newNode.setFilhoDireito(NILL);
            return newNode;
        }

        // 2. Comparação e chamada recursiva para descer na árvore.
        int comparacao = this.comparator.compare(value, no.getValue());

        // <<< CORREÇÃO: Chamadas recursivas para realmente descer na árvore.
        if (comparacao < 0) {
            no.setFilhoEsquerdo(inserirRecursivo(no.getFilhoEsquerdo(), value));
        } else if (comparacao > 0) {
            no.setFilhoDireito(inserirRecursivo(no.getFilhoDireito(), value));
        } else {
            // Valor duplicado, não faz nada. O contador não é incrementado.
            return no;
        }

        // 3. Atualiza a altura do nó atual.
        no.setAltura(1 + Math.max(altura(no.getFilhoEsquerdo()), altura(no.getFilhoDireito())));

        // 4. Calcula o fator de balanceamento e rotaciona se necessário.
        int fatorBalanceamento = getFatorbalanceamentoNo(no);

        // Caso Esquerda-Esquerda
        if (fatorBalanceamento > 1 && this.comparator.compare(value, no.getFilhoEsquerdo().getValue()) < 0) {
            return rotacaoDireita(no);
        }

        // Caso Direita-Direita
        // <<< CORREÇÃO: Checando o filho da DIREITA.
        if (fatorBalanceamento < -1 && this.comparator.compare(value, no.getFilhoDireito().getValue()) > 0) {
            return rotacaoEsquerda(no);
        }

        // Caso Esquerda-Direita
        if (fatorBalanceamento > 1 && this.comparator.compare(value, no.getFilhoEsquerdo().getValue()) > 0) {
            no.setFilhoEsquerdo(rotacaoEsquerda(no.getFilhoEsquerdo()));
            return rotacaoDireita(no);
        }

        // Caso Direita-Esquerda
        // <<< CORREÇÃO: Checando o filho da DIREITA.
        if (fatorBalanceamento < -1 && this.comparator.compare(value, no.getFilhoDireito().getValue()) < 0) {
            no.setFilhoDireito(rotacaoDireita(no.getFilhoDireito()));
            return rotacaoEsquerda(no);
        }

        return no;
    }

    public int max(int primeiroNumero, int segundoNumero){
        if(primeiroNumero > segundoNumero){
            return primeiroNumero;
        }
        return segundoNumero;
    }

    public int getFatorbalanceamentoNo(NoArvoreAVL<T> no){
        if(no.isNull()){
            return 0;
        }

        return altura(no.getFilhoEsquerdo()) - altura(no.getFilhoDireito());
    }

    private NoArvoreAVL<T> rotacaoDireita(NoArvoreAVL<T> y) {
        NoArvoreAVL<T> x = y.getFilhoEsquerdo();
        NoArvoreAVL<T> T2 = x.getFilhoDireito();

        x.setFilhoDireito(y);
        y.setFilhoEsquerdo(T2);

        y.setAltura(max(altura(y.getFilhoEsquerdo()), altura(y.getFilhoDireito())) + 1);
        x.setAltura(max(altura(x.getFilhoEsquerdo()), altura(x.getFilhoDireito())) + 1);

        return x;
    }

    private NoArvoreAVL<T> rotacaoEsquerda(NoArvoreAVL<T> x) {
        NoArvoreAVL<T> y = x.getFilhoDireito();
        NoArvoreAVL<T> T2 = y.getFilhoEsquerdo();

        y.setFilhoEsquerdo(x);
        x.setFilhoDireito(T2);

        x.setAltura(max(altura(x.getFilhoEsquerdo()), altura(x.getFilhoDireito())) + 1);
        y.setAltura(max(altura(y.getFilhoEsquerdo()), altura(y.getFilhoDireito())) + 1);

        return y;
    }

    public int altura(NoArvoreAVL<T> no){
        if(no.isNull()){
            return 0;
        }
        return no.getAltura();
    }

    @SuppressWarnings("unchecked")
    public T[] toArray(Class<T> c) {

        T[] array = (T[]) Array.newInstance(c, this.quantidadeDeNos);

        AtomicInteger indiceAtual = new AtomicInteger(0);

        preencherArrayPreOrdem(this.raiz, array, indiceAtual);

        return array;
    }

    private void preencherArrayPreOrdem(NoArvoreAVL<T> no, T[] array, AtomicInteger indice) {
        // Condição de parada (usando seu padrão de nó NILL).
        if (no.isNull()) {
            return;
        }

        // <<< MUDANÇA: A ORDEM DAS 3 OPERAÇÕES ABAIXO É O QUE DEFINE O PERCURSO >>>

        // 1. Processa a RAIZ primeiro.
        if (indice.get() < this.quantidadeDeNos) {
            array[indice.getAndIncrement()] = no.getValue();
        }

        // 2. Visita a subárvore ESQUERDA.
        preencherArrayPreOrdem(no.getFilhoEsquerdo(), array, indice);

        // 3. Visita a subárvore DIREITA.
        preencherArrayPreOrdem(no.getFilhoDireito(), array, indice);
    }
}
