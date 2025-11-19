package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.List;

public class GameStatus {

    public boolean checkGameOver(List<Player> vivos, int boardSize) {
        for (Player p : vivos) {
            if (p.getPosicao() >= boardSize) {
                return true;
            }
        }
        return vivos.isEmpty();
    }
}
