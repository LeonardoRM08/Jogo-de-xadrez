package aplicativo;

import estrutura.Peca;
import estrutura.Posicao;
import estrutura.Tabuleiro;
import xadrez.ExcecaoXadrez;
import xadrez.Partida;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoXadrez;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Programa {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Partida partida = new Partida();
        List<PecaDeXadrez> capturadas = new ArrayList<>();

        while (!partida.getCheckMate()) {
            try {
                Interface.limparTela();
                Interface.printJogo(partida, capturadas);
                System.out.println();

                System.out.print("Origem: ");
                PosicaoXadrez origem = Interface.lePosicao(sc);
                System.out.println();

                boolean[][] possibilidades = partida.movimentosPossiveis(origem);
                Interface.printTabuleiro(partida.getPecas(), possibilidades);

                System.out.print("Destino: ");
                PosicaoXadrez destino = Interface.lePosicao(sc);

                PecaDeXadrez pecaPega = partida.movimentoDaPeca(origem, destino);

                if (pecaPega != null) {
                    capturadas.add(pecaPega);
                }

                if (partida.getPromocao() != null){
                    System.out.print("Escolha a peça que entrará no lugar do peão promovido (T/C/B/D): ");
                    String tipo = sc.nextLine();
                    partida.recebePromocao(tipo);
                }
            }
            catch (ExcecaoXadrez e){
                System.out.println(e.getMessage());
                sc.nextLine();
            }
            catch (InputMismatchException e){
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
        Interface.printJogo(partida, capturadas);


    }
}
