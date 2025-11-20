package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Report {

    private final int nrTurnos;
    private final List<Player> vivos;
    private final int boardSize;

    public Report(int nrTurnos, List<Player> vivos, int boardSize) {
        this.nrTurnos = nrTurnos;
        this.vivos = vivos;
        this.boardSize = boardSize;
    }

    public ArrayList<String> generateReport() {
        ArrayList<String> r = new ArrayList<>();

        r.add("THE GREAT PROGRAMMING JOURNEY");
        r.add("");
        r.add("NR. DE TURNOS");
        r.add(String.valueOf(nrTurnos));
        r.add("");

        Player vencedor = null;
        for (Player p : vivos) {
            if (p.getPosicao() == boardSize) {
                vencedor = p;
                break;
            }
        }

        r.add("VENCEDOR");
        r.add(vencedor == null ? "Nenhum" : vencedor.getNome());
        r.add("");

        r.add("RESTANTES");

        final Player finalVencedor = vencedor;

        vivos.stream()
                .filter(p -> p != finalVencedor)
                .sorted(Comparator.comparingInt(a -> Integer.parseInt(a.getId())))
                .forEach(p -> r.add(p.getNome() + " " + p.getPosicao()));

        return r;
    }
}
