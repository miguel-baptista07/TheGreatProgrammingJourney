package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.List;

public class GameStatus {
    private boolean gameOver;
    private final int boardSize;
    private final List<Player> players;

    public GameStatus(int boardSize, List<Player> players) {
        this.gameOver = false;
        this.boardSize = boardSize;
        this.players = players;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean checkGameOver(List<Player> players) {
        if (gameOver) {
            return true;
        }

        for (Player player : players) {
            if (player.getPosicao() >= boardSize) {
                this.gameOver = true;
                return true;
            }
        }

        return false;
    }
}
