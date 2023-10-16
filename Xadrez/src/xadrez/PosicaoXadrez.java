package xadrez;

import estrutura.Posicao;

public class PosicaoXadrez {
    private char coluna;
    private int linha;

    public PosicaoXadrez(char coluna, int linha) {
        if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8){
            throw new ExcecaoXadrez("São aceitas as casas de a1 até h8");
        }
        this.coluna = coluna;
        this.linha = linha;
    }

    public char getColuna() {
        return coluna;
    }

    public int getLinha() {
        return linha;
    }

    protected Posicao conversaoMatrizParaCasa(){
        return new Posicao(8 - linha, coluna - 'a');//8 - 8 = 0; 8 - 7 = 1...'a' - 'a' = 0; 'b' - 'a' = 1...
    }
    protected static PosicaoXadrez conversaoCasaParaMatriz(Posicao posicao){
        return new PosicaoXadrez((char)('a' + posicao.getColuna()), 8 - posicao.getLinha());
    }

    @Override
    public String toString() {
        return "" + coluna + linha; //"" força o compilador a entender que isso é uma concatenação de strings
    }
}
