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
    }

    @Test
    void testRegrasSobrevivencia() {
        // Testa célula viva com 2 vizinhos (deve sobreviver)
        tabuleiro.setCelula(1, 1, true);
        tabuleiro.setCelula(1, 2, true);
        tabuleiro.setCelula(1, 3, true);
        
        tabuleiro.proximaGeracao();
        assertTrue(tabuleiro.getCelula(1, 2));
        
        // Testa célula morta com 3 vizinhos (deve nascer)
        tabuleiro = new Tabuleiro(6, 6);
        tabuleiro.setCelula(0, 0, true);
        tabuleiro.setCelula(0, 1, true);
        tabuleiro.setCelula(0, 2, true);
        
        tabuleiro.proximaGeracao();
        assertTrue(tabuleiro.getCelula(1, 1));
    }

    @Test
    void testPosicoesEspeciais() {
        // Testa células nas bordas
        tabuleiro.setCelula(0, 0, true);
        tabuleiro.setCelula(0, 1, true);
        tabuleiro.setCelula(1, 0, true);
        
        tabuleiro.proximaGeracao();
        assertTrue(tabuleiro.getCelula(0, 0));
    }

    @Test
    void testContagemVizinhos() {
        // Configura um padrão com 2 vizinhos vivos
        tabuleiro.setCelula(1, 1, true);
        tabuleiro.setCelula(1, 3, true);
        
        assertEquals(2, tabuleiro.contaVizinhosVivos(1, 2));
    }

    @Test
    void testTransicoesMultiplas() {
        // Configura um padrão que deve oscilar
        tabuleiro.setCelula(1, 1, true);
        tabuleiro.setCelula(1, 2, true);
        tabuleiro.setCelula(1, 3, true);
        
        tabuleiro.proximaGeracao();
        assertTrue(tabuleiro.getCelula(1, 2));
        
        tabuleiro.proximaGeracao();
        assertTrue(tabuleiro.getCelula(1, 1));
        assertTrue(tabuleiro.getCelula(1, 2));
        assertTrue(tabuleiro.getCelula(1, 3));
    }

    @Test
    void testPadroesConhecidos() {
        // Testa o padrão "blinker"
        tabuleiro.setCelula(1, 1, true);
        tabuleiro.setCelula(1, 2, true);
        tabuleiro.setCelula(1, 3, true);
        
        tabuleiro.proximaGeracao();
        assertTrue(tabuleiro.getCelula(1, 2));
        
        tabuleiro.proximaGeracao();
        assertTrue(tabuleiro.getCelula(1, 1));
        assertTrue(tabuleiro.getCelula(1, 2));
        assertTrue(tabuleiro.getCelula(1, 3));
    }
} 