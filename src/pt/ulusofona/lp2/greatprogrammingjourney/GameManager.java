package pt.ulusofona.lp2.greatprogrammingjourney;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GameManager {
    private ArrayList<Player> players;
    private Board board;
    private int currentPlayerIndex;
    private int turnCounter;
    private GameStatus gameStatus;

    public GameManager() {
        this.players = new ArrayList<>();
        this.board = new Board();
        this.currentPlayerIndex = 0;
        this.turnCounter = 0;
        this.gameStatus = null;
    }

    public boolean createInitialBoard(String[][] playerInfo, int worldSize) {
        if (playerInfo == null || worldSize < 2) {
            return false;
        }

        if (playerInfo.length < 2 || playerInfo.length > 4) {
            return false;
        }

        if (worldSize < 2 * playerInfo.length) {
            return false;
        }

        players.clear();
        turnCounter = 1;
        currentPlayerIndex = 0;

        for (int i = 0; i < playerInfo.length; i++) {
            String[] info = playerInfo[i];

            if (info == null || info.length != 4) {
                return false;
            }

            String id = info[0];
            String nome = info[1];
            String linguagens = info[2];
            String cor = info[3];

            if (id == null || id.isEmpty()) {
                return false;
            }

            for (Player p : players) {
                if (p.getId().equals(id)) {
                    return false;
                }
            }

            if (nome == null || nome.isEmpty()) {
                return false;
            }

            if (cor == null || !isValidColor(cor)) {
                return false;
            }

            Player player = new Player(id, nome, linguagens, cor);
            players.add(player);
        }

        players.sort((p1, p2) -> {
            try {
                int id1 = Integer.parseInt(p1.getId());
                int id2 = Integer.parseInt(p2.getId());
                return Integer.compare(id1, id2);
            } catch (NumberFormatException e) {
                return p1.getId().compareTo(p2.getId());
            }
        });

        board.setTamanhoTabuleiro(worldSize);
        gameStatus = new GameStatus(worldSize);

        return true;
    }

    private boolean isValidColor(String cor) {
        return cor.equalsIgnoreCase("Purple") ||
                cor.equalsIgnoreCase("Green") ||
                cor.equalsIgnoreCase("Brown") ||
                cor.equalsIgnoreCase("Blue");
    }

    public String getImagePng(int nrSquare) {
        if (nrSquare < 1 || nrSquare > board.getTamanhoTabuleiro()) {
            return null;
        }

        if (nrSquare == board.getTamanhoTabuleiro()) {
            return "glory.png";
        }

        return null;
    }

    public String[] getProgrammerInfo(int id) {
        for (Player player : players) {
            try {
                if (Integer.parseInt(player.getId()) == id) {
                    String corFormatada = player.getCor().substring(0, 1).toUpperCase() +
                            player.getCor().substring(1).toLowerCase();
                    return new String[] {
                            player.getId(),
                            player.getNome(),
                            player.getLinguagens(),
                            corFormatada,
                            String.valueOf(player.getPosicao())
                    };
                }
            } catch (NumberFormatException e) {
                if (player.getId().equals(String.valueOf(id))) {
                    String corFormatada = player.getCor().substring(0, 1).toUpperCase() +
                            player.getCor().substring(1).toLowerCase();
                    return new String[] {
                            player.getId(),
                            player.getNome(),
                            player.getLinguagens(),
                            corFormatada,
                            String.valueOf(player.getPosicao())
                    };
                }
            }
        }
        return null;
    }

    public String getProgrammerInfoAsStr(int id) {
        String[] info = getProgrammerInfo(id);
        if (info == null) {
            return null;
        }

        String estado = "Em Jogo";

        for (Player p : players) {
            try {
                if (Integer.parseInt(p.getId()) == id) {
                    if (p.getPosicao() > board.getTamanhoTabuleiro()) {
                        estado = "Fora do Jogo";
                    }
                    break;
                }
            } catch (NumberFormatException e) {
                System.err.println("Erro ao converter ID: " + p.getId());
            }
        }

        return info[0] + " | " + info[1] + " | " + info[4] + " | " + info[2] + " | " + estado;
    }

    public String[] getSlotInfo(int position) {
        if (position < 1 || position > board.getTamanhoTabuleiro()) {
            return null;
        }

        ArrayList<String> playersInPosition = new ArrayList<>();

        for (Player player : players) {
            if (player.getPosicao() == position) {
                playersInPosition.add(player.getId());
            }
        }

        if (playersInPosition.isEmpty()) {
            return new String[] {""};
        }

        String ids = String.join(",", playersInPosition);
        return new String[] {ids};
    }

    public int getCurrentPlayerID() {
        if (players.isEmpty()) {
            return -1;
        }

        Player currentPlayer = players.get(currentPlayerIndex);
        try {
            return Integer.parseInt(currentPlayer.getId());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public boolean moveCurrentPlayer(int nrSpaces) {
        if (gameStatus.isGameOver()) {
            return false;
        }

        if (nrSpaces < 1 || nrSpaces > 6) {
            return false;
        }

        Player currentPlayer = players.get(currentPlayerIndex);

        int novaPosicao = currentPlayer.getPosicao() + nrSpaces;

        if (novaPosicao > board.getTamanhoTabuleiro()) {
            int excesso = novaPosicao - board.getTamanhoTabuleiro();
            novaPosicao = board.getTamanhoTabuleiro() - excesso;
        }

        currentPlayer.setPosicao(novaPosicao);

        turnCounter++;

        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();

        gameStatus.checkGameOver(players);

        return true;
    }

    public boolean gameIsOver() {
        return gameStatus.checkGameOver(players);
    }

    public ArrayList<String> getGameResults() {
        Report report = new Report(turnCounter, players, board.getTamanhoTabuleiro());
        return report.generateReport();
    }

    public JPanel getAuthorsPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Desenvolvido por: Miguel Baptista e Gonçao Almeida"));
        return panel;
    }

    public HashMap<String, String> customizeBoard() {
        HashMap<String, String> customization = new HashMap<>();
        return customization;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getTurnCounter() {
        return turnCounter;
    }
}