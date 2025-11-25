package pt.ulusofona.lp2.greatprogrammingjourney;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class DiagnosticTest {

    private GameManager gameManager;

    @BeforeEach
    void setUp() {
        gameManager = new GameManager();
    }

    @Test
    @DisplayName("DIAGNÓSTICO COMPLETO - Passo a passo")
    void fullDiagnostic() {
        System.out.println("\n=== INICIANDO DIAGNÓSTICO ===\n");

        // Passo 1: Criar jogador
        String[][] players = {
                {"1", "Alice", "Python", "Blue"}
        };

        System.out.println("Passo 1: Criando tabuleiro...");
        boolean created = gameManager.createInitialBoard(players, 20);
        System.out.println("Resultado: " + created);
        assertTrue(created, "Tabuleiro deve ser criado com sucesso");

        // Passo 2: Verificar informações do jogador
        System.out.println("\nPasso 2: Verificando informações do jogador...");
        String[] info = gameManager.getProgrammerInfo(1);
        assertNotNull(info, "Informações do jogador não devem ser null");
        System.out.println("ID: " + info[0]);
        System.out.println("Nome: " + info[1]);
        System.out.println("Linguagens: " + info[2]);
        System.out.println("Cor: " + info[3]);
        System.out.println("Posição: " + info[4]);

        // Passo 3: Verificar jogador atual
        System.out.println("\nPasso 3: Verificando jogador atual...");
        int currentId = gameManager.getCurrentPlayerID();
        System.out.println("ID do jogador atual: " + currentId);
        assertEquals(1, currentId, "Jogador atual deve ser 1");

        // Passo 4: Testar movimentos inválidos
        System.out.println("\nPasso 4: Testando movimentos inválidos...");

        boolean move0 = gameManager.moveCurrentPlayer(0);
        System.out.println("moveCurrentPlayer(0) = " + move0 + " (esperado: false)");
        assertFalse(move0, "Movimento de 0 casas deve ser inválido");

        boolean move7 = gameManager.moveCurrentPlayer(7);
        System.out.println("moveCurrentPlayer(7) = " + move7 + " (esperado: false)");
        assertFalse(move7, "Movimento de 7 casas deve ser inválido");

        boolean moveNeg = gameManager.moveCurrentPlayer(-1);
        System.out.println("moveCurrentPlayer(-1) = " + moveNeg + " (esperado: false)");
        assertFalse(moveNeg, "Movimento negativo deve ser inválido");

        // Passo 5: Testar movimento válido de 1 casa
        System.out.println("\nPasso 5: Testando movimento de 1 casa...");
        String[] infoBefore1 = gameManager.getProgrammerInfo(1);
        System.out.println("Posição antes: " + infoBefore1[4]);

        boolean move1 = gameManager.moveCurrentPlayer(1);
        System.out.println("moveCurrentPlayer(1) = " + move1 + " (esperado: true)");

        String[] infoAfter1 = gameManager.getProgrammerInfo(1);
        System.out.println("Posição depois: " + infoAfter1[4]);

        assertTrue(move1, "Python deve conseguir mover 1 casa");
        assertEquals("2", infoAfter1[4], "Deve estar na posição 2");

        // Passo 6: Completar turno
        System.out.println("\nPasso 6: Completando turno...");
        String reaction = gameManager.reactToAbyssOrTool();
        System.out.println("Reação: " + (reaction == null ? "null (sem abismo/ferramenta)" : reaction));

        // Passo 7: Testar movimento de 6 casas
        System.out.println("\nPasso 7: Testando movimento de 6 casas...");
        String[] infoBefore6 = gameManager.getProgrammerInfo(1);
        System.out.println("Posição antes: " + infoBefore6[4]);

        boolean move6 = gameManager.moveCurrentPlayer(6);
        System.out.println("moveCurrentPlayer(6) = " + move6 + " (esperado: true)");

        String[] infoAfter6 = gameManager.getProgrammerInfo(1);
        System.out.println("Posição depois: " + infoAfter6[4]);

        assertTrue(move6, "Python deve conseguir mover 6 casas");
        assertEquals("8", infoAfter6[4], "Deve estar na posição 8 (2 + 6)");

        System.out.println("\n=== DIAGNÓSTICO CONCLUÍDO ===\n");
    }

    @Test
    @DisplayName("DIAGNÓSTICO - Testar restrições Assembly")
    void testAssemblyRestrictions() {
        System.out.println("\n=== TESTANDO ASSEMBLY ===\n");

        String[][] players = {
                {"1", "Bob", "Assembly", "Green"}
        };

        assertTrue(gameManager.createInitialBoard(players, 20));

        System.out.println("Testando movimentos para Assembly:");
        System.out.println("Assembly pode mover no máximo 2 casas");

        boolean move1 = gameManager.moveCurrentPlayer(1);
        System.out.println("moveCurrentPlayer(1) = " + move1 + " (esperado: true)");
        assertTrue(move1, "Assembly deve conseguir mover 1 casa");
        gameManager.reactToAbyssOrTool();

        boolean move2 = gameManager.moveCurrentPlayer(2);
        System.out.println("moveCurrentPlayer(2) = " + move2 + " (esperado: true)");
        assertTrue(move2, "Assembly deve conseguir mover 2 casas");
        gameManager.reactToAbyssOrTool();

        boolean move3 = gameManager.moveCurrentPlayer(3);
        System.out.println("moveCurrentPlayer(3) = " + move3 + " (esperado: false)");
        assertFalse(move3, "Assembly NÃO deve conseguir mover 3 casas");
    }

    @Test
    @DisplayName("DIAGNÓSTICO - Testar restrições C")
    void testCRestrictions() {
        System.out.println("\n=== TESTANDO C ===\n");

        String[][] players = {
                {"1", "Charlie", "C", "Brown"}
        };

        assertTrue(gameManager.createInitialBoard(players, 20));

        System.out.println("Testando movimentos para C:");
        System.out.println("C pode mover no máximo 3 casas");

        boolean move3 = gameManager.moveCurrentPlayer(3);
        System.out.println("moveCurrentPlayer(3) = " + move3 + " (esperado: true)");
        assertTrue(move3, "C deve conseguir mover 3 casas");
        gameManager.reactToAbyssOrTool();

        boolean move4 = gameManager.moveCurrentPlayer(4);
        System.out.println("moveCurrentPlayer(4) = " + move4 + " (esperado: false)");
        assertFalse(move4, "C NÃO deve conseguir mover 4 casas");
    }
}