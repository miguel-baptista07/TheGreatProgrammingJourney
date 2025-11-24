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
        String vencedor = "Nenhum";
        int maxPos = 0;
        for (Player p : players) {
            if (!p.isEliminado() && p.getPosicao() > maxPos) {
                maxPos = p.getPosicao();
                vencedor = p.getNome();
            }
        }
        results.add("VENCEDOR");
        results.add(vencedor);
        results.add("");
        List<Player> restantes = new ArrayList<>();
        for (Player p : players) {
            if (!p.isEliminado() && p.getPosicao() < boardSize) {
                restantes.add(p);
            }
        }
        restantes.sort(Comparator.comparingInt(Player::getPosicao).reversed());
        results.add("RESTANTES");
        for (Player p : restantes) {
            results.add(p.getNome() + " " + p.getPosicao());
        }
        return results;
    }
}
