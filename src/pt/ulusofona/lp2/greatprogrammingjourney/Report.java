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
        results.add("NR. OF TURNS");
        results.add(String.valueOf(nrTurnos));
        results.add("");

        String winner = "None";
        int maxPos = 0;
        for (Player p : players) {
            if (!p.isEliminado() && p.getPosicao() > maxPos) {
                maxPos = p.getPosicao();
                winner = p.getNome();
            }
        }

        results.add("WINNER");
        results.add(winner);
        results.add("");

        List<Player> remaining = new ArrayList<>();
        for (Player p : players) {
            if (!p.isEliminado() && p.getPosicao() < boardSize) {
                remaining.add(p);
            }
        }

        remaining.sort(Comparator.comparingInt(Player::getPosicao).reversed());

        results.add("REMAINING");
        for (Player p : remaining) {
            results.add(p.getNome() + " " + p.getPosicao());
        }

        return results;
    }
}
