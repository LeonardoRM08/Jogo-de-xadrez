package aplicativo;

import estrutura.Posicao;
import estrutura.Tabuleiro;
import xadrez.ExcecaoXadrez;
import xadrez.Partida;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoXadrez;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Programa {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Partida partida = new Partida();

        while (true) {
            try {

                Interface.printTabuleiro(partida.getPecas());
                System.out.println();

                System.out.print("Origem: ");
                PosicaoXadrez origem = Interface.lePosicao(sc);
                System.out.println();

                System.out.print("Destino: ");
                PosicaoXadrez destino = Interface.lePosicao(sc);

                PecaDeXadrez pecaPega = partida.movimentoDaPeca(origem, destino);
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
    }
}
