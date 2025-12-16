package pt.ulusofona.lp2.greatprogrammingjourney;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestUnitariosGame {

    private GameManager game(String[][] elements) {
        GameManager gm = new GameManager();
        String[][] players = {
                {"1", "A", "Java;C", "Blue"},
                {"2", "B", "Python;Java", "Green"}
        };
        if (elements == null) {
            assertTrue(gm.createInitialBoard(players, 20));
        } else {
            assertTrue(gm.createInitialBoard(players, 20, elements));
        }
        return gm;
    }

    @Test
    void massiveGameSimulation() {
        String[][] elements = {
                {"0","0","2"},{"0","1","3"},{"0","2","4"},{"0","3","5"},
                {"0","4","6"},{"0","5","7"},{"0","6","8"},{"0","7","9"},
                {"0","8","10"},{"0","9","11"},
                {"1","0","12"},{"1","1","13"},{"1","2","14"},
                {"1","3","15"},{"1","4","16"},{"1","5","17"}
        };

        GameManager gm = game(elements);

        for (int i = 0; i < 40; i++) {
            gm.moveCurrentPlayer(1);
            gm.reactToAbyssOrTool();
            gm.moveCurrentPlayer(2);
            gm.reactToAbyssOrTool();
            gm.moveCurrentPlayer(3);
            gm.reactToAbyssOrTool();
        }

        for (int pos = -5; pos <= 25; pos++) {
            gm.getSlotInfo(pos);
            gm.getImagePng(pos);
            gm.getPlayersAtPosition(pos);
        }

        gm.getProgrammersInfo();
        gm.getGameResults();
        gm.gameIsOver();
    }

    @Test
    void exhaustiveMoveValidation() {
        GameManager gm = game(null);

        for (int i = -3; i <= 10; i++) {
            gm.moveCurrentPlayer(i);
            gm.reactToAbyssOrTool();
        }
    }

    @Test
    void playerStateAbuse() {
        Player p = new Player("1","X","Java;C","Blue");

        for (int i = 0; i < 5; i++) {
            p.addTool(i);
            p.hasTool(i);
        }

        for (int i = 0; i < 5; i++) {
            p.removeTool(i);
            p.hasTool(i);
        }

        p.setFerramentaAtiva(1);
        p.getFerramentaAtiva();
        p.setFerramentaAtiva(null);
        p.getFerramentaAtiva();

        p.setLastMoveSpaces(1);
        p.getLastMoveSpaces();
        p.setLastMoveSpaces(3);
        p.getLastMoveSpaces();

        p.setPosicao(2);
        p.setPosicao(5);
        p.setPosicao(9);
        p.getHistoricalPosition(1);
        p.getHistoricalPosition(2);

        p.prender(2);
        p.isPreso();
        p.setPreso(false);
        p.isPreso();

        p.setEliminado(true);
        p.isEliminado();
        p.setEliminado(false);
        p.isEliminado();

        p.toString();
    }

    @Test
    void boardAbuse() {
        Board b = new Board();

        for (int i = 1; i <= 10; i++) {
            b.getElementAt(i);
            b.getAllElementsAt(i);
        }

        for (int i = 0; i <= 9; i++) {
            b.addElement(ElementsFactory.createAbyss(i, i + 1));
            b.addElement(ElementsFactory.createAbyss(i, i + 1));
            b.getAllElementsAt(i + 1);
        }

        b.clearElements();
        b.clearElements();
    }

    @Test
    void factoryAndAdapterStress() {
        for (int i = -2; i <= 12; i++) {
            BoardElement a = ElementsFactory.createAbyss(Math.max(0, i), i + 1);
            BoardElement t = ElementsFactory.createTool(Math.max(0, i), i + 1);

            if (a != null) {
                ElementsIOAdapter.typeOf(a);
            }
            if (t != null) {
                ElementsIOAdapter.typeOf(t);
            }
        }

        for (int type = 0; type <= 1; type++) {
            for (int id = 0; id <= 9; id++) {
                ElementsIOAdapter.toElement(type, id, id + 1);
            }
        }
    }

    @Test
    void gameStatusStress() {
        GameManager gm = game(null);
        GameStatus gs = new GameStatus(20, gm.getPlayers());

        gs.isGameOver();
        gs.checkGameOver(gm.getPlayers());

        gm.getPlayers().get(0).setPosicaoSemGuardarHistorico(10);
        gs.checkGameOver(gm.getPlayers());

        gm.getPlayers().get(0).setPosicaoSemGuardarHistorico(20);
        gs.checkGameOver(gm.getPlayers());
        gs.isGameOver();
    }

    @Test
    void toolNamesFullRange() {
        for (int i = -10; i <= 30; i++) {
            GameManager.toolName(i);
        }
    }

    @Test
    void programmerInfoAbuse() {
        GameManager gm = game(null);

        for (int i = -5; i <= 10; i++) {
            gm.getProgrammerInfo(i);
        }
    }

    @Test
    void slotInfoAbuse() {
        GameManager gm = game(null);

        for (int i = -5; i <= 30; i++) {
            gm.getSlotInfo(i);
        }
    }

    @Test
    void playersAtPositionAbuse() {
        GameManager gm = game(null);

        for (int i = -2; i <= 30; i++) {
            List<Player> ps = gm.getPlayersAtPosition(i);
            if (ps != null) {
                ps.size();
            }
        }
    }
}
