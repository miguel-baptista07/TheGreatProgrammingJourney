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

    // ========== TESTES DE CRIAÇÃO DO TABULEIRO ==========

    @Test
    void test_01_CreateBoardWithAbyssesAndTools() {
        String[][] elementos = {
                {"5", "A", "0"},  // Syntax Error na posição 5
                {"10", "T", "0"}, // Debugger na posição 10
                {"15", "A", "7"}  // BSOD na posição 15
        };

        assertTrue(gm.createInitialBoard(PLAYER_INFO, WORLD_SIZE, elementos));
        assertEquals(1, gm.getCurrentPlayerID());
    }

    @Test
    void test_02_CreateBoardWithoutElements() {
        assertTrue(gm.createInitialBoard(PLAYER_INFO, WORLD_SIZE, null));
        assertEquals(3, gm.getPlayers().size());
    }

    // ========== TESTES DE ABISMOS ==========

    @Test
    void test_03_SyntaxError_MovesBack1() {
        String[][] elementos = {{"5", "A", "0"}};
        gm.createInitialBoard(PLAYER_INFO, WORLD_SIZE, elementos);
        
        gm.moveCurrentPlayer(4); // Alice vai para posição 5
        String reacao = gm.reactToAbyssOrTool();
        
        Player alice = gm.getPlayers().get(0);
        assertEquals(4, alice.getPosicao()); // Voltou 1 casa
        assertTrue(reacao.contains("Syntax Error"));
    }

    @Test
    void test_04_LogicalError_MovesBackHalf() {
        String[][] elementos = {{"7", "A", "1"}};
        gm.createInitialBoard(PLAYER_INFO, WORLD_SIZE, elementos);
        
        gm.moveCurrentPlayer(6); // Alice vai para posição 7
        gm.reactToAbyssOrTool();
        
        Player alice = gm.getPlayers().get(0);
        assertEquals(4, alice.getPosicao()); // Voltou metade (6/2 = 3)
    }

    @Test
    void test_05_BSOD_EliminatesPlayer() {
        String[][] elementos = {{"5", "A", "7"}};
        gm.createInitialBoard(PLAYER_INFO, WORLD_SIZE, elementos);
        
        gm.moveCurrentPlayer(4); // Alice vai para posição 5
        gm.reactToAbyssOrTool();
        
        Player alice = gm.getPlayers().get(0);
        assertTrue(alice.foiEliminado());
        assertEquals(PlayerState.ELIMINADO, alice.getEstado());
    }

    @Test
    void test_06_InfiniteLoop_TrapsPlayer() {
        String[][] elementos = {{"5", "A", "8"}};
        gm.createInitialBoard(PLAYER_INFO, WORLD_SIZE, elementos);
        
        gm.moveCurrentPlayer(4); // Alice vai para posição 5
        gm.reactToAbyssOrTool();
        
        Player alice = gm.getPlayers().get(0);
        assertTrue(alice.estaPreso());
        assertEquals(PlayerState.PRESO, alice.getEstado());
    }

    @Test
    void test_07_Crash_SendsToPosition1() {
        String[][] elementos = {{"10", "A", "4"}};
        gm.createInitialBoard(PLAYER_INFO, WORLD_SIZE, elementos);
        
        gm.moveCurrentPlayer(6); // Alice vai para 7
        gm.reactToAbyssOrTool();
        gm.moveCurrentPlayer(3); // Alice vai para 10
        gm.reactToAbyssOrTool();
        
        Player alice = gm.getPlayers().get(0);
        assertEquals(1, alice.getPosicao());
    }

    // ========== TESTES DE FERRAMENTAS ==========

    @Test
    void test_08_PlayerPicksUpTool() {
        String[][] elementos = {{"5", "T", "0"}};
        gm.createInitialBoard(PLAYER_INFO, WORLD_SIZE, elementos);
        
        gm.moveCurrentPlayer(4); // Alice vai para posição 5
        gm.reactToAbyssOrTool();
        
        Player alice = gm.getPlayers().get(0);
        assertTrue(alice.temFerramenta(0)); // Tem Debugger
        assertEquals(1, alice.getFerramentas().size());
    }

    @Test
    void test_09_PlayerDoesNotPickUpToolTwice() {
        String[][] elementos = {
                {"5", "T", "0"},
                {"8", "T", "0"}
        };
        gm.createInitialBoard(PLAYER_INFO, WORLD_SIZE, elementos);
        
        gm.moveCurrentPlayer(4); // Alice vai para 5
        gm.reactToAbyssOrTool();
        gm.moveCurrentPlayer(3); // Alice vai para 8
        gm.reactToAbyssOrTool();
        
        Player alice = gm.getPlayers().get(0);
        assertEquals(1, alice.getFerramentas().size()); // Ainda tem só 1
    }

    // ========== TESTES DE NEUTRALIZAÇÃO ==========

    @Test
    void test_10_DebuggerNeutralizesSyntaxError() {
        String[][] elementos = {
                {"3", "T", "0"}, // Debugger
                {"7", "A", "0"}  // Syntax Error
        };
        gm.createInitialBoard(PLAYER_INFO, WORLD_SIZE, elementos);
        
        gm.moveCurrentPlayer(2); // Alice vai para 3
        gm.reactToAbyssOrTool(); // Apanha Debugger
        
        gm.moveCurrentPlayer(4); // Alice vai para 7
        String reacao = gm.reactToAbyssOrTool();
        
        Player alice = gm.getPlayers().get(0);
        assertEquals(7, alice.getPosicao()); // Não voltou
        assertTrue(reacao.contains("neutralizou"));
        assertFalse(alice.temFerramenta(0)); // Ferramenta foi usada
    }

    @Test
    void test_11_AntivirusPreventsBSOD() {
        String[][] elementos = {
                {"3", "T", "4"}, // Antivirus
                {"7", "A", "7"}  // BSOD
        };
        gm.createInitialBoard(PLAYER_INFO, WORLD_SIZE, elementos);
        
        gm.moveCurrentPlayer(2); // Alice apanha Antivirus
        gm.reactToAbyssOrTool();
        
        gm.moveCurrentPlayer(4); // Alice vai para BSOD
        gm.reactToAbyssOrTool();
        
        Player alice = gm.getPlayers().get(0);
        assertFalse(alice.foiEliminado()); // Não foi eliminada!
        assertEquals(PlayerState.ATIVO, alice.getEstado());
    }

    @Test
    void test_12_BreakpointFreesFromInfiniteLoop() {
        String[][] elementos = {
                {"3", "T", "5"}, // Breakpoint
                {"7", "A", "8"}  // Infinite Loop
        };
        gm.createInitialBoard(PLAYER_INFO, WORLD_SIZE, elementos);
        
        gm.moveCurrentPlayer(2); // Alice apanha Breakpoint
        gm.reactToAbyssOrTool();
        
        gm.moveCurrentPlayer(4); // Alice vai para Infinite Loop
        gm.reactToAbyssOrTool();
        
        Player alice = gm.getPlayers().get(0);
        assertFalse(alice.estaPreso()); // Foi libertada!
        assertEquals(PlayerState.ATIVO, alice.getEstado());
    }

    // ========== TESTES DE MOVIMENTO ==========

    @Test
    void test_13_AssemblyCannotMove5or6() {
        gm.createInitialBoard(PLAYER_INFO, WORLD_SIZE, null);
        
        // Charlie usa Assembly
        gm.moveCurrentPlayer(1); // Alice
        gm.reactToAbyssOrTool();
        gm.moveCurrentPlayer(1); // Bob
        gm.reactToAbyssOrTool();
        
        assertFalse(gm.moveCurrentPlayer(5)); // Charlie não pode mover 5
        assertFalse(gm.moveCurrentPlayer(6)); // Charlie não pode mover 6
        assertTrue(gm.moveCurrentPlayer(4));  // Charlie pode mover 4
    }

    @Test
    void test_14_TrappedPlayerCannotMove() {
        String[][] elementos = {{"3", "A", "8"}};
        gm.createInitialBoard(PLAYER_INFO, WORLD_SIZE, elementos);
        
        gm.moveCurrentPlayer(2); // Alice fica presa
        gm.reactToAbyssOrTool();
        
        assertFalse(gm.moveCurrentPlayer(3)); // Não pode mover
        
        Player alice = gm.getPlayers().get(0);
        assertEquals(3, alice.getPosicao()); // Ainda na mesma posição
    }

    @Test
    void test_15_BounceBackWhenExceedingBoard() {
        gm.createInitialBoard(PLAYER_INFO, WORLD_SIZE, null);
        
        Player alice = gm.getPlayers().get(0);
        alice.setPosicao(28);
        
        gm.moveCurrentPlayer(5); // 28 + 5 = 33, excede 30
        
        assertEquals(27, alice.getPosicao()); // 30 - (33-30) = 27
    }

    // ========== TESTES DE GAME OVER ==========

    @Test
    void test_16_GameOverWhenPlayerReachesEnd() {
        gm.createInitialBoard(PLAYER_INFO, WORLD_SIZE, null);
        
        Player alice = gm.getPlayers().get(0);
        alice.setPosicao(29);
        
        gm.moveCurrentPlayer(1);
        
        assertTrue(gm.gameIsOver());
    }

    @Test
    void test_17_GameOverWhenOnlyOnePlayerLeft() {
        String[][] elementos = {
                {"3", "A", "7"}, // BSOD
                {"5", "A", "7"}  // BSOD
        };
        gm.createInitialBoard(PLAYER_INFO, WORLD_SIZE, elementos);
        
        // Alice cai em BSOD
        gm.moveCurrentPlayer(2);
        gm.reactToAbyssOrTool();
        
        // Bob cai em BSOD
        gm.moveCurrentPlayer(3);
        gm.reactToAbyssOrTool();
        
        assertTrue(gm.gameIsOver()); // Só resta Charlie
    }

    // ========== TESTES DE SAVE/LOAD ==========

    @Test
    void test_18_SaveAndLoadGame() throws Exception {
        String[][] elementos = {
                {"5", "A", "0"},
                {"10", "T", "0"}
        };
        gm.createInitialBoard(PLAYER_INFO, WORLD_SIZE, elementos);
        
        gm.moveCurrentPlayer(2);
        gm.reactToAbyssOrTool();
        
        File tempFile = File.createTempFile("test_save", ".txt");
        assertTrue(gm.saveGame(tempFile));
        
        GameManager gm2 = new GameManager();
        gm2.loadGame(tempFile);
        
        assertEquals(gm.getTurnCounter(), gm2.getTurnCounter());
        assertEquals(gm.getPlayers().size(), gm2.getPlayers().size());
        assertEquals(gm.getBoard().getTamanhoTabuleiro(), gm2.getBoard().getTamanhoTabuleiro());
        
        tempFile.delete();
    }

    // ========== TESTES DE getSlotInfo ==========

    @Test
    void test_19_GetSlotInfoWithAbyss() {
        String[][] elementos = {{"5", "A", "0"}};
        gm.createInitialBoard(PLAYER_INFO, WORLD_SIZE, elementos);
        
        String[] info = gm.getSlotInfo(5);
        assertNotNull(info);
        assertEquals(3, info.length);
        assertEquals("A:0", info[2]); // Abismo id 0
    }

    @Test
    void test_20_GetSlotInfoWithTool() {
        String[][] elementos = {{"5", "T", "2"}};
        gm.createInitialBoard(PLAYER_INFO, WORLD_SIZE, elementos);
        
        String[] info = gm.getSlotInfo(5);
        assertNotNull(info);
        assertEquals("T:2", info[2]); // Ferramenta id 2
    }

    @Test
    void test_21_GetSlotInfoWithPlayers() {
        gm.createInitialBoard(PLAYER_INFO, WORLD_SIZE, null);
        
        String[] info = gm.getSlotInfo(1);
        assertNotNull(info);
        assertTrue(info[0].contains("1")); // Alice
        assertTrue(info[0].contains("2")); // Bob
        assertTrue(info[0].contains("3")); // Charlie
    }

    // ========== TESTES DE SEGMENTATION FAULT ==========

    @Test
    void test_22_SegmentationFaultAffectsAllPlayersInPosition() {
        String[][] elementos = {{"5", "A", "9"}};
        gm.createInitialBoard(PLAYER_INFO, WORLD_SIZE, elementos);
        
        // Colocar todos na posição 5
        gm.getPlayers().get(0).setPosicao(5);
        gm.getPlayers().get(1).setPosicao(5);
        gm.getPlayers().get(2).setPosicao(5);
        
        gm.reactToAbyssOrTool();
        
        // Todos devem ter recuado 3 casas
        assertEquals(2, gm.getPlayers().get(0).getPosicao());
        assertEquals(2, gm.getPlayers().get(1).getPosicao());
        assertEquals(2, gm.getPlayers().get(2).getPosicao());
    }

    // ========== TESTE DE DUPLICATED CODE ==========

    @Test
    void test_23_DuplicatedCodeReturnsToPreviousPosition() {
        String[][] elementos = {{"7", "A", "5"}};
        gm.createInitialBoard(PLAYER_INFO, WORLD_SIZE, elementos);
        
        gm.moveCurrentPlayer(3); // Alice vai para 4
        gm.reactToAbyssOrTool();
        gm.moveCurrentPlayer(3); // Alice vai para 7
        gm.reactToAbyssOrTool();
        
        Player alice = gm.getPlayers().get(0);
        assertEquals(4, alice.getPosicao()); // Voltou para posição anterior
    }
}
