package jogodavida;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TabuleiroTest {
    private Tabuleiro tabuleiro;

    @BeforeEach
    void setUp() {
        tabuleiro = new Tabuleiro(6, 6);
    }

    @Test
    void testInicializacao() {
        assertNotNull(tabuleiro);
        // Verifica se todas as células começam mortas
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                assertFalse(tabuleiro.getCelula(i, j));
            }
        }
    }

    @Test
    void testMortePorSolidao() {
        // Célula com 0 vizinhos morre
        tabuleiro.setCelula(2, 2, true);
        tabuleiro.proximaGeracao();
        assertFalse(tabuleiro.getCelula(2, 2));

        // Célula com 1 vizinho morre
        tabuleiro = new Tabuleiro(6, 6);
        tabuleiro.setCelula(2, 2, true);
        tabuleiro.setCelula(2, 3, true);
        tabuleiro.proximaGeracao();
        assertFalse(tabuleiro.getCelula(2, 2));
        assertFalse(tabuleiro.getCelula(2, 3));
    }

    @Test
    void testSobrevivencia() {
        // Célula com 2 vizinhos sobrevive
        tabuleiro.setCelula(1, 2, true);
        tabuleiro.setCelula(2, 2, true);
        tabuleiro.setCelula(3, 2, true);
        tabuleiro.proximaGeracao();
        assertTrue(tabuleiro.getCelula(2, 2));

        // Célula com 3 vizinhos sobrevive
        tabuleiro = new Tabuleiro(6, 6);
        tabuleiro.setCelula(1, 1, true);
        tabuleiro.setCelula(1, 2, true);
        tabuleiro.setCelula(2, 1, true);
        tabuleiro.setCelula(2, 2, true);
        tabuleiro.proximaGeracao();
        assertTrue(tabuleiro.getCelula(2, 2));
    }

    @Test
    void testMortePorSuperpopulacao() {
        // Célula com 4 vizinhos morre
        tabuleiro.setCelula(1, 1, true);
        tabuleiro.setCelula(1, 2, true);
        tabuleiro.setCelula(1, 3, true);
        tabuleiro.setCelula(2, 2, true);
        tabuleiro.setCelula(3, 2, true);
        tabuleiro.proximaGeracao();
        assertFalse(tabuleiro.getCelula(2, 2));
    }

    @Test
    void testNascimento() {
        // Célula morta com 3 vizinhos nasce
        tabuleiro.setCelula(1, 1, true);
        tabuleiro.setCelula(1, 2, true);
        tabuleiro.setCelula(1, 3, true);
        tabuleiro.proximaGeracao();
        assertTrue(tabuleiro.getCelula(2, 2));
    }

    @Test
    void testCoordenadasInvalidas() {
        // Testa coordenadas negativas
        assertFalse(tabuleiro.getCelula(-1, -1));
        tabuleiro.setCelula(-1, -1, true);
        assertFalse(tabuleiro.getCelula(-1, -1));

        // Testa coordenadas fora dos limites
        assertFalse(tabuleiro.getCelula(6, 6));
        tabuleiro.setCelula(6, 6, true);
        assertFalse(tabuleiro.getCelula(6, 6));
    }

    @Test
    void testBlinker() {
        // Configura o padrão blinker
        tabuleiro.setCelula(2, 1, true);
        tabuleiro.setCelula(2, 2, true);
        tabuleiro.setCelula(2, 3, true);

        // Primeira geração
        tabuleiro.proximaGeracao();
        assertTrue(tabuleiro.getCelula(1, 2));
        assertTrue(tabuleiro.getCelula(2, 2));
        assertTrue(tabuleiro.getCelula(3, 2));

        // Segunda geração (volta ao padrão original)
        tabuleiro.proximaGeracao();
        assertTrue(tabuleiro.getCelula(2, 1));
        assertTrue(tabuleiro.getCelula(2, 2));
        assertTrue(tabuleiro.getCelula(2, 3));
    }

    @Test
    void testBloco() {
        // Configura o padrão bloco 2x2
        tabuleiro.setCelula(1, 1, true);
        tabuleiro.setCelula(1, 2, true);
        tabuleiro.setCelula(2, 1, true);
        tabuleiro.setCelula(2, 2, true);

        // O bloco deve permanecer estável
        tabuleiro.proximaGeracao();
        assertTrue(tabuleiro.getCelula(1, 1));
        assertTrue(tabuleiro.getCelula(1, 2));
        assertTrue(tabuleiro.getCelula(2, 1));
        assertTrue(tabuleiro.getCelula(2, 2));
    }
} 