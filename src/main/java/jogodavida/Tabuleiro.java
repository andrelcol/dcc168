package jogodavida;

public class Tabuleiro {
    private Celula[][] celulas;
    private int linhas;
    private int colunas;

    public Tabuleiro(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.celulas = new Celula[linhas][colunas];
        inicializarTabuleiro();
    }

    public void inicializarTabuleiro() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                celulas[i][j] = new Celula();
            }
        }
    }

    public void setCelula(int x, int y, boolean estado) {
        if (coordenadaValida(x, y)) {
            celulas[x][y].setViva(estado);
        }
    }

    public boolean getCelula(int x, int y) {
        if (coordenadaValida(x, y)) {
            return celulas[x][y].isViva();
        }
        return false;
    }

    private boolean coordenadaValida(int x, int y) {
        return x >= 0 && x < linhas && y >= 0 && y < colunas;
    }

    public int contaVizinhosVivos(int x, int y) {
        int contador = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int novoX = x + i;
                int novoY = y + j;
                if (coordenadaValida(novoX, novoY) && celulas[novoX][novoY].isViva()) {
                    contador++;
                }
            }
        }
        return contador;
    }

    public void proximaGeracao() {
        // Cria um array temporário para armazenar os novos estados
        boolean[][] novosEstados = new boolean[linhas][colunas];

        // Calcula o novo estado de cada célula
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                int vizinhosVivos = contaVizinhosVivos(i, j);
                boolean celulaAtual = celulas[i][j].isViva();

                // Aplica as regras do Jogo da Vida
                if (celulaAtual) {
                    // Uma célula viva sobrevive se tiver 2 ou 3 vizinhos vivos
                    novosEstados[i][j] = vizinhosVivos == 2 || vizinhosVivos == 3;
                } else {
                    // Uma célula morta nasce se tiver exatamente 3 vizinhos vivos
                    novosEstados[i][j] = vizinhosVivos == 3;
                }
            }
        }

        // Atualiza o estado das células
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                celulas[i][j].setViva(novosEstados[i][j]);
            }
        }
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }
} 