package xadrez.pecas;

import estrutura.Posicao;
import estrutura.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Peao extends PecaDeXadrez {

    public Peao(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()]; //inicialmente tudo falso
        Posicao auxiliar = new Posicao(0, 0);

        if (getCor() == Cor.BRANCO){
            //acima
            auxiliar.setValores(posicao.getLinha() - 1, posicao.getColuna());

            if (getTabuleiro().posicaoExistente(auxiliar) && !getTabuleiro().aquiTemPeca(auxiliar)){ //se a posição existir e não tiver ocupada...
                matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true; //movimento validado
            }

            auxiliar.setValores(posicao.getLinha() - 2, posicao.getColuna()); //para seu primeiro movimento
            Posicao auxiliar2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());//se houver uma peça no meio do caminho, o peão não pode se mexer para frente

            if (getTabuleiro().posicaoExistente(auxiliar) && !getTabuleiro().aquiTemPeca(auxiliar)
                && getTabuleiro().posicaoExistente(auxiliar2) && !getTabuleiro().aquiTemPeca(auxiliar2)
                && getContadorDeMovimento() == 0){
                matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true; //movimento validado
        }
            //canto superior esquerdo (quando captura uma peça)
            auxiliar.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
            if (getTabuleiro().posicaoExistente(auxiliar) && aquiTemPecaAdversaria(auxiliar)){ //se a posição existir e tiver ocupada por uma peça adversária...
                matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true; //movimento validado
            }

            //canto superior direito (quando captura uma peça)
            auxiliar.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
            if (getTabuleiro().posicaoExistente(auxiliar) && aquiTemPecaAdversaria(auxiliar)){ //se a posição existir e tiver ocupada por uma peça adversária...
                matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true; //movimento validado
            }
    }
        else { //se for preta
            //acima
            auxiliar.setValores(posicao.getLinha() + 1, posicao.getColuna());

            if (getTabuleiro().posicaoExistente(auxiliar) && !getTabuleiro().aquiTemPeca(auxiliar)){ //se a posição existir e não tiver ocupada...
                matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true; //movimento validado
            }

            auxiliar.setValores(posicao.getLinha() + 2, posicao.getColuna()); //para seu primeiro movimento
            Posicao auxiliar2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());//se houver uma peça no meio do caminho, o peão não pode se mexer para frente

            if (getTabuleiro().posicaoExistente(auxiliar) && !getTabuleiro().aquiTemPeca(auxiliar)
                    && getTabuleiro().posicaoExistente(auxiliar2) && !getTabuleiro().aquiTemPeca(auxiliar2)
                    && getContadorDeMovimento() == 0){
                matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true; //movimento validado
            }
            //canto superior esquerdo (quando captura uma peça)
            auxiliar.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
            if (getTabuleiro().posicaoExistente(auxiliar) && aquiTemPecaAdversaria(auxiliar)){ //se a posição existir e tiver ocupada por uma peça adversária...
                matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true; //movimento validado
            }

            //canto superior direito (quando captura uma peça)
            auxiliar.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
            if (getTabuleiro().posicaoExistente(auxiliar) && aquiTemPecaAdversaria(auxiliar)){ //se a posição existir e tiver ocupada por uma peça adversária...
                matriz[auxiliar.getLinha()][auxiliar.getColuna()] = true; //movimento validado
            }
        }
        return matriz;
    }

    @Override
    public String toString() {
        return "P";
    }

}
