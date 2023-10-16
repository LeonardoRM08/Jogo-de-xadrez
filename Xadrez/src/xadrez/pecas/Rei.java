package xadrez.pecas;

import estrutura.Posicao;
import estrutura.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Rei extends PecaDeXadrez {

    public Rei(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    private boolean podeMover (Posicao posicao){
        PecaDeXadrez peca = (PecaDeXadrez)getTabuleiro().peca(posicao);
        return peca == null || peca.getCor() != getCor();
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao auxiliar = new Posicao(0, 0);

        //acima
        auxiliar.setValores(posicao.getLinha() - 1, posicao.getColuna());
        if (getTabuleiro().posicaoExistente(auxiliar) && podeMover(auxiliar)){
            matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true;
        }

        //abaixo
        auxiliar.setValores(posicao.getLinha() + 1, posicao.getColuna());
        if (getTabuleiro().posicaoExistente(auxiliar) && podeMover(auxiliar)){
            matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true;
        }
        //à esquerda
        auxiliar.setValores(posicao.getLinha(), posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExistente(auxiliar) && podeMover(auxiliar)){
            matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true;
        }
        //à direita
        auxiliar.setValores(posicao.getLinha(), posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExistente(auxiliar) && podeMover(auxiliar)){
            matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true;
        }

        //canto esquerdo superior
        auxiliar.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExistente(auxiliar) && podeMover(auxiliar)){
            matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true;
        }

        //canto esquerdo inferior
        auxiliar.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExistente(auxiliar) && podeMover(auxiliar)){
            matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true;
        }

        //canto direito superior
        auxiliar.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExistente(auxiliar) && podeMover(auxiliar)){
            matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true;
        }

        //canto direito inferior
        auxiliar.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExistente(auxiliar) && podeMover(auxiliar)){
            matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true;
        }

        return matriz;
    }

    @Override
    public String toString() {
        return "K";
    }

}
