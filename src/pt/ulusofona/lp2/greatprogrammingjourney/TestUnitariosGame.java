package pt.ulusofona.lp2.greatprogrammingjourney;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestUnitariosGame {

    private GameManager gameWithElements(String[][] elements) {
        GameManager gm = new GameManager();
        String[][] players = {
                {"1", "A", "Java", "Blue"},
                {"2", "B", "Java", "Green"}
        };
        assertTrue(gm.createInitialBoard(players, 10, elements));
        return gm;
    }

    private GameManager gameNoElements() {
        GameManager gm = new GameManager();
        String[][] players = {
                {"1", "A", "Java", "Blue"},
                {"2", "B", "Java", "Green"}
        };
        assertTrue(gm.createInitialBoard(players, 20));
        return gm;
    }

    @Test
    void testCreateGameValid() {
        assertNotNull(gameNoElements());
    }

    @Test
    void testInitialPositions() {
        GameManager gm = gameNoElements();
        for (Player p : gm.getPlayers()) {
            assertEquals(1, p.getPosicao());
        }
    }

    @Test
    void testValidMove() {
        GameManager gm = gameNoElements();
        assertTrue(gm.moveCurrentPlayer(2));
    }

    @Test
    void testInvalidMoveTooHigh() {
        GameManager gm = gameNoElements();
        assertFalse(gm.moveCurrentPlayer(7));
    }

    @Test
    void testTurnChangesAfterMove() {
        GameManager gm = gameNoElements();
        int first = gm.getCurrentPlayerID();
        gm.moveCurrentPlayer(1);
        gm.reactToAbyssOrTool();
        int second = gm.getCurrentPlayerID();
        assertNotEquals(first, second);
    }

    @Test
    void testToolPickupInheritance() {
        GameManager gm = gameWithElements(new String[][]{{"1", "0", "2"}});
        gm.moveCurrentPlayer(1);
        gm.reactToAbyssOrTool();
        Player p = gm.getPlayers().get(0);
        assertTrue(p.hasTool(0));
    }

    @Test
    void testSyntaxAbyssMovesBack() {
        GameManager gm = gameWithElements(new String[][]{{"0", "0", "2"}});
        gm.moveCurrentPlayer(1);
        gm.reactToAbyssOrTool();
        assertEquals(1, gm.getPlayers().get(0).getPosicao());
    }

    @Test
    void testBSODEliminatesPlayer() {
        GameManager gm = gameWithElements(new String[][]{{"0", "7", "2"}});
        gm.moveCurrentPlayer(1);
        gm.reactToAbyssOrTool();
        assertTrue(gm.getPlayers().get(0).isEliminado());
    }

    @Test
    void testPrisonApplies() {
        GameManager gm = gameWithElements(new String[][]{{"0", "8", "2"}});
        gm.moveCurrentPlayer(1);
        gm.reactToAbyssOrTool();
        assertTrue(gm.getPlayers().get(0).isPreso());
    }

    @Test
    void testSegmentationFaultTwoPlayers() {
        GameManager gm = gameWithElements(new String[][]{{"0", "9", "2"}});
        gm.moveCurrentPlayer(1);
        gm.reactToAbyssOrTool();
        List<Player> ps = gm.getPlayers();
        assertTrue(ps.get(0).getPosicao() <= 1 || ps.get(1).getPosicao() <= 1);
    }

    @Test
    void testCounterToolPreventsAbyssEffect() {
        GameManager gm = gameWithElements(new String[][]{
                {"1", "4", "1"},
                {"0", "0", "2"}
        });
        gm.reactToAbyssOrTool();
        gm.moveCurrentPlayer(1);
        int posBefore = gm.getPlayers().get(0).getPosicao();
        gm.reactToAbyssOrTool();
        int posAfter = gm.getPlayers().get(0).getPosicao();
        assertEquals(posBefore, posAfter);
    }

    @Test
    void testFactoryCreatesCorrectTypes() {
        assertTrue(ElementsFactory.createAbyss(0, 1).isAbyss());
        assertFalse(ElementsFactory.createTool(0, 1).isAbyss());
    }

    @Test
    void testAdapterTypeOf() {
        assertEquals(0, ElementsIOAdapter.typeOf(new AbyssErroDeSintaxe(1)));
        assertEquals(1, ElementsIOAdapter.typeOf(new ToolInheritance(1)));
    }

    @Test
    void testPlayerToolAddRemove() {
        Player p = new Player("1", "A", "Java", "Blue");
        p.addTool(1);
        assertTrue(p.hasTool(1));
        p.removeTool(1);
        assertFalse(p.hasTool(1));
    }

    @Test
    void testPlayerHistory() {
        Player p = new Player("1", "A", "Java", "Blue");
        p.setPosicao(3);
        p.setPosicao(6);
        assertEquals(3, p.getHistoricalPosition(1));
    }

    @Test
    void testSaveLoadGame() throws Exception {
        GameManager gm = gameNoElements();
        File f = File.createTempFile("game", ".txt");
        assertTrue(gm.saveGame(f));
        GameManager gm2 = new GameManager();
        gm2.loadGame(f);
        assertEquals(gm.getPlayers().size(), gm2.getPlayers().size());
    }

    @Test
    void testReportNotEmpty() {
        assertFalse(gameNoElements().getGameResults().isEmpty());
    }

    @Test
    void testToolName() {
        assertEquals("Herança", GameManager.toolName(0));
        assertEquals("Desconhecida", GameManager.toolName(99));
    }

    @Test
    void testFinalSquareImage() {
        assertEquals("glory.png", gameNoElements().getImagePng(20));
    }

    @Test
    void testGameOverByPosition() {
        GameManager gm = gameNoElements();
        gm.getPlayers().get(0).setPosicaoSemGuardarHistorico(20);
        assertTrue(gm.gameIsOver());
    }

    @Test
    void testPlayersAtSamePosition() {
        assertEquals(2, gameNoElements().getPlayersAtPosition(1).size());
    }

    @Test
    void testPlayerHasLanguage() {
        assertTrue(new Player("1", "A", "Java;C", "Blue").hasLanguage("Java"));
    }

    @Test
    void testPlayerToString() {
        assertTrue(new Player("1", "A", "Java", "Blue").toString().contains("ID"));
    }

    @Test
    void testBoardAddAndClear() {
        Board b = new Board();
        b.addElement(new AbyssErroDeSintaxe(3));
        assertNotNull(b.getElementAt(3));
        b.clearElements();
        assertNull(b.getElementAt(3));
    }
}
