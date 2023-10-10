package aplicativo;

import xadrez.PecaDeXadrez;

public class Interface {
    public static  void printTabuleiro(PecaDeXadrez[][] pecas){
        System.out.println();
        for (int i = 0; i < pecas.length; i ++){
            System.out.print((8 - i) + " ");
            for (int j = 0; j < pecas.length; j ++){
                printPeca(pecas[i][j]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    private static void printPeca(PecaDeXadrez peca){
        if (peca == null){ //se posição vazia...
            System.out.print("-");
        }
        else {
            System.out.print(peca);
        }
        System.out.print(" ");
    }
}
