package aplicativo;

import xadrez.Cor;
import xadrez.Partida;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoXadrez;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Interface {

    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

    // High Intensity backgrounds
    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";  // CYAN
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";   // WHITE

    public static final String BLACK_UNDERLINED = "\033[4;30m";  // BLACK
    public static final String RED_UNDERLINED = "\033[4;31m";    // RED
    public static final String GREEN_UNDERLINED = "\033[4;32m";  // GREEN
    public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
    public static final String BLUE_UNDERLINED = "\033[4;34m";   // BLUE
    public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
    public static final String CYAN_UNDERLINED = "\033[4;36m";   // CYAN
    public static final String WHITE_UNDERLINED = "\033[4;37m";  // WHITE

    public static PosicaoXadrez lePosicao(Scanner sc) {
        try {
            String s = sc.nextLine();
            char coluna = s.charAt(0); // é o primeiro caractere da posição
            int linha = Integer.parseInt(s.substring(1)); // segundo caractere
            return new PosicaoXadrez(coluna, linha);
        } catch (RuntimeException e) {
            throw new InputMismatchException("São aceitas as casas de a1 até h8");
        }
    }

    public static void limparTela() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Não foi possível limpar a tela: " + e.getMessage());
        }
    }

    public static void printJogo(Partida partida, List<PecaDeXadrez> capturadas) {
        printTabuleiro(partida.getPecas());
        System.out.println();
        printPecasPegas(capturadas);
        System.out.println();
        System.out.println("Turno: " + partida.getTurno());
        if(!partida.getCheckMate()) {
            System.out.println("Aguardando jogador " + partida.getJogador());
            if (partida.getCheck()) {
                System.out.println("Check!");
            }
        } else {
            System.out.println("Checkmate!");
            System.out.println("Vitória do jogador " + partida.getJogador() + "\nFim de jogo");
        }
    }

    public static void printTabuleiro(PecaDeXadrez[][] pecas) {
        System.out.println();
        System.out.println("   a b c d e f g h");
        for (int i = 0; i < pecas.length; i++) {
            System.out.print((8 - i) + "  ");
            for (int j = 0; j < pecas.length; j++) {
                printPeca(pecas[i][j], false);
            }
            System.out.print(" " + (8 - i));
            System.out.println();
        }
        System.out.println("   a b c d e f g h");
    }

    public static void printTabuleiro(PecaDeXadrez[][] pecas, boolean[][] movimentosPossiveis) {
        System.out.println();
        System.out.println("   a b c d e f g h");
        for (int i = 0; i < pecas.length; i++) {
            System.out.print((8 - i) + "  ");
            for (int j = 0; j < pecas.length; j++) {
                printPeca(pecas[i][j], movimentosPossiveis[i][j]);
            }
            System.out.print(" " + (8 - i));
            System.out.println();
        }
        System.out.println("   a b c d e f g h");
    }

    private static void printPeca(PecaDeXadrez peca, boolean colorir) {
        if (colorir) { // se sim...
            System.out.print(ANSI_RED_BACKGROUND + ANSI_BLACK);

        }
        if (peca == null) { //se posição vazia...
            System.out.print("-" + ANSI_RESET);
        } else {
            if (peca.getCor() == Cor.BRANCO) {
                System.out.print(YELLOW_BOLD_BRIGHT + peca + ANSI_RESET);
            } else {
                System.out.print(BLACK_BOLD_BRIGHT + peca + ANSI_RESET);
            }
        }
        System.out.print(" ");
    }

    private static void printPecasPegas(List<PecaDeXadrez> capturada) {
        List<PecaDeXadrez> brancas = capturada.stream().filter(x -> x.getCor() == Cor.BRANCO).toList();
        List<PecaDeXadrez> pretas = capturada.stream().filter(x -> x.getCor() == Cor.PRETO).toList();
        System.out.println("Peças pegas:");

        System.out.println(YELLOW_BOLD_BRIGHT);
        System.out.print("Brancas: ");
        System.out.print(Arrays.toString(brancas.toArray()));
        System.out.println(ANSI_RESET);

        System.out.println(BLACK_BOLD_BRIGHT);
        System.out.print("Pretas: ");
        System.out.print(Arrays.toString(pretas.toArray()));
        System.out.println(ANSI_RESET);
    }
}
