package estrutura;

public abstract class Peca {
    protected Posicao posicao; // a posicao não pode ser visível para o programa principal
    private Tabuleiro tabuleiro;

    public Peca(Tabuleiro tabuleiro) { // inicialmente, é como se não houvesse peças no jogo, então a posição começa = null
        this.tabuleiro = tabuleiro;
        posicao = null;
    }

    protected Tabuleiro getTabuleiro() { //de uso interno das classes do pacote jogo-
        return tabuleiro;
    }

//    public void setTabuleiro(Tabuleiro tabuleiro) { // o tabuleiro não pode ser alterado
//        this.tabuleiro = tabuleiro;
//    }

    public abstract boolean[][] movimentosPossiveis();

    public boolean movimentoPossivel (Posicao posicao){
        return movimentosPossiveis()[posicao.getLinha()][posicao.getColuna()]; // hook method
    }

    public boolean pecaTravadaOuNao(){
        boolean[][] matriz = movimentosPossiveis();
        for (int i = 0; i < matriz.length; i ++){
            for (int j = 0; j < matriz.length; j ++){
                if (matriz[i][j]){
                    return true;
                }
            }
        }
        return false;
    }
}
