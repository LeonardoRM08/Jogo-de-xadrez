package xadrez.pecas;

import estrutura.Posicao;
import estrutura.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Torre extends PecaDeXadrez {

    public Torre(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString() {
        return "T";
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()]; //inicialmente tudo falso
        Posicao auxiliar = new Posicao(0, 0);

        //acima da peça
        auxiliar.setValores(posicao.getLinha() - 1, posicao.getColuna()); // anda livremente na mesma coluna
        while (getTabuleiro().posicaoExistente(auxiliar) && !getTabuleiro().aquiTemPeca(auxiliar)){// enquanto aux existir e não houver peça lá
            matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true;
            auxiliar.setLinha(auxiliar.getLinha() - 1); // atualiza o valor da linha
        }
        if (getTabuleiro().posicaoExistente(auxiliar) && aquiTemPecaAdversaria(auxiliar)){
            matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true;
        }

        //abaixo da peça
        auxiliar.setValores(posicao.getLinha() + 1, posicao.getColuna()); // anda livremente na mesma coluna
        while (getTabuleiro().posicaoExistente(auxiliar) && !getTabuleiro().aquiTemPeca(auxiliar)){
            matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true;
            auxiliar.setLinha(auxiliar.getLinha() + 1);
        }
        if (getTabuleiro().posicaoExistente(auxiliar) && aquiTemPecaAdversaria(auxiliar)){
            matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true;
        }

        //à esquerda
        auxiliar.setValores(posicao.getLinha(), posicao.getColuna() - 1); // anda livremente na mesma coluna
        while (getTabuleiro().posicaoExistente(auxiliar) && !getTabuleiro().aquiTemPeca(auxiliar)){
            matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true;
            auxiliar.setColuna(auxiliar.getColuna() - 1);
        }
        if (getTabuleiro().posicaoExistente(auxiliar) && aquiTemPecaAdversaria(auxiliar)){
            matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true;
        }

        //à direita
        auxiliar.setValores(posicao.getLinha(), posicao.getColuna() + 1); // anda livremente na mesma coluna
        while (getTabuleiro().posicaoExistente(auxiliar) && !getTabuleiro().aquiTemPeca(auxiliar)){
            matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true;
            auxiliar.setColuna(auxiliar.getColuna() + 1);
        }
        if (getTabuleiro().posicaoExistente(auxiliar) && aquiTemPecaAdversaria(auxiliar)){
            matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true;
        }

        return matriz;
    }
}
