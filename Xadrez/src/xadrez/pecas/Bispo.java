package xadrez.pecas;

import estrutura.Posicao;
import estrutura.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Bispo extends PecaDeXadrez {

    public Bispo(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()]; //inicialmente tudo falso
        Posicao auxiliar = new Posicao(0, 0);

        //diagonal superior esquerda
        auxiliar.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
        while (getTabuleiro().posicaoExistente(auxiliar) && !getTabuleiro().aquiTemPeca(auxiliar)){// enquanto a casa existir e não houver peça lá
            matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true;
            auxiliar.setLinha(auxiliar.getLinha() - 1);
            auxiliar.setColuna(auxiliar.getColuna() - 1);

        }
        if (getTabuleiro().posicaoExistente(auxiliar) && aquiTemPecaAdversaria(auxiliar)){
            matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true;
        }

        //diagonal superior direita
        auxiliar.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
        while (getTabuleiro().posicaoExistente(auxiliar) && !getTabuleiro().aquiTemPeca(auxiliar)){
            matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true;
            auxiliar.setLinha(auxiliar.getLinha() - 1);
            auxiliar.setColuna(auxiliar.getColuna() + 1);
        }
        if (getTabuleiro().posicaoExistente(auxiliar) && aquiTemPecaAdversaria(auxiliar)){
            matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true;
        }

        //diagonal inferior esquerda
        auxiliar.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
        while (getTabuleiro().posicaoExistente(auxiliar) && !getTabuleiro().aquiTemPeca(auxiliar)){
            matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true;
            auxiliar.setLinha(auxiliar.getLinha() + 1);
            auxiliar.setColuna(auxiliar.getColuna() - 1);
        }
        if (getTabuleiro().posicaoExistente(auxiliar) && aquiTemPecaAdversaria(auxiliar)){
            matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true;
        }

        //diagonal inferior direita
        auxiliar.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
        while (getTabuleiro().posicaoExistente(auxiliar) && !getTabuleiro().aquiTemPeca(auxiliar)){
            matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true;
            auxiliar.setLinha(auxiliar.getLinha() + 1);
            auxiliar.setColuna(auxiliar.getColuna() + 1);
        }
        if (getTabuleiro().posicaoExistente(auxiliar) && aquiTemPecaAdversaria(auxiliar)){
            matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true;
        }

        return matriz;
    }

    @Override
    public String toString() {
        return "B";
    }
}
