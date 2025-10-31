package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.List;

public class GameStatus {
    private boolean gameOver;
    private final int boardSize;

    public GameStatus(int boardSize) {
        this.gameOver = false;
        this.boardSize = boardSize;
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
