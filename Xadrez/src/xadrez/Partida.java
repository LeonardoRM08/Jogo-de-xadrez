package xadrez;

import estrutura.Peca;
import estrutura.Posicao;
import estrutura.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

import java.util.ArrayList;
import java.util.List;

public class Partida { // onde terão as regras

    private int turno;
    private Cor jogador;
    private Tabuleiro tabuleiro;

    private List<Peca> pecasNoTabuleiro = new ArrayList<>();
    private List<Peca> pecasPegas = new ArrayList<>();

    public Partida() {
        tabuleiro = new Tabuleiro(8,8);
        turno = 1;
        jogador = Cor.BRANCO;
        posicaoInicial();
    }

    public int getTurno(){
        return turno;
    }

    public Cor getJogador(){
        return jogador;
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

    public boolean[][] movimentosPossiveis (PosicaoXadrez origem){
        Posicao posicao =  origem.conversaoMatrizParaCasa();
        podeOuNaoMexer(posicao);
        return tabuleiro.peca(posicao).movimentosPossiveis();
    }

    public PecaDeXadrez movimentoDaPeca(PosicaoXadrez origem, PosicaoXadrez destino){
        Posicao o = origem.conversaoMatrizParaCasa();
        Posicao d = destino.conversaoMatrizParaCasa();
        podeOuNaoMexer(o);
        destinoCertoOuNao(o, d);
        Peca pecaPega = moverPeca(o, d);
        novoTurno();
        return (PecaDeXadrez) pecaPega;
    }

    private void podeOuNaoMexer(Posicao posicao){
        if (!tabuleiro.aquiTemPeca(posicao)){ //! nega a condição
            throw new ExcecaoXadrez("Não há peça na posição informada");
        }
        if (jogador != ((PecaDeXadrez)tabuleiro.peca(posicao)).getCor()){
            throw new ExcecaoXadrez("Nesse turno, deve ser escolhida a peça " + jogador);
        }

        if (!tabuleiro.peca(posicao).pecaTravadaOuNao()){
            throw new ExcecaoXadrez("A peça não pode fazer esse movimento"); //PosicaoXadrez.conversaoCasaParaMatriz(posicao)
        }
    }

    private void destinoCertoOuNao(Posicao origem, Posicao destino){
        if (!tabuleiro.peca(origem).movimentoPossivel(destino)){
            throw new ExcecaoXadrez("A peça não pode ir até esse destino");
        }
    }

    private Peca moverPeca(Posicao origem, Posicao destino){
        Peca p = tabuleiro.removePeca(origem);
        Peca pecaPega = tabuleiro.removePeca(destino);
        tabuleiro.posicaoDaPeca(p, destino);

        if (pecaPega != null){
            pecasNoTabuleiro.remove(pecaPega);
            pecasPegas.add(pecaPega);
        }
        return pecaPega;
    }

    private void novoTurno (){
        turno++;
        jogador = (jogador == Cor.BRANCO)? Cor.PRETO : Cor.BRANCO; //condicional ternaria
    }

    private void casaDaPeca(char coluna, int linha, PecaDeXadrez peca){
        tabuleiro.posicaoDaPeca(peca, new PosicaoXadrez(coluna, linha).conversaoMatrizParaCasa());
        pecasNoTabuleiro.add(peca);
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
