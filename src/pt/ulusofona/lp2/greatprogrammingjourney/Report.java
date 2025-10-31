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
        int maxPosicao = 0;

        for (Player player : players) {
            if (player.getPosicao() > maxPosicao) {
                maxPosicao = player.getPosicao();
                vencedor = player.getNome() + " " + player.getPosicao();
            }
        }

        results.add("VENCEDOR");
        results.add(vencedor);
        results.add("");

        ArrayList<Player> restantes = new ArrayList<>();
        for (Player p : players) {
            if (p.getPosicao() < boardSize) {
                restantes.add(p);
            }
        }

        // Ordenar por posição decrescente
        restantes.sort(Comparator.comparingInt(Player::getPosicao).reversed());

        StringBuilder restantesStr = new StringBuilder("RESTANTES ");
        for (int i = 0; i < restantes.size(); i++) {
            Player p = restantes.get(i);
            restantesStr.append(p.getNome()).append(" ").append(p.getPosicao());
            if (i < restantes.size() - 1) {
                restantesStr.append(", ");
            }
        }

        results.add(restantesStr.toString());

        return results;
    }
}