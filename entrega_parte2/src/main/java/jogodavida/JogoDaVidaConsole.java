package jogodavida;

public class JogoDaVidaConsole {
    private Tabuleiro tabuleiro;
    private int geracao;

    public JogoDaVidaConsole(int linhas, int colunas) {
        this.tabuleiro = new Tabuleiro(linhas, colunas);
        this.geracao = 0;
    }

    public void inicializarTabuleiro() {
        tabuleiro.inicializarTabuleiro();
    }

    public void setCelula(int linha, int coluna, boolean viva) {
        tabuleiro.setCelula(linha, coluna, viva);
    }

    public boolean getCelula(int linha, int coluna) {
        return tabuleiro.getCelula(linha, coluna);
    }

    public void proximaGeracao() {
        tabuleiro.proximaGeracao();
        geracao++;
    }

    public int getGeracao() {
        return geracao;
    }

    public int getLinhas() {
        return tabuleiro.getLinhas();
    }

    public int getColunas() {
        return tabuleiro.getColunas();
    }

    public static void main(String[] args) {
        // Exemplo de uso
        JogoDaVidaConsole jogo = new JogoDaVidaConsole(5, 5);
        jogo.inicializarTabuleiro();
        
        // Configurar padrão inicial (exemplo: glider)
        jogo.setCelula(0, 1, true);
        jogo.setCelula(1, 2, true);
        jogo.setCelula(2, 0, true);
        jogo.setCelula(2, 1, true);
        jogo.setCelula(2, 2, true);

        // Executar algumas gerações
        for (int i = 0; i < 4; i++) {
            System.out.println("Geração " + jogo.getGeracao() + ":");
            imprimirTabuleiro(jogo);
            System.out.println();
            jogo.proximaGeracao();
        }
    }

    private static void imprimirTabuleiro(JogoDaVidaConsole jogo) {
        for (int i = 0; i < jogo.getLinhas(); i++) {
            for (int j = 0; j < jogo.getColunas(); j++) {
                System.out.print(jogo.getCelula(i, j) ? "■ " : "□ ");
            }
            System.out.println();
        }
    }
} 