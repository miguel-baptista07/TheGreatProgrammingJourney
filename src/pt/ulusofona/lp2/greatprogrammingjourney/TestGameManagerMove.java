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
        // Jogador 1 (Alice) está na posição 1 (posição inicial)
        int initialPosition = 1;
        int moveSpaces = 3;
        int expectedPosition = initialPosition + moveSpaces;

        // 1. Mover o jogador atual (Alice)
        assertTrue(gm.moveCurrentPlayer(moveSpaces), "O movimento deve ser válido.");

        // 2. Verificar a nova posição de Alice
        // O ID de Alice é "1"
        String[] aliceInfo = gm.getProgrammerInfo(1);
        assertEquals(String.valueOf(expectedPosition), aliceInfo[4], "Alice deve estar na posição " + expectedPosition);

        // 3. Verificar que o turno avançou para o próximo jogador (Bob)
        assertEquals(2, gm.getCurrentPlayerID(), "O turno deve ter avançado para o Jogador 2 (Bob)");
    }

    @Test
    void test_02_TurnAdvancement_WrapsAround() {
        // O turno começa em Alice (ID 1)
        assertEquals(1, gm.getCurrentPlayerID(), "O turno deve começar em Alice (ID 1)");

        // 1. Alice move
        gm.moveCurrentPlayer(1);
        assertEquals(2, gm.getCurrentPlayerID(), "Após o movimento de Alice, o turno deve ser de Bob (ID 2)");

        // 2. Bob move
        gm.moveCurrentPlayer(1);
        assertEquals(1, gm.getCurrentPlayerID(), "Após o movimento de Bob, o turno deve voltar para Alice (ID 1)");
    }

    @Test
    void test_03_InvalidMove_TooHigh() {
        // Tentar mover com um valor de dado inválido (> 6)
        int invalidMoveSpaces = 7;

        // 1. Tentar mover
        assertFalse(gm.moveCurrentPlayer(invalidMoveSpaces), "O movimento com 7 casas deve ser inválido");

        // 2. Verificar que o jogador atual não mudou (continua Alice)
        assertEquals(1, gm.getCurrentPlayerID(), "O turno não deve avançar após um movimento inválido");

        // 3. Verificar que a posição do jogador não mudou (continua 1)
        String[] aliceInfo = gm.getProgrammerInfo(1);
        assertEquals("1", aliceInfo[4], "A posição de Alice não deve mudar após um movimento inválido");
    }

    @Test
    void test_04_InvalidMove_TooLow() {
        // Tentar mover com um valor de dado inválido (< 1)
        int invalidMoveSpaces = 0;

        // 1. Tentar mover
        assertFalse(gm.moveCurrentPlayer(invalidMoveSpaces), "O movimento com 0 casas deve ser inválido");

        // 2. Verificar que o jogador atual não mudou (continua Alice)
        assertEquals(1, gm.getCurrentPlayerID(), "O turno não deve avançar após um movimento inválido");
    }

    @Test
    void test_05_BoundaryCondition_Overshoot() {
        // Mover Alice (ID 1) para perto do fim (Posição 18)
        // O tabuleiro tem 20 casas (1 a 20). A última casa é a 20.
        // O jogador deve estar na posição 18.
        gm.getPlayers().get(0).setPosicao(18); // Alice na 18

        // 1. Alice move 5 casas (18 + 5 = 23). Excesso = 3. Nova posição = 20 - 3 = 17.
        int moveSpaces = 5;
        int expectedPosition = 17;

        assertTrue(gm.moveCurrentPlayer(moveSpaces), "O movimento deve ser válido");

        // 2. Verificar a nova posição de Alice
        String[] aliceInfo = gm.getProgrammerInfo(1);
        assertEquals(String.valueOf(expectedPosition), aliceInfo[4], "Alice deve recuar para a posição " + expectedPosition);

        // 3. O jogo não deve ter terminado (17 < 20)
        assertFalse(gm.gameIsOver(), "O jogo não deve ter terminado");
    }

    @Test
    void test_06_BoundaryCondition_ReachEnd() {
        // Mover Bob (ID 2) para a posição 19 (para que seja o próximo a jogar)
        gm.getPlayers().get(1).setPosicao(19); // Bob na 19

        // 1. Mover Alice (ID 1) para a posição 19
        gm.getPlayers().get(0).setPosicao(19); // Alice na 19

        // 2. Alice move 1 casa (19 + 1 = 20). Atinge o fim.
        int moveSpaces = 1;
        int expectedPosition = 20;

        assertTrue(gm.moveCurrentPlayer(moveSpaces), "O movimento deve ser válido");

        // 3. Verificar a nova posição de Alice
        String[] aliceInfo = gm.getProgrammerInfo(1);
        assertEquals(String.valueOf(expectedPosition), aliceInfo[4], "Alice deve estar na posição " + expectedPosition);

        // 4. O jogo deve ter terminado
        assertTrue(gm.gameIsOver(), "O jogo deve ter terminado");

        // 5. O turno deve ter avançado para Bob (embora o jogo tenha terminado)
        assertEquals(2, gm.getCurrentPlayerID(), "O turno deve ter avançado para Bob");
    }
}