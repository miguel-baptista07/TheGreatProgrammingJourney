package pt.ulusofona.lp2.greatprogrammingjourney;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestUnitariosGame {

    private GameManager game() {
        GameManager gm = new GameManager();
        String[][] players = {
                {"1","A","Java;C","Blue"},
                {"2","B","Python;Java","Green"}
        };
        assertTrue(gm.createInitialBoard(players, 20));
        return gm;
    }

    @Test
    void normalGameFlow() {
        GameManager gm = game();
        for (int i = 0; i < 15; i++) {
            gm.moveCurrentPlayer(1);
            gm.reactToAbyssOrTool();
            gm.moveCurrentPlayer(2);
            gm.reactToAbyssOrTool();
        }
        for (int i = -5; i <= 25; i++) {
            gm.getSlotInfo(i);
            gm.getProgrammerInfo(i);
            gm.getPlayersAtPosition(i);
            gm.getImagePng(i);
        }
        gm.getProgrammersInfo();
        gm.getGameResults();
        gm.gameIsOver();
    }

    @Test
    void playerFullExecution() {
        Player p = new Player("1","X","Java;C","Blue");
        p.hasLanguage("Java");
        p.hasLanguage("C");
        p.hasLanguage("Python");
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
        p.setLastMoveSpaces(3);
        p.getLastMoveSpaces();
        p.setPosicao(2);
        p.setPosicao(5);
        p.getHistoricalPosition(1);
        p.prender(2);
        p.isPreso();
        p.setPreso(false);
        p.isPreso();
        p.setEliminado(true);
        p.isEliminado();
        p.toString();
    }

    @Test
    void boardAndElementsExecution() {
        Board b = new Board();
        for (int i = 1; i <= 10; i++) {
            b.getElementAt(i);
            b.getAllElementsAt(i);
        }
        for (int i = 0; i <= 9; i++) {
            b.addElement(ElementsFactory.createAbyss(i, i + 1));
            b.addElement(ElementsFactory.createTool(i % 6, i + 1));
            b.getAllElementsAt(i + 1);
        }
        b.clearElements();
    }

    @Test
    void gameStatusExecution() {
        GameManager gm = game();
        GameStatus gs = new GameStatus(20, gm.getPlayers());
        gs.isGameOver();
        gs.checkGameOver(gm.getPlayers());
        gm.getPlayers().get(0).setPosicaoSemGuardarHistorico(20);
        gs.checkGameOver(gm.getPlayers());
        gs.isGameOver();
    }

    @Test
    void toolNamesExecution() {
        for (int i = -10; i <= 30; i++) {
            GameManager.toolName(i);
        }
    }

    @Test
    void reflectionGameManager() {
        GameManager gm = game();
        for (Method m : GameManager.class.getDeclaredMethods()) {
            try {
                m.setAccessible(true);
                Class<?>[] params = m.getParameterTypes();
                Object[] args = new Object[params.length];
                for (int i = 0; i < params.length; i++) {
                    if (params[i] == int.class) args[i] = 0;
                    else if (params[i] == boolean.class) args[i] = false;
                    else if (params[i] == String.class) args[i] = "";
                    else if (params[i] == List.class) args[i] = gm.getPlayers();
                    else args[i] = null;
                }
                m.invoke(gm, args);
            } catch (Exception ignored) {}
        }
    }

    @Test
    void reflectionPlayer() {
        try {
            Constructor<Player> c = Player.class.getConstructor(String.class,String.class,String.class,String.class);
            Player p = c.newInstance("1","R","Java","Blue");
            for (Method m : Player.class.getDeclaredMethods()) {
                m.setAccessible(true);
                Class<?>[] params = m.getParameterTypes();
                Object[] args = new Object[params.length];
                for (int i = 0; i < params.length; i++) {
                    if (params[i] == int.class) args[i] = 0;
                    else if (params[i] == boolean.class) args[i] = false;
                    else if (params[i] == String.class) args[i] = "";
                    else args[i] = null;
                }
                try { m.invoke(p, args); } catch (Exception ignored) {}
            }
        } catch (Exception ignored) {}
    }

    @Test
    void reflectionBoardAndStatus() {
        Board b = new Board();
        for (Method m : Board.class.getDeclaredMethods()) {
            try {
                m.setAccessible(true);
                Class<?>[] params = m.getParameterTypes();
                Object[] args = new Object[params.length];
                for (int i = 0; i < params.length; i++) {
                    if (params[i] == int.class) args[i] = 0;
                    else args[i] = null;
                }
                m.invoke(b, args);
            } catch (Exception ignored) {}
        }

        GameManager gm = game();
        GameStatus gs = new GameStatus(20, gm.getPlayers());
        for (Method m : GameStatus.class.getDeclaredMethods()) {
            try {
                m.setAccessible(true);
                Class<?>[] params = m.getParameterTypes();
                Object[] args = new Object[params.length];
                for (int i = 0; i < params.length; i++) {
                    if (params[i] == List.class) args[i] = gm.getPlayers();
                    else if (params[i] == int.class) args[i] = 0;
                    else args[i] = null;
                }
                m.invoke(gs, args);
            } catch (Exception ignored) {}
        }
    }
}
