package pt.ulusofona.lp2.greatprogrammingjourney;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestUnitariosGame {

    private GameManager baseGame() {
        GameManager gm = new GameManager();
        String[][] players = {
                {"1", "Ana", "Java;C", "Blue"},
                {"2", "Bruno", "Python;Java", "Green"}
        };
        assertTrue(gm.createInitialBoard(players, 20));
        return gm;
    }

    private GameManager gameWithElements(String[][] elements) {
        GameManager gm = new GameManager();
        String[][] players = {
                {"1", "Ana", "Java;C", "Blue"},
                {"2", "Bruno", "Python;Java", "Green"}
        };
        assertTrue(gm.createInitialBoard(players, 20, elements));
        return gm;
    }

    @Test void t001_createGame() { assertNotNull(baseGame()); }
    @Test void t002_playersSize() { assertEquals(2, baseGame().getPlayers().size()); }
    @Test void t003_turnCounter() { assertTrue(baseGame().getTurnCounter() >= 1); }
    @Test void t004_currentPlayerId() { assertTrue(baseGame().getCurrentPlayerID() >= 0); }
    @Test void t005_gameNotOverInitially() { assertFalse(baseGame().gameIsOver()); }

    @Test void t006_validMove1() { assertTrue(baseGame().moveCurrentPlayer(1)); }
    @Test void t007_validMove2() { assertTrue(baseGame().moveCurrentPlayer(2)); }
    @Test void t008_invalidMoveHigh() { assertFalse(baseGame().moveCurrentPlayer(7)); }
    @Test void t009_invalidMoveZero() { assertFalse(baseGame().moveCurrentPlayer(0)); }

    @Test void t010_turnChanges() {
        GameManager gm = baseGame();
        int id = gm.getCurrentPlayerID();
        gm.moveCurrentPlayer(1);
        gm.reactToAbyssOrTool();
        assertNotEquals(id, gm.getCurrentPlayerID());
    }

    @Test void t020_slotInfoValid() { assertNotNull(baseGame().getSlotInfo(1)); }
    @Test void t021_slotInfoInvalidLow() { assertNull(baseGame().getSlotInfo(-1)); }
    @Test void t022_slotInfoInvalidHigh() { assertNull(baseGame().getSlotInfo(999)); }

    @Test void t023_programmerInfoValid() { assertNotNull(baseGame().getProgrammerInfo(1)); }
    @Test void t024_programmerInfoInvalid() { assertNull(baseGame().getProgrammerInfo(999)); }


    @Test void t030_imageInvalidLow() { assertNull(baseGame().getImagePng(-1)); }
    @Test void t031_imageEmptySlot() { assertNull(baseGame().getImagePng(1)); }
    @Test void t032_imageFinal() { assertEquals("glory.png", baseGame().getImagePng(20)); }

    @Test void t033_toolName0() { assertNotNull(GameManager.toolName(0)); }
    @Test void t034_toolName1() { assertNotNull(GameManager.toolName(1)); }
    @Test void t035_toolName2() { assertNotNull(GameManager.toolName(2)); }
    @Test void t036_toolName3() { assertNotNull(GameManager.toolName(3)); }
    @Test void t037_toolName4() { assertNotNull(GameManager.toolName(4)); }
    @Test void t038_toolName5() { assertNotNull(GameManager.toolName(5)); }
    @Test void t039_toolNameInvalid() { assertNotNull(GameManager.toolName(999)); }
    @Test void t040_toolNameNegative() { assertNotNull(GameManager.toolName(-5)); }

    @Test void t050_toolPickupInheritance() {
        GameManager gm = gameWithElements(new String[][]{{"1", "0", "2"}});
        gm.moveCurrentPlayer(1);
        gm.reactToAbyssOrTool();
        assertTrue(gm.getPlayers().get(0).hasTool(0));
    }

    @Test void t060_syntaxAbyss() {
        GameManager gm = gameWithElements(new String[][]{{"0", "0", "2"}});
        gm.moveCurrentPlayer(1);
        gm.reactToAbyssOrTool();
        assertEquals(1, gm.getPlayers().get(0).getPosicao());
    }

    @Test void t067_bsodAbyss() {
        GameManager gm = gameWithElements(new String[][]{{"0", "7", "2"}});
        gm.moveCurrentPlayer(1);
        gm.reactToAbyssOrTool();
        assertTrue(gm.getPlayers().get(0).isEliminado());
    }

    @Test void t068_prisonAbyss() {
        GameManager gm = gameWithElements(new String[][]{{"0", "8", "2"}});
        gm.moveCurrentPlayer(1);
        gm.reactToAbyssOrTool();
        assertTrue(gm.getPlayers().get(0).isPreso());
    }

    @Test void t069_segmentationFault() {
        GameManager gm = gameWithElements(new String[][]{{"0", "9", "2"}});
        gm.moveCurrentPlayer(1);
        gm.reactToAbyssOrTool();
        assertTrue(
                gm.getPlayers().get(0).getPosicao() <= 1 ||
                        gm.getPlayers().get(1).getPosicao() <= 1
        );
    }

    @Test void t080_playerBasics() {
        Player p = new Player("1", "X", "Java;C", "Blue");
        assertEquals("1", p.getId());
        assertEquals("X", p.getNome());
        assertEquals("Blue", p.getCor());
    }

    @Test void t081_playerLanguages() {
        Player p = new Player("1", "X", "Java;C", "Blue");
        assertTrue(p.hasLanguage("java"));
        assertTrue(p.hasLanguage("C"));
        assertFalse(p.hasLanguage("Python"));
    }

    @Test void t082_playerToolsFalse() {
        Player p = new Player("1", "X", "Java", "Blue");
        assertFalse(p.hasTool(99));
    }

    @Test void t083_playerPrenderLibertar() {
        Player p = new Player("1", "X", "Java", "Blue");
        p.prender(2);
        assertTrue(p.isPreso());
        p.setPreso(false);
        assertFalse(p.isPreso());
    }

    @Test void t084_playerFerramentaAtivaNull() {
        Player p = new Player("1", "X", "Java", "Blue");
        p.setFerramentaAtiva(null);
        assertNull(p.getFerramentaAtiva());
    }

    @Test void t090_boardEmpty() {
        Board b = new Board();
        assertNull(b.getElementAt(1));
        assertTrue(b.getAllElementsAt(1).isEmpty());
    }

    @Test void t091_boardMultipleElementsSamePos() {
        Board b = new Board();
        b.addElement(new AbyssErroDeSintaxe(2));
        b.addElement(new AbyssErroDeSintaxe(2));
        assertEquals(2, b.getAllElementsAt(2).size());
    }

    @Test void t092_boardClearTwice() {
        Board b = new Board();
        b.clearElements();
        b.clearElements();
        assertNull(b.getElementAt(1));
    }

    @Test void t100_factoryMultipleAbyss() {
        for (int i = 0; i <= 9; i++) {
            assertNotNull(ElementsFactory.createAbyss(i, 1));
        }
    }

    @Test void t101_factoryMultipleTools() {
        for (int i = 0; i <= 5; i++) {
            assertNotNull(ElementsFactory.createTool(i, 1));
        }
    }

    @Test void t102_adapterTypeFallback() {
        BoardElement e = ElementsFactory.createAbyss(0, 1);
        assertTrue(ElementsIOAdapter.typeOf(e) >= 0);
    }

    @Test void t110_gameStatusFlow() {
        GameManager gm = baseGame();
        GameStatus gs = new GameStatus(20, gm.getPlayers());
        assertFalse(gs.isGameOver());
        gm.getPlayers().get(0).setPosicaoSemGuardarHistorico(20);
        assertTrue(gs.checkGameOver(gm.getPlayers()));
        assertTrue(gs.isGameOver());
    }

    @Test void t120_playersAtPosition() {
        GameManager gm = baseGame();
        List<Player> ps = gm.getPlayersAtPosition(1);
        assertEquals(2, ps.size());
    }
}
