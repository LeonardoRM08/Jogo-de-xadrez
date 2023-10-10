package xadrez;

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

    private void posicaoInicial(){
        tabuleiro.posicaoDaPeca(new Torre(tabuleiro, Cor.BRANCO), new Posicao(0, 0));
        tabuleiro.posicaoDaPeca(new Torre(tabuleiro, Cor.BRANCO), new Posicao(0, 7));
        tabuleiro.posicaoDaPeca(new Torre(tabuleiro, Cor.PRETO),  new Posicao(7, 0));
        tabuleiro.posicaoDaPeca(new Torre(tabuleiro, Cor.PRETO),  new Posicao(7, 7));
        tabuleiro.posicaoDaPeca(new Rei(tabuleiro, Cor.BRANCO), new Posicao(0, 4));
        tabuleiro.posicaoDaPeca(new Rei(tabuleiro, Cor.BRANCO), new Posicao(7, 4));
    }
}
