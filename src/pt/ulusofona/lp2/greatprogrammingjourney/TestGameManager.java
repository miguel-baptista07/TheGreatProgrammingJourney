package pt.ulusofona.lp2.greatprogrammingjourney;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileNotFoundException;
import static org.junit.jupiter.api.Assertions.*;

class TestGameManager {
    private GameManager gm;

    @BeforeEach
    void setUp() {
        gm = new GameManager();
    }


    @Test
    void testSyntaxErrorReturnsToPreviousPosition() {
        String[][] players = {
                {"1", "Bruninho", "Java", "Blue"},
                {"2", "Raquelita", "C#", "Green"}
        };
        String[][] elements = {
                {"0", "0", "3"}
        };

        assertTrue(gm.createInitialBoard(players, 10, elements));


        gm.moveCurrentPlayer(2);
        gm.reactToAbyssOrTool();


        String[] info = gm.getProgrammerInfo(1);
        assertEquals("1", info[4], "Jogador deve voltar à posição anterior ao movimento");
    }


    @Test
    void testToolProtectsAgainstAbyss() {
        String[][] players = {
                {"1", "Bruninho", "Java", "Blue"},
                {"2", "Raquelita", "C#", "Green"}
        };
        String[][] elements = {
                {"1", "4", "2"},
                {"0", "0", "4"}
        };

        assertTrue(gm.createInitialBoard(players, 10, elements));


        gm.moveCurrentPlayer(1);
        gm.reactToAbyssOrTool();

        String info1 = gm.getProgrammerInfoAsStr(1);
        assertTrue(info1.contains("IDE"), "Jogador deve ter ferramenta IDE ativa");


        gm.moveCurrentPlayer(2);
        gm.reactToAbyssOrTool();


        String[] info = gm.getProgrammerInfo(1);
        assertEquals("4", info[4], "Ferramenta deve neutralizar o abismo");
    }


    @Test
    void testAssemblyMovementRestrictions() {
        String[][] players = {
                {"1", "Assembly Dev", "Assembly", "Blue"},
                {"2", "Normal Dev", "Java", "Green"}
        };

        assertTrue(gm.createInitialBoard(players, 20));

        assertFalse(gm.moveCurrentPlayer(5), "Jogador Assembly não deve poder mover 5 casas");


        assertFalse(gm.moveCurrentPlayer(6), "Jogador Assembly não deve poder mover 6 casas");


        assertTrue(gm.moveCurrentPlayer(4), "Jogador Assembly deve poder mover 4 casas");
    }


    @Test
    void testBSODEliminatesPlayer() {
        String[][] players = {
                {"1", "Bruninho", "Java", "Blue"},
                {"2", "Raquelita", "C#", "Green"}
        };
        String[][] elements = {
                {"0", "7", "3"}
        };

        assertTrue(gm.createInitialBoard(players, 10, elements));

        gm.moveCurrentPlayer(2);
        gm.reactToAbyssOrTool();


        String info = gm.getProgrammerInfoAsStr(1);
        assertNotNull(info, "getProgrammerInfoAsStr deve retornar info mesmo para jogador eliminado");
        assertTrue(info.contains("Derrotado"), "Jogador eliminado deve ter estado 'Derrotado'");
    }

    @Test
    void testSaveAndLoadGame() throws FileNotFoundException, InvalidFileException {
        String[][] players = {
                {"1", "Bruninho", "Java", "Blue"},
                {"2", "Raquelita", "C#", "Green"}
        };
        String[][] elements = {
                {"0", "0", "3"},
                {"1", "4", "5"}
        };

        assertTrue(gm.createInitialBoard(players, 10, elements));


        gm.moveCurrentPlayer(2);
        gm.reactToAbyssOrTool();
        gm.moveCurrentPlayer(3);
        gm.reactToAbyssOrTool();

        File tempFile = new File("test_save.txt");
        assertTrue(gm.saveGame(tempFile), "Deve conseguir gravar o jogo");


        GameManager gm2 = new GameManager();
        gm2.loadGame(tempFile);

        assertNotNull(gm2.getProgrammerInfo(1), "Jogador 1 deve existir após carregar");
        assertNotNull(gm2.getProgrammerInfo(2), "Jogador 2 deve existir após carregar");

        String[] slot3 = gm2.getSlotInfo(3);
        assertEquals("A:0", slot3[2], "Abismo deve estar na posição 3 após carregar");


        tempFile.delete();
    }


    @Test
    void testMultipleToolsCollection() {
        String[][] players = {
                {"1", "Bruninho", "Java", "Blue"},
                {"2", "Raquelita", "C#", "Green"}
        };
        String[][] elements = {
                {"1", "1", "2"},
                {"1", "4", "4"},
        };

        assertTrue(gm.createInitialBoard(players, 10, elements));


        gm.moveCurrentPlayer(1);
        gm.reactToAbyssOrTool();

        String info1 = gm.getProgrammersInfo();
        assertTrue(info1.contains("Programação Funcional"), "Primeira ferramenta deve estar ativa");


        gm.moveCurrentPlayer(2);
        gm.reactToAbyssOrTool();

        String info2 = gm.getProgrammersInfo();
        assertTrue(info2.contains("IDE"), "Segunda ferramenta deve estar ativa");
    }


    @Test
    void testInfiniteLoopFreezesPlayer() {
        String[][] players = {
                {"1", "Bruninho", "Java", "Blue"},
                {"2", "Raquelita", "C#", "Green"}
        };
        String[][] elements = {
                {"0", "8", "3"}
        };

        assertTrue(gm.createInitialBoard(players, 10, elements));

        gm.moveCurrentPlayer(2);
        gm.reactToAbyssOrTool();

        String info = gm.getProgrammerInfoAsStr(1);
        assertTrue(info.contains("Preso"), "Jogador deve ficar preso após Infinite Loop");


        assertFalse(gm.moveCurrentPlayer(2), "Jogador preso não deve poder mover");
    }


    @Test
    void testGetSlotInfoWithoutPlayers() {
        String[][] players = {
                {"1", "Bruninho", "Java", "Blue"},
                {"2", "Raquelita", "C#", "Green"}
        };
        String[][] elements = {
                {"0", "1", "5"}
        };

        assertTrue(gm.createInitialBoard(players, 10, elements));

        String[] slotInfo = gm.getSlotInfo(5);
        assertNotNull(slotInfo, "getSlotInfo não deve retornar null");
        assertEquals("", slotInfo[0], "Não deve haver jogadores na posição");
        assertEquals("A:1", slotInfo[2], "Deve reportar o abismo mesmo sem jogadores");
    }
}