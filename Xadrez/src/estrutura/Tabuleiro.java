package estrutura;

public class Tabuleiro {
    private int linhas;
    private int colunas;
    private Peca[][] pecas;

    public Tabuleiro(int linhas, int colunas) {
        if (linhas < 1 || colunas <1) {
            throw new ExcecaoTabuleiro("Não foi possível criar o tabuleiro, deve haver ao menos 1 linha e 1 coluna");
        }
        this.linhas = linhas;
        this.colunas = colunas;
        pecas = new Peca[linhas][colunas];
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public Peca peca(int linha, int coluna) {
        if(!posicaoExistente(linha, coluna)){
            throw new ExcecaoTabuleiro("Essa posição não existe");
        }
        return pecas[linha][coluna];
    }

    public Peca peca(Posicao posicao){ //sobrecarga
        if(!posicaoExistente(posicao)){
            throw new ExcecaoTabuleiro("Essa posição não existe");
        }
        return pecas[posicao.getLinha()][posicao.getColuna()];
    }

    public void posicaoDaPeca (Peca peca, Posicao posicao){
        if(aquiTemPeca(posicao)){
            throw new ExcecaoTabuleiro("A posição " + posicao + " já está ocupada");
        }
        pecas[posicao.getLinha()][posicao.getColuna()] = peca;
        peca.posicao = posicao; // é acessa pelo modificador protected
    }

    public Peca removePeca (Posicao posicao){
        if(!posicaoExistente(posicao)){
            throw new ExcecaoTabuleiro("Essa posição não existe");
        }
        if (peca(posicao) == null){
            return null;
        }
        Peca auxiliar = peca(posicao);
        auxiliar.posicao = null; //a peca não existe mais
        pecas[posicao.getLinha()][posicao.getColuna()] = null;
        return auxiliar;
    }

    private boolean posicaoExistente (int linha, int coluna){
        return linha >= 0 && linha <= 7 && coluna >= 0 && coluna <= 7;
    }

    public boolean posicaoExistente (Posicao posicao){
        return  posicaoExistente(posicao.getLinha(), posicao.getColuna());
    }

    public boolean aquiTemPeca(Posicao posicao){
        if(!posicaoExistente(posicao)){
            throw new ExcecaoTabuleiro("Essa posição não existe");
        }
        return peca(posicao) != null;
    }
}
