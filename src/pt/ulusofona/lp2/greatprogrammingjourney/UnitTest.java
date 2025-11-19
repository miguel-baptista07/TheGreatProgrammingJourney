package pt.ulusofona.lp2.greatprogrammingjourney;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnitTest {

    @Test
    void testMovePlayerBasic() {

        GameManager gm = new GameManager();

        String[][] players = {
                {"1", "Alice", "Java;Python", "Blue"},
                {"2", "Bob", "C;Kotlin", "Green"}
        };

        boolean ok = gm.createInitialBoard(players, 20);
        assertTrue(ok, "A criação do tabuleiro deve ser válida");


        assertEquals(1, gm.getCurrentPlayerID());


        assertTrue(gm.moveCurrentPlayer(3));


        gm.reactToAbyssOrTool();


        String[] info = gm.getProgrammerInfo(1);
        assertEquals("4", info[2], "Após mover 3 casas, o jogador deveria estar na posição 4");


        assertEquals(2, gm.getCurrentPlayerID());
    }
}
