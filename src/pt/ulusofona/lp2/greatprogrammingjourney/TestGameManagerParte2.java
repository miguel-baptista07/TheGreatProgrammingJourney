package pt.ulusofona.lp2.greatprogrammingjourney;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

/**
 * Testes unitários para a parte 2 do projeto.
 * Testa abismos, ferramentas, interações e save/load.
 */
public class TestGameManagerParte2 {

    private GameManager gm;
    private final int WORLD_SIZE = 30;
    private final String[][] PLAYER_INFO = {
            {"1", "Alice", "Java", "Purple"},
            {"2", "Bob", "Python", "Green"},
            {"3", "Charlie", "Assembly", "Blue"}
    };

    @BeforeEach
    void setUp() {
        gm = new GameManager();
    }



    @Test
    void test_01_CreateBoardWithAbyssesAndTools() {
        String[][] elementos = {
                {"5", "A", "0"},
                {"10", "T", "0"},
                {"15", "A", "7"}
        };

        assertTrue(gm.createInitialBoard(PLAYER_INFO, WORLD_SIZE, elementos));
        assertEquals(1, gm.getCurrentPlayerID());
    }

    @Test
    void test_02_CreateBoardWithoutElements() {
        assertTrue(gm.createInitialBoard(PLAYER_INFO, WORLD_SIZE, null));
        assertEquals(3, gm.getPlayers().size());
    }



    @Test
    void test_03_SyntaxError_MovesBack1() {
        String[][] elementos = {{"5", "A", "0"}};
        gm.createInitialBoard(PLAYER_INFO, WORLD_SIZE, elementos);
        
        gm.moveCurrentPlayer(4);
        String reacao = gm.reactToAbyssOrTool();
        
        Player alice = gm.getPlayers().get(0);
        assertEquals(4, alice.getPosicao());
        assertTrue(reacao.contains("Syntax Error"));
    }

    @Test
    void test_04_LogicalError_MovesBackHalf() {
        String[][] elementos = {{"7", "A", "1"}};
        gm.createInitialBoard(PLAYER_INFO, WORLD_SIZE, elementos);
        
        gm.moveCurrentPlayer(6);
        gm.reactToAbyssOrTool();
        
        Player alice = gm.getPlayers().get(0);
        assertEquals(4, alice.getPosicao());
    }

    @Test
    void test_05_BSOD_EliminatesPlayer() {
        String[][] elementos = {{"5", "A", "7"}};
        gm.createInitialBoard(PLAYER_INFO, WORLD_SIZE, elementos);
        
        gm.moveCurrentPlayer(4);
        gm.reactToAbyssOrTool();
        
        Player alice = gm.getPlayers().get(0);
        assertTrue(alice.foiEliminado());
        assertEquals(PlayerState.ELIMINADO, alice.getEstado());
    }}
