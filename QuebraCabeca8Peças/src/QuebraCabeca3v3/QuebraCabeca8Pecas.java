package QuebraCabeca3v3;
import java.util.*;

public class QuebraCabeca8Pecas {

    public static void main(String[] args) {
        System.out.println("Márcio Luis Cipriano de Souza e João Vitor Leão Marques");
        System.out.println("Materia de I.A");
        int[][] initialBoard = getInputBoard("Digite a configuração inicial (separe os números por espaço):");
        int[][] targetBoard = getInputBoard("Digite a configuração desejada (separe os números por espaço):");

        List<int[][]> passos = resolverQuebraCabeca(initialBoard, targetBoard);

        if (passos == null) {
            System.out.println("Não foi possível encontrar uma solução para o quebra-cabeça.");
        } else {
            System.out.println("Passo a passo para resolver o quebra-cabeça:");
            for (int i = 0; i < passos.size(); i++) {
                imprimirTabuleiro(passos.get(i));
                System.out.println();
            }
        }
    }

    private static int[][] getInputBoard(String mensagem) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(mensagem);
        int[][] tabuleiro = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tabuleiro[i][j] = scanner.nextInt();
            }
        }
        return tabuleiro;
    }

    private static void imprimirTabuleiro(int[][] tabuleiro) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(tabuleiro[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static List<int[][]> resolverQuebraCabeca(int[][] tabuleiroInicial, int[][] tabuleiroAlvo) {
        Queue<int[][]> fila = new LinkedList<>();
        Map<String, Integer> visitados = new HashMap<>();
        Map<String, int[][]> pai = new HashMap<>();

        String tabuleiroInicialStr = tabuleiroParaString(tabuleiroInicial);
        fila.add(tabuleiroInicial);
        visitados.put(tabuleiroInicialStr, 0);

        int[][] direcoes = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };

        while (!fila.isEmpty()) {
            int[][] tabuleiroAtual = fila.poll();
            String tabuleiroAtualStr = tabuleiroParaString(tabuleiroAtual);

            if (tabuleiroAtualStr.equals(tabuleiroParaString(tabuleiroAlvo))) {
                return reconstruirPassos(pai, tabuleiroAtual);
            }

            int[] posicaoVazia = encontrarPosicaoVazia(tabuleiroAtual);

            for (int[] dir : direcoes) {
                int novaLinha = posicaoVazia[0] + dir[0];
                int novaColuna = posicaoVazia[1] + dir[1];

                if (isValidPosition(novaLinha, novaColuna)) {
                    int[][] novoTabuleiro = clonarTabuleiro(tabuleiroAtual);
                    trocar(novoTabuleiro, posicaoVazia[0], posicaoVazia[1], novaLinha, novaColuna);
                    String novoTabuleiroStr = tabuleiroParaString(novoTabuleiro);

                    if (!visitados.containsKey(novoTabuleiroStr)) {
                        fila.add(novoTabuleiro);
                        visitados.put(novoTabuleiroStr, visitados.get(tabuleiroAtualStr) + 1);
                        pai.put(novoTabuleiroStr, tabuleiroAtual);
                    }
                }
            }
        }

        return null;
    }

    private static boolean isValidPosition(int linha, int coluna) {
        return linha >= 0 && linha < 3 && coluna >= 0 && coluna < 3;
    }

    private static int[][] clonarTabuleiro(int[][] tabuleiro) {
        int[][] clone = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                clone[i][j] = tabuleiro[i][j];
            }
        }
        return clone;
    }

    public static void trocar(int[][] tabuleiro, int linha1, int coluna1, int linha2, int coluna2) {
        int temp = tabuleiro[linha1][coluna1];
        tabuleiro[linha1][coluna1] = tabuleiro[linha2][coluna2];
        tabuleiro[linha2][coluna2] = temp;
    }

    private static int[] encontrarPosicaoVazia(int[][] tabuleiro) {
        int[] posicao = new int[2];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[i][j] == 0) {
                    posicao[0] = i;
                    posicao[1] = j;
                    return posicao;
                }
            }
        }
        return posicao;
    }

    private static String tabuleiroParaString(int[][] tabuleiro) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sb.append(tabuleiro[i][j]);
                sb.append(" "); 
            }
        }
        return sb.toString();
    }

    private static List<int[][]> reconstruirPassos(Map<String, int[][]> pai, int[][] tabuleiroAtual) {
        List<int[][]> passos = new ArrayList<>();
        String tabuleiroAtualStr = tabuleiroParaString(tabuleiroAtual);

        while (tabuleiroAtualStr != null) {
            passos.add(0, tabuleiroAtual);
            tabuleiroAtual = pai.get(tabuleiroAtualStr);
            tabuleiroAtualStr = (tabuleiroAtual != null) ? tabuleiroParaString(tabuleiroAtual) : null;
        }

        return passos;
    }
}