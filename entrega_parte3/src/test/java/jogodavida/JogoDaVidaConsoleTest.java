package jogodavida;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import jogodavida.JogoDaVidaConsole;

class JogoDaVidaConsoleTest {
    private JogoDaVidaConsole jogo;

    @BeforeEach
    void setUp() {
        jogo = new JogoDaVidaConsole(5, 5);
        jogo.inicializarTabuleiro();
    }

    @Test
    void testInicializacao() {
        assertEquals(5, jogo.getLinhas());
        assertEquals(5, jogo.getColunas());
        assertEquals(0, jogo.getGeracao());
        
        // Verifica se todas as células estão mortas inicialmente
        for (int i = 0; i < jogo.getLinhas(); i++) {
            for (int j = 0; j < jogo.getColunas(); j++) {
                assertFalse(jogo.getCelula(i, j));
            }
        }
    }

    @Test
    void testSetGetCelula() {
        jogo.setCelula(0, 0, true);
        assertTrue(jogo.getCelula(0, 0));
        
        jogo.setCelula(0, 0, false);
        assertFalse(jogo.getCelula(0, 0));
    }

    @Test
    void testProximaGeracao() {
        // Configura um padrão simples (bloco 2x2)
        jogo.setCelula(0, 0, true);
        jogo.setCelula(0, 1, true);
        jogo.setCelula(1, 0, true);
        jogo.setCelula(1, 1, true);

        // Verifica estado inicial
        assertTrue(jogo.getCelula(0, 0));
        assertTrue(jogo.getCelula(0, 1));
        assertTrue(jogo.getCelula(1, 0));
        assertTrue(jogo.getCelula(1, 1));
        assertEquals(0, jogo.getGeracao());

        // Avança uma geração
        jogo.proximaGeracao();
        assertEquals(1, jogo.getGeracao());

        // Verifica se o bloco permanece estável
        assertTrue(jogo.getCelula(0, 0));
        assertTrue(jogo.getCelula(0, 1));
        assertTrue(jogo.getCelula(1, 0));
        assertTrue(jogo.getCelula(1, 1));
    }

    @Test
    void testGlider() {
        // Configura um glider
        jogo.setCelula(0, 1, true);
        jogo.setCelula(1, 2, true);
        jogo.setCelula(2, 0, true);
        jogo.setCelula(2, 1, true);
        jogo.setCelula(2, 2, true);

        // Verifica estado inicial
        assertTrue(jogo.getCelula(0, 1));
        assertTrue(jogo.getCelula(1, 2));
        assertTrue(jogo.getCelula(2, 0));
        assertTrue(jogo.getCelula(2, 1));
        assertTrue(jogo.getCelula(2, 2));

        // Avança uma geração
        jogo.proximaGeracao();
        assertEquals(1, jogo.getGeracao());

        // Verifica se o glider se moveu corretamente
        assertTrue(jogo.getCelula(1, 0));
        assertTrue(jogo.getCelula(1, 2));
        assertTrue(jogo.getCelula(2, 1));
        assertTrue(jogo.getCelula(2, 2));
        assertTrue(jogo.getCelula(3, 1));
    }

    @Test
    void testCoordenadasInvalidas() {
        // Testa coordenadas fora dos limites
        jogo.setCelula(-1, 0, true);
        assertFalse(jogo.getCelula(-1, 0));

        jogo.setCelula(0, -1, true);
        assertFalse(jogo.getCelula(0, -1));

        jogo.setCelula(5, 0, true);
        assertFalse(jogo.getCelula(5, 0));

        jogo.setCelula(0, 5, true);
        assertFalse(jogo.getCelula(0, 5));
    }
} 