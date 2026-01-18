package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Report {
    private final int nrTurnos;
    private final List<Player> players;
    private final int boardSize;

    public Report(int nrTurnos, List<Player> players, int boardSize) {
        this.nrTurnos = nrTurnos;
        this.players = players;
        this.boardSize = boardSize;
    }

    public ArrayList<String> generateReport() {
        ArrayList<String> results = new ArrayList<>();

        results.add("THE GREAT PROGRAMMING JOURNEY");
        results.add("");
        results.add("NR. DE TURNOS");
        results.add(String.valueOf(nrTurnos));
        results.add("");

        // Verificar se existe vencedor (alguém chegou ao fim do tabuleiro)
        Player vencedor = null;
        int maxPos = -1;

        for (Player p : players) {
            if (!p.isEliminado() && p.getPosicao() >= boardSize) {
                if (p.getPosicao() > maxPos) {
                    maxPos = p.getPosicao();
                    vencedor = p;
                }
            }
        }

        // Se não há vencedor, é empate
        if (vencedor == null) {
            results.add("O jogo terminou empatado.");
            results.add("");
            // Em caso de empate, mostrar todos com formato completo
            results.add("Participantes:");
            
            List<Player> participantes = new ArrayList<>(players);
            participantes.sort(
                    Comparator.comparingInt(Player::getPosicao).reversed()
                            .thenComparing(p -> p.getNome().toLowerCase())
            );

            for (Player p : participantes) {
                String causa = p.getCausaDerrota();
                if (causa == null || causa.isEmpty()) {
                    causa = "No tools";
                }

                results.add(
                        p.getNome() + " : " + p.getPosicao() + " : " + causa
                );
            }
        } else {
            results.add("VENCEDOR");
            results.add(vencedor.getNome());
            results.add("");
            
            // Quando há vencedor, mostrar RESTANTES com formato simplificado (sem causa)
            results.add("RESTANTES");
            
            List<Player> restantes = new ArrayList<>();
            for (Player p : players) {
                if (!p.equals(vencedor)) {
                    restantes.add(p);
                }
            }
            
            restantes.sort(
                    Comparator.comparingInt(Player::getPosicao).reversed()
                            .thenComparing(p -> p.getNome().toLowerCase())
            );

            for (Player p : restantes) {
                results.add(p.getNome() + " " + p.getPosicao());
            }
        }

        return results;
    }
}
