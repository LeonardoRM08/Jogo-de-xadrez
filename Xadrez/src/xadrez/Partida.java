package xadrez;

import estrutura.Peca;
import estrutura.Posicao;
import estrutura.Tabuleiro;
import xadrez.pecas.*;

import java.util.ArrayList;
import java.util.List;

public class Partida { // onde terão as regras

    private int turno;
    private Cor jogador;
    private Tabuleiro tabuleiro;
    private boolean check; //sempre começa como falso
    private boolean checkMate;

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

    public Cor getJogador() {
        return jogador;
    }

    public boolean getCheck(){
        return check;
    }

    public boolean getCheckMate(){
        return checkMate;
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

        if (testeDeCheck(jogador)){
            desfazerMovimento(o, d, pecaPega);
            throw new ExcecaoXadrez("O movimento colocaria o seu próprio rei em check");
        }

        check = testeDeCheck(oponente(jogador)) ? true : false; // se o adversário ficou em check depois do movimento...

        if (testeDeCheckMate(oponente(jogador))){
            checkMate = true;
        }
        else{
            novoTurno();
        }
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
        PecaDeXadrez p = (PecaDeXadrez)tabuleiro.removePeca(origem);
        p.incrementaContadorDeMovimento();
        Peca pecaPega = tabuleiro.removePeca(destino);
        tabuleiro.posicaoDaPeca(p, destino);

        if (pecaPega != null){
            pecasNoTabuleiro.remove(pecaPega);
            pecasPegas.add(pecaPega);
        }

        if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) { //roque pequeno
            Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
            Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
            PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removePeca(origemTorre);
            tabuleiro.posicaoDaPeca(torre, destinoTorre);
            torre.incrementaContadorDeMovimento();
        }

        if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) { //roque grande
            Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
            Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);
            PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removePeca(origemTorre);
            tabuleiro.posicaoDaPeca(torre, destinoTorre);
            torre.incrementaContadorDeMovimento();
        }
        return pecaPega;
    }

    private void desfazerMovimento (Posicao origem, Posicao destino, Peca pecaPega){ //não permite que o jogador se coloque em check
        PecaDeXadrez p = (PecaDeXadrez)tabuleiro.removePeca(destino); //peça tirada da posição pra qual foi movida
        p.decrementaContadorDeMovimento();
        tabuleiro.posicaoDaPeca(p, origem);

        if (pecaPega != null){ //se o movimento errado comeu uma peça...
            tabuleiro.posicaoDaPeca(pecaPega, destino);
            pecasPegas.remove(pecaPega);
            pecasNoTabuleiro.add(pecaPega);
        }

        if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) { //roque pequeno
            Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
            Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
            PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removePeca(destinoTorre); //inverte em relação ao movimento
            tabuleiro.posicaoDaPeca(torre, origemTorre);
            torre.decrementaContadorDeMovimento();
        }

        if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) { //roque grande
            Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
            Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);
            PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removePeca(destinoTorre);
            tabuleiro.posicaoDaPeca(torre, origemTorre);
            torre.decrementaContadorDeMovimento();
        }
    }

    private void novoTurno (){
        turno++;
        jogador = (jogador == Cor.BRANCO)? Cor.PRETO : Cor.BRANCO; //condicional ternaria
    }

    private Cor oponente (Cor cor){
        return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
    }

    private PecaDeXadrez rei (Cor cor){
        List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == cor).toList(); //lambda
        for (Peca p :lista) {
            if (p instanceof Rei){ //herança
                return (PecaDeXadrez) p;
            }
        }
        throw new IllegalStateException("Não existe rei de cor" + cor + "no tabuleiro"); //não é pra isso acontecer
    }

    private boolean testeDeCheck (Cor cor){
        Posicao posicaoDoRei = rei(cor).getPosicaoXadrez().conversaoMatrizParaCasa();
        List<Peca> pecasAdversarias = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == oponente(cor)).toList();
        for (Peca p : pecasAdversarias) {
            boolean[][] movimentosPossiveisDaPeca = p.movimentosPossiveis(); //válido para todas as peças do xadrez
            if (movimentosPossiveisDaPeca[posicaoDoRei.getLinha()][posicaoDoRei.getColuna()]){// se verdadeiro, o rei está em cheque
                return true;
            }
        }
        return false; //o rei não está em perigo
    }

    private boolean testeDeCheckMate (Cor cor){
        if (!testeDeCheck(cor)){ //é impossível ter check mate sem check primeiro
            return false;
        }
        List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == cor).toList();
        for (Peca p : lista){
            boolean[][] movimentosPossiveisDaPeca = p.movimentosPossiveis(); //válido para todas as peças do xadrez
            for (int i = 0; i < tabuleiro.getLinhas(); i ++){
                for (int j = 0; j < tabuleiro.getColunas(); j ++){
                    if (movimentosPossiveisDaPeca[i][j]){ //esse movimento possível tira o rei do cheque?
                        Posicao origem = ((PecaDeXadrez)p).getPosicaoXadrez().conversaoMatrizParaCasa();
                        Posicao destino = new Posicao(i, j);
                        Peca pecaPega = moverPeca(origem, destino);
                        boolean testeDeCheck = testeDeCheck(cor); //o rei continua em check?
                        desfazerMovimento(origem, destino, pecaPega); //precisa desfazer por ser apenas um teste
                        if (!testeDeCheck){ // se o movimento tirar o rei do check...
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }


    private void casaDaPeca(char coluna, int linha, PecaDeXadrez peca){
        tabuleiro.posicaoDaPeca(peca, new PosicaoXadrez(coluna, linha).conversaoMatrizParaCasa());
        pecasNoTabuleiro.add(peca);
    }

    private void posicaoInicial(){
        //Torres
        casaDaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
        casaDaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));

        casaDaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
        casaDaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));

        //Cavalos
        casaDaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
        casaDaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));

        casaDaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
        casaDaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETO));

        //Bispos
        casaDaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
        casaDaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));

        casaDaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETO));
        casaDaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETO));

        //Reis
        casaDaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));

        casaDaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO, this));

        //Rainhas

        casaDaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCO));

        casaDaPeca('d', 8, new Rainha(tabuleiro, Cor.PRETO));

        //Peões
        casaDaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO));
        casaDaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO));
        casaDaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO));
        casaDaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO));
        casaDaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO));
        casaDaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO));
        casaDaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO));
        casaDaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO));

        casaDaPeca('a', 7, new Peao(tabuleiro, Cor.PRETO));
        casaDaPeca('b', 7, new Peao(tabuleiro, Cor.PRETO));
        casaDaPeca('c', 7, new Peao(tabuleiro, Cor.PRETO));
        casaDaPeca('d', 7, new Peao(tabuleiro, Cor.PRETO));
        casaDaPeca('e', 7, new Peao(tabuleiro, Cor.PRETO));
        casaDaPeca('f', 7, new Peao(tabuleiro, Cor.PRETO));
        casaDaPeca('g', 7, new Peao(tabuleiro, Cor.PRETO));
        casaDaPeca('h', 7, new Peao(tabuleiro, Cor.PRETO));
    }
}
