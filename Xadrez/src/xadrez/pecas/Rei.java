package xadrez.pecas;

import estrutura.Posicao;
import estrutura.Tabuleiro;
import xadrez.Cor;
import xadrez.Partida;
import xadrez.PecaDeXadrez;

public class Rei extends PecaDeXadrez {

    private Partida partida; // para realizar o roque, o rei precisa de informações das torres

    public Rei(Tabuleiro tabuleiro, Cor cor, Partida partida) {
        super(tabuleiro, cor);
        this.partida = partida;
    }

    private boolean podeMover (Posicao posicao){
        PecaDeXadrez peca = (PecaDeXadrez)getTabuleiro().peca(posicao);
        return peca == null || peca.getCor() != getCor();
    }

    private boolean testeRook(Posicao posicao){
        PecaDeXadrez p = (PecaDeXadrez) getTabuleiro().peca(posicao);
        return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContadorDeMovimento() == 0;
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

        //roque
        if (getContadorDeMovimento() == 0 && !partida.getCheck()) {
            //roque do lado do rei (roque pequeno)
            Posicao posicaoTorre1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
            if (testeRook(posicaoTorre1)) {
                Posicao casaDireitaDoRei = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                Posicao casaEsquerdaDaTorre = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
                if (getTabuleiro().peca(casaDireitaDoRei) == null && getTabuleiro().peca(casaEsquerdaDaTorre) == null){
                    matriz[posicao.getLinha()][posicao.getColuna() + 2] = true;
                }
            }

            //roque do lado da rainha (roque grande)
            Posicao posicaoTorre2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
            if (testeRook(posicaoTorre2)) {
                Posicao casaEsquerdaDoRei = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                Posicao casaDoMeio = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
                Posicao casaDireitaDaTorre = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
                if (getTabuleiro().peca(casaEsquerdaDoRei) == null
                        && getTabuleiro().peca(casaDoMeio) == null
                        && getTabuleiro().peca(casaDireitaDaTorre) == null){
                    matriz[posicao.getLinha()][posicao.getColuna() - 2] = true;
                }
            }
        }
        return matriz;
    }

    @Override
    public String toString() {
        return "R";
    }

}
