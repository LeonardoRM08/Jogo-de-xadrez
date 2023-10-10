package aplicativo;

import estrutura.Posicao;
import estrutura.Tabuleiro;
import xadrez.Partida;

public class Programa {
    public static void main(String[] args) {
        Partida partida = new Partida();
        Interface.printTabuleiro(partida.getPecas());


    }
}
