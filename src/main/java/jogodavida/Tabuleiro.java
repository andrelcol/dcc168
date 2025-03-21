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

    private void inicializarTabuleiro() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                celulas[i][j] = new Celula();
            }
        }
    }

    public void setCelula(int x, int y, boolean estado) {
        if (x >= 0 && x < linhas && y >= 0 && y < colunas) {
            celulas[x][y].setViva(estado);
        }
    }

    public boolean getCelula(int x, int y) {
        if (x >= 0 && x < linhas && y >= 0 && y < colunas) {
            return celulas[x][y].isViva();
        }
        return false;
    }

    public int contaVizinhosVivos(int x, int y) {
        int contador = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int novoX = x + i;
                int novoY = y + j;
                if (novoX >= 0 && novoX < linhas && novoY >= 0 && novoY < colunas) {
                    if (celulas[novoX][novoY].isViva()) {
                        contador++;
                    }
                }
            }
        }
        return contador;
    }

    public void proximaGeracao() {
        Celula[][] novaGeracao = new Celula[linhas][colunas];
        
        // Inicializa a nova geração
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                novaGeracao[i][j] = new Celula();
            }
        }

        // Aplica as regras do Jogo da Vida
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                int vizinhosVivos = contaVizinhosVivos(i, j);
                boolean celulaAtual = celulas[i][j].isViva();
                
                // Regras do Jogo da Vida
                if (celulaAtual) {
                    // Sobrevivência
                    novaGeracao[i][j].setViva(vizinhosVivos == 2 || vizinhosVivos == 3);
                } else {
                    // Nascimento
                    novaGeracao[i][j].setViva(vizinhosVivos == 3);
                }
            }
        }

        // Atualiza o tabuleiro com a nova geração
        this.celulas = novaGeracao;
    }
} 