package xadrez;

import estrutura.Peca;
import estrutura.Posicao;
import estrutura.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class Partida { // onde terão as regras

    private Tabuleiro tabuleiro;

    public Partida() {
        tabuleiro = new Tabuleiro(8,8);
        posicaoInicial();
    }

    public PecaDeXadrez[][] getPecas(){ //o programa só deve reconhecer a camada de xadrez, não a de tabuleiro
        PecaDeXadrez[][] matriz = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
        for (int i = 0; i < tabuleiro.getLinhas(); i ++){
            for (int j = 0; j < tabuleiro.getColunas(); j ++){
                matriz[i][j] = (PecaDeXadrez) tabuleiro.peca(i, j); //downcasting
            }
        }
        return matriz;
    }

    public PecaDeXadrez movimentoDaPeca(PosicaoXadrez origem, PosicaoXadrez destino){
        Posicao o = origem.conversaoMatrizParaCasa();
        Posicao d = destino.conversaoMatrizParaCasa();
        vaziaOuNao(o);
        Peca pecaPega = moverPeca(o, d);
        return (PecaDeXadrez) pecaPega;
    }

    private void vaziaOuNao(Posicao posicao){
        if (!tabuleiro.aquiTemPeca(posicao)){
            throw new ExcecaoXadrez("Não há peça na posição " + posicao);
        }
    }

    private Peca moverPeca(Posicao origem, Posicao destino){
        Peca p = tabuleiro.removePeca(origem);
        Peca pecaPega = tabuleiro.removePeca(destino);
        tabuleiro.posicaoDaPeca(p, destino);
        return pecaPega;
    }

    private void casaDaPeca(char coluna, int linha, PecaDeXadrez peca){
        tabuleiro.posicaoDaPeca(peca, new PosicaoXadrez(coluna, linha).conversaoMatrizParaCasa());
    }

    private void posicaoInicial(){
        casaDaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
        casaDaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
        casaDaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO));
        casaDaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
        casaDaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
        casaDaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO));
    }
}
