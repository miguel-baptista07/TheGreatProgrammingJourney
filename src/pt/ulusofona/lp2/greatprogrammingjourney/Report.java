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

        Player vencedor = null;
        int maxPos = 0;
        for (Player p : players) {
            if (!p.isEliminado() && p.getPosicao() >= boardSize) {
                if (p.getPosicao() > maxPos) {
                    maxPos = p.getPosicao();
                    vencedor = p;
                }
            }
        }

        if (vencedor == null) {
            for (Player p : players) {
                if (!p.isEliminado() && p.getPosicao() > maxPos) {
                    maxPos = p.getPosicao();
                    vencedor = p;
                }
            }
        }

        results.add("VENCEDOR");
        results.add(vencedor != null ? vencedor.getNome() : "Nenhum");
        results.add("");

        List<Player> restantes = new ArrayList<>();
        for (Player p : players) {
            if (p != vencedor) {
                restantes.add(p);
            }
        }

        java.util.Map<Player, Integer> creationOrder = new java.util.HashMap<>();
        int order = 0;
        for (Player p : players) {
            creationOrder.put(p, order++);
        }

        restantes.sort(Comparator
                .comparing(Player::isEliminado)
                .thenComparingInt(Player::getPosicao).reversed()
                .thenComparing(p -> creationOrder.getOrDefault(p, Integer.MAX_VALUE))
        );

        results.add("RESTANTES");
        for (Player p : restantes) {
            results.add(p.getNome() + " " + p.getPosicao());
        }

        return results;
    }
}