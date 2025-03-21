package jogodavida;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TabuleiroTest {
    private Tabuleiro tabuleiro;
    private static final int LINHAS = 5;
    private static final int COLUNAS = 5;

    @BeforeEach
    void setUp() {
        tabuleiro = new Tabuleiro(LINHAS, COLUNAS);
    }

    // Testes de Fluxo de Controle
    @Test
    void testInicializacao() {
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                assertFalse(tabuleiro.getCelula(i, j), "Célula deve iniciar morta");
            }
        }
    }

    @Test
    void testSetGetCelula() {
        tabuleiro.setCelula(0, 0, true);
        assertTrue(tabuleiro.getCelula(0, 0), "Célula deve estar viva após set");
        
        tabuleiro.setCelula(0, 0, false);
        assertFalse(tabuleiro.getCelula(0, 0), "Célula deve estar morta após set");
    }

    @Test
    void testContaVizinhosVivos() {
        // Configura um padrão com 3 vizinhos vivos
        tabuleiro.setCelula(0, 0, true);
        tabuleiro.setCelula(0, 1, true);
        tabuleiro.setCelula(0, 2, true);
        
        assertEquals(1, tabuleiro.contaVizinhosVivos(0, 0), "Célula da esquerda deve ter 1 vizinho");
        assertEquals(2, tabuleiro.contaVizinhosVivos(0, 1), "Célula do meio deve ter 2 vizinhos");
        assertEquals(1, tabuleiro.contaVizinhosVivos(0, 2), "Célula da direita deve ter 1 vizinho");
    }

    @Test
    void testProximaGeracao() {
        // Configura um padrão Blinker (3 células em linha)
        tabuleiro.setCelula(1, 0, true);
        tabuleiro.setCelula(1, 1, true);
        tabuleiro.setCelula(1, 2, true);
        
        // Verifica estado inicial
        assertTrue(tabuleiro.getCelula(1, 0), "Célula inicial deve estar viva");
        assertTrue(tabuleiro.getCelula(1, 1), "Célula inicial deve estar viva");
        assertTrue(tabuleiro.getCelula(1, 2), "Célula inicial deve estar viva");
        
        // Avança uma geração
        tabuleiro.proximaGeracao();
        
        // Verifica estado após uma geração (deve virar vertical)
        assertFalse(tabuleiro.getCelula(1, 0), "Célula deve estar morta após uma geração");
        assertTrue(tabuleiro.getCelula(1, 1), "Célula do meio deve permanecer viva");
        assertFalse(tabuleiro.getCelula(1, 2), "Célula deve estar morta após uma geração");
        assertTrue(tabuleiro.getCelula(0, 1), "Célula acima deve nascer");
        assertTrue(tabuleiro.getCelula(2, 1), "Célula abaixo deve nascer");
    }

    // Testes de Fluxo de Dados
    @Test
    void testFluxoDadosNascimento() {
        // Configura 3 vizinhos vivos para uma célula morta
        tabuleiro.setCelula(0, 0, true);
        tabuleiro.setCelula(0, 1, true);
        tabuleiro.setCelula(0, 2, true);
        
        tabuleiro.proximaGeracao();
        assertTrue(tabuleiro.getCelula(1, 1), "Célula morta com 3 vizinhos deve nascer");
    }

    @Test
    void testFluxoDadosMorte() {
        // Configura uma célula viva com 4 vizinhos
        tabuleiro.setCelula(1, 1, true);
        tabuleiro.setCelula(0, 0, true);
        tabuleiro.setCelula(0, 1, true);
        tabuleiro.setCelula(0, 2, true);
        tabuleiro.setCelula(1, 0, true);
        
        tabuleiro.proximaGeracao();
        assertFalse(tabuleiro.getCelula(1, 1), "Célula viva com 4 vizinhos deve morrer");
    }

    @Test
    void testFluxoDadosSobrevivencia() {
        // Configura uma célula viva com 2 vizinhos
        tabuleiro.setCelula(1, 1, true);
        tabuleiro.setCelula(0, 0, true);
        tabuleiro.setCelula(0, 1, true);
        
        tabuleiro.proximaGeracao();
        assertTrue(tabuleiro.getCelula(1, 1), "Célula viva com 2 vizinhos deve sobreviver");
    }

    // Testes de Casos Especiais
    @Test
    void testBordas() {
        // Testa células nas bordas do tabuleiro
        tabuleiro.setCelula(0, 0, true);
        tabuleiro.setCelula(0, COLUNAS-1, true);
        tabuleiro.setCelula(LINHAS-1, 0, true);
        tabuleiro.setCelula(LINHAS-1, COLUNAS-1, true);
        
        assertEquals(0, tabuleiro.contaVizinhosVivos(0, 0), "Célula no canto superior esquerdo deve ter 0 vizinhos");
        assertEquals(0, tabuleiro.contaVizinhosVivos(0, COLUNAS-1), "Célula no canto superior direito deve ter 0 vizinhos");
        assertEquals(0, tabuleiro.contaVizinhosVivos(LINHAS-1, 0), "Célula no canto inferior esquerdo deve ter 0 vizinhos");
        assertEquals(0, tabuleiro.contaVizinhosVivos(LINHAS-1, COLUNAS-1), "Célula no canto inferior direito deve ter 0 vizinhos");
    }

    @Test
    void testPadraoEstavel() {
        // Configura um padrão estável (bloco 2x2)
        tabuleiro.setCelula(0, 0, true);
        tabuleiro.setCelula(0, 1, true);
        tabuleiro.setCelula(1, 0, true);
        tabuleiro.setCelula(1, 1, true);
        
        // Verifica se o padrão permanece estável após várias gerações
        for (int i = 0; i < 5; i++) {
            tabuleiro.proximaGeracao();
            assertTrue(tabuleiro.getCelula(0, 0), "Célula deve permanecer viva");
            assertTrue(tabuleiro.getCelula(0, 1), "Célula deve permanecer viva");
            assertTrue(tabuleiro.getCelula(1, 0), "Célula deve permanecer viva");
            assertTrue(tabuleiro.getCelula(1, 1), "Célula deve permanecer viva");
        }
    }
} 