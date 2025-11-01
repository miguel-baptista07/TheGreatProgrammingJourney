package pt.ulusofona.lp2.greatprogrammingjourney;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestGameManagerMove {

    private GameManager gm;
    private final int WORLD_SIZE = 20;
    private final String[][] PLAYER_INFO = {
            {"1", "Alice", "Java", "Purple"},
            {"2", "Bob", "Python", "Green"}
    };

    @BeforeEach
    void setUp() {
        gm = new GameManager();
        gm.createInitialBoard(PLAYER_INFO, WORLD_SIZE);
    }

    @Test
    void test_01_ValidMove_PlayerAdvances() {
        int initialPosition = 1;
        int moveSpaces = 3;
        int expectedPosition = initialPosition + moveSpaces;

        assertTrue(gm.moveCurrentPlayer(moveSpaces));
        String[] aliceInfo = gm.getProgrammerInfo(1);
        assertEquals(String.valueOf(expectedPosition), aliceInfo[4]);
        assertEquals(2, gm.getCurrentPlayerID());
    }

    @Test
    void test_02_TurnAdvancement_WrapsAround() {
        assertEquals(1, gm.getCurrentPlayerID());
        gm.moveCurrentPlayer(1);
        assertEquals(2, gm.getCurrentPlayerID());
        gm.moveCurrentPlayer(1);
        assertEquals(1, gm.getCurrentPlayerID());
    }

    @Test
    void test_03_InvalidMove_TooHigh() {
        int invalidMoveSpaces = 7;
        assertFalse(gm.moveCurrentPlayer(invalidMoveSpaces));
        assertEquals(1, gm.getCurrentPlayerID());
        String[] aliceInfo = gm.getProgrammerInfo(1);
        assertEquals("1", aliceInfo[4]);
    }

    @Test
    void test_04_InvalidMove_TooLow() {
        int invalidMoveSpaces = 0;
        assertFalse(gm.moveCurrentPlayer(invalidMoveSpaces));
        assertEquals(1, gm.getCurrentPlayerID());
    }

    @Test
    void test_05_BoundaryCondition_Overshoot() {
        gm.getPlayers().get(0).setPosicao(18);
        int moveSpaces = 5;
        int expectedPosition = 17;

        assertTrue(gm.moveCurrentPlayer(moveSpaces));
        String[] aliceInfo = gm.getProgrammerInfo(1);
        assertEquals(String.valueOf(expectedPosition), aliceInfo[4]);
        assertFalse(gm.gameIsOver());
    }

    @Test
    void test_06_BoundaryCondition_ReachEnd() {
        gm.getPlayers().get(1).setPosicao(19);
        gm.getPlayers().get(0).setPosicao(19);

        int moveSpaces = 1;
        int expectedPosition = 20;

        assertTrue(gm.moveCurrentPlayer(moveSpaces));
        String[] aliceInfo = gm.getProgrammerInfo(1);
        assertEquals(String.valueOf(expectedPosition), aliceInfo[4]);
        assertTrue(gm.gameIsOver());
        assertEquals(2, gm.getCurrentPlayerID());
    }
}
