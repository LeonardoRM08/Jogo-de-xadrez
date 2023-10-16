package xadrez;

import estrutura.Peca;
import estrutura.Posicao;
import estrutura.Tabuleiro;

public abstract class PecaDeXadrez extends Peca{

    private Cor cor;
    private int contadorDeMovimento; //inicialmente = 0

    public PecaDeXadrez(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro);
        this.cor = cor;
    }

    public Cor getCor() {
        return cor;
    }

    public void incrementaContadorDeMovimento (){
        contadorDeMovimento ++;
    }

    public void decrementaContadorDeMovimento (){
        contadorDeMovimento --;
    }

    public int getContadorDeMovimento() {
        return contadorDeMovimento;
    }

    public PosicaoXadrez getPosicaoXadrez(){
        return PosicaoXadrez.conversaoCasaParaMatriz(posicao);
    }

    protected boolean aquiTemPecaAdversaria (Posicao posicao){ //está nessa classe por que vai ser reaproveitada em várias outras
        PecaDeXadrez peca = (PecaDeXadrez) getTabuleiro().peca(posicao); // pega a peça na posicao
        return peca != null && peca.getCor() != cor;
    }

}
