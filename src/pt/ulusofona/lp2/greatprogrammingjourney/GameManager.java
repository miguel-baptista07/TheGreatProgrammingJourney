package pt.ulusofona.lp2.greatprogrammingjourney;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class GameManager {
    private final List<Player> players;
    private final List<Player> allPlayers;
    private final Board board;
    private int currentPlayerIndex;
    private int turnCounter;
    private boolean gameOver;

    public GameManager() {
        players = new ArrayList<>();
        allPlayers = new ArrayList<>();
        board = new Board();
        currentPlayerIndex = 0;
        turnCounter = 0;
        gameOver = false;
    }

    private void resetGame() {
        players.clear();
        allPlayers.clear();
        board.clearElements();
        currentPlayerIndex = 0;
        turnCounter = 0;
        gameOver = false;
    }

    public static String toolName(int id) {
        switch (id) {
            case 0: return "Herança";
            case 1: return "Programação Funcional";
            case 2: return "Testes Unitários";
            case 3: return "Tratamento de Excepções";
            case 4: return "IDE";
            case 5: return "Ajuda do Professor";
            default: return "T" + id;
        }
    }

    public boolean createInitialBoard(String[][] playerInfo, int worldSize) {
        return createInitialBoard(playerInfo, worldSize, null);
    }

    public boolean createInitialBoard(String[][] playerInfo, int worldSize, String[][] abyssesAndTools) {
        resetGame();
        if (playerInfo == null || worldSize < 2) {
            return false;
        }
        if (playerInfo.length < 2 || playerInfo.length > 4) {
            return false;
        }
        if (worldSize < 2 * playerInfo.length) {
            return false;
        }

        for (String[] info : playerInfo) {
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

            Player newPlayer = new Player(id, nome, linguagens, cor);
            newPlayer.setPosicao(1);
            newPlayer.setEliminado(false);
            newPlayer.setPreso(false);
            newPlayer.setFerramentaAtiva(null);

            players.add(newPlayer);
            allPlayers.add(newPlayer);
        }

        players.sort(Comparator.comparingInt(p -> Integer.parseInt(p.getId())));
        allPlayers.sort(Comparator.comparingInt(p -> Integer.parseInt(p.getId())));

        board.setTamanhoTabuleiro(worldSize);

        if (abyssesAndTools != null) {
            for (String[] row : abyssesAndTools) {
                if (row == null || row.length < 3) {
                    return false;
                }
                int type, subtype, position;
                try {
                    type = Integer.parseInt(row[0]);
                    subtype = Integer.parseInt(row[1]);
                    position = Integer.parseInt(row[2]);
                } catch (NumberFormatException e) {
                    return false;
                }
                if (type != 0 && type != 1) {
                    return false;
                }
                if (position < 1 || position > worldSize) {
                    return false;
                }

                BoardElement e = ElementsIOAdapter.toElement(type, subtype, position);
                board.addElement(e);
            }
        }

        return true;
    }

    private boolean isValidColor(String cor) {
        return cor.equalsIgnoreCase("Purple")
                || cor.equalsIgnoreCase("Green")
                || cor.equalsIgnoreCase("Brown")
                || cor.equalsIgnoreCase("Blue");
    }

    public String getImagePng(int nrSquare) {
        if (nrSquare < 1 || nrSquare > board.getTamanhoTabuleiro()) {
            return null;
        }
        if (nrSquare == board.getTamanhoTabuleiro()) {
            return "glory.png";
        }
        BoardElement el = board.getElementAt(nrSquare);
        if (el == null) {
            return null;
        }
        if (el.isAbyss()) {
            switch (el.getId()) {
                case 0: return "syntax.png";
                case 1: return "logic.png";
                case 2: return "exception.png";
                case 3: return "file-not-found-exception.png";
                case 4: return "crash.png";
                case 5: return "duplicated-code.png";
                case 6: return "secondary-effects.png";
                case 7: return "bsod.png";
                case 8: return "infinite-loop.png";
                case 9: return "core-dumped.png";
                default: return "unknownPiece.png";
            }
        } else {
            switch (el.getId()) {
                case 0: return "inheritance.png";
                case 1: return "functional.png";
                case 2: return "unit-tests.png";
                case 3: return "catch.png";
                case 4: return "IDE.png";
                case 5: return "ajuda-professor.png";
                default: return "unknownPiece.png";
            }
        }
    }

    public String[] getProgrammerInfo(int id) {
        for (Player p : allPlayers) {
            try {
                if (Integer.parseInt(p.getId()) == id) {
                    return new String[]{
                            p.getId(),
                            p.getNome(),
                            p.getLinguagens(),
                            formatColor(p.getCor()),
                            String.valueOf(p.getPosicao())
                    };
                }
            } catch (NumberFormatException e) {
                if (p.getId().equals(String.valueOf(id))) {
                    return new String[]{
                            p.getId(),
                            p.getNome(),
                            p.getLinguagens(),
                            formatColor(p.getCor()),
                            String.valueOf(p.getPosicao())
                    };
                }
            }
        }
        return null;
    }

    private String formatColor(String cor) {
        return cor.substring(0, 1).toUpperCase() + cor.substring(1).toLowerCase();
    }

    public String getProgrammerInfoAsStr(int id) {
        Player found = null;
        for (Player p : allPlayers) {
            String pid = p.getId().trim();
            try {
                if (Integer.parseInt(pid) == id) {
                    found = p;
                }
            } catch (NumberFormatException ignored) {
                if (pid.equals(String.valueOf(id))) {
                    found = p;
                }
            }
            if (found != null) {
                break;
            }
        }
        if (found == null) {
            return null;
        }

        String toolStr = found.getFerramentas().isEmpty()
                ? "No tools"
                : found.getFerramentasAsString();

        String estado = found.isEliminado() ? "Derrotado"
                : found.isPreso() ? "Preso"
                : "Em Jogo";

        return found.getId()
                + " | " + found.getNome()
                + " | " + found.getPosicao()
                + " | " + toolStr
                + " | " + found.getLinguagens()
                + " | " + estado;
    }

    public String getProgrammersInfo() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Player p : players) {
            if (!first) {
                sb.append(" | ");
            }
            sb.append(p.getNome())
                    .append(" : ")
                    .append(p.getFerramentas().isEmpty()
                            ? "No tools"
                            : p.getFerramentasAsString());
            first = false;
        }
        return sb.toString();
    }

    public String[] getSlotInfo(int position) {
        if (position < 1 || position > board.getTamanhoTabuleiro()) {
            return null;
        }
        List<String> ids = new ArrayList<>();
        for (Player p : players) {
            if (p.getPosicao() == position) {
                ids.add(p.getId());
            }
        }
        String joined = ids.isEmpty() ? "" : String.join(",", ids);
        BoardElement e = board.getElementAt(position);
        String type = "";
        if (e != null) {
            if (e.isAbyss()) {
                type = "A:" + e.getId();
            } else {
                type = "T:" + e.getId();
            }
        }
        return new String[]{joined, "", type};
    }

    public int getCurrentPlayerID() {
        if (players.isEmpty()) {
            return -1;
        }
        normalizeCurrentIndex();
        Player cur = players.get(currentPlayerIndex);
        try {
            return Integer.parseInt(cur.getId());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void normalizeCurrentIndex() {
        if (players.isEmpty()) {
            currentPlayerIndex = 0;
        }
        if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex = 0;
        }
    }

    public boolean moveCurrentPlayer(int nrSpaces) {
        if (gameOver) {
            return false;
        }
        if (nrSpaces < 1 || nrSpaces > 6) {
            return false;
        }
        if (players.isEmpty()) {
            return false;
        }

        normalizeCurrentIndex();
        Player current = players.get(currentPlayerIndex);

        if (current.isPreso()) {
            current.setPreso(false);
            advanceToNextAlive();
            turnCounter++;
            return false;
        }

        if (current.getLinguagens().contains("Assembly")) {
            if (nrSpaces > 2) {
                return false;
            }
        }

        if (current.getLinguagens().contains("C")) {
            if (nrSpaces > 3) {
                return false;
            }
        }



        current.prepararMovimento();

        int novaPos = current.getPosicao() + nrSpaces;
        if (novaPos > board.getTamanhoTabuleiro()) {
            int excesso = novaPos - board.getTamanhoTabuleiro();
            novaPos = board.getTamanhoTabuleiro() - excesso;
            if (novaPos < 1) {
                novaPos = 1;
            }
        }

        current.setLastMoveSpaces(nrSpaces);
        current.setPosicao(novaPos);
        return true;
    }

    public String reactToAbyssOrTool() {
        if (gameOver || players.isEmpty()) {
            return null;
        }

        normalizeCurrentIndex();
        Player current = players.get(currentPlayerIndex);

        BoardElement el = board.getElementAt(current.getPosicao());
        String message = null;

        if (el != null) {
            message = el.applyEffect(current, this);
        }

        checkGameOverCondition();
        advanceToNextAlive();
        turnCounter++;

        if (message != null && message.contains("anulado por")) {
            current.setFerramentaAtiva(null);
        }

        if (players.isEmpty()) {
            gameOver = true;
        }

        return message;
    }

    private void advanceToNextAlive() {
        if (players.isEmpty()) {
            currentPlayerIndex = 0;
            return;
        }
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    private void checkGameOverCondition() {
        for (Player p : players) {
            if (p.getPosicao() >= board.getTamanhoTabuleiro()) {
                gameOver = true;
                return;
            }
        }
    }

    public boolean gameIsOver() {
        if (gameOver) {
            return true;
        }
        checkGameOverCondition();
        return gameOver;
    }

    public ArrayList<String> getGameResults() {
        Report r = new Report(turnCounter, allPlayers, board.getTamanhoTabuleiro());
        return r.generateReport();
    }

    public boolean saveGame(File file) {
        if (file == null) {
            return false;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(String.valueOf(board.getTamanhoTabuleiro()));
            bw.newLine();
            bw.write(String.valueOf(turnCounter));
            bw.newLine();
            bw.write(String.valueOf(currentPlayerIndex));
            bw.newLine();
            bw.write(String.valueOf(allPlayers.size()));
            bw.newLine();

            for (Player p : allPlayers) {
                String tools = p.getFerramentas()
                        .stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(","));

                bw.write(String.join(";", Arrays.asList(
                        p.getId(),
                        p.getNome(),
                        p.getLinguagens(),
                        p.getCor(),
                        String.valueOf(p.getPosicao()),
                        String.valueOf(p.isEliminado()),
                        tools
                )));
                bw.newLine();
            }

            bw.write(String.valueOf(board.getElementos().size()));
            bw.newLine();

            for (BoardElement be : board.getElementos().values()) {
                int type = be.isAbyss() ? 0 : 1;
                bw.write(type + ";" + be.getId() + ";" + be.getPosition());
                bw.newLine();
            }

            bw.flush();
            return true;

        } catch (IOException e) {
            return false;
        }
    }

    public void loadGame(File file) throws InvalidFileException, FileNotFoundException {
        if (file == null || !file.exists()) {
            throw new FileNotFoundException();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            int worldSize = loadIntLine(br, "Empty or invalid world size");
            int savedTurn = loadIntLine(br, "Invalid turnCounter");
            int savedCurrentIdx = loadIntLine(br, "Invalid currentPlayerIndex");
            int playersCount = loadIntLine(br, "Invalid players count");

            List<Player> loadedPlayers = loadPlayersBlock(br, playersCount);

            int elementsCount = loadIntLine(br, "Invalid elements count");
            Map<Integer, BoardElement> loadedElements = loadElementsBlock(br, elementsCount, worldSize);

            players.clear();
            allPlayers.clear();
            allPlayers.addAll(loadedPlayers);

            for (Player p : loadedPlayers) {
                if (!p.isEliminado()) {
                    players.add(p);
                }
            }

            board.clearElements();
            for (BoardElement be : loadedElements.values()) {
                board.addElement(be);
            }
            board.setTamanhoTabuleiro(worldSize);

            turnCounter = savedTurn;
            currentPlayerIndex = Math.max(0, Math.min(savedCurrentIdx, players.size() - 1));
            gameOver = false;

            checkGameOverCondition();

        } catch (IOException ex) {
            throw new InvalidFileException("IO error while reading file: " + ex.getMessage());
        }
    }

    private int loadIntLine(BufferedReader br, String errMsg) throws IOException, InvalidFileException {
        String line = br.readLine();
        if (line == null) {
            throw new InvalidFileException(errMsg);
        }
        try {
            return Integer.parseInt(line.trim());
        } catch (NumberFormatException e) {
            throw new InvalidFileException(errMsg);
        }
    }

    private List<Player> loadPlayersBlock(BufferedReader br, int count)
            throws IOException, InvalidFileException {

        List<Player> loaded = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            String line = br.readLine();
            if (line == null) {
                throw new InvalidFileException("Incomplete players data");
            }

            String[] parts = line.split(";", -1);
            if (parts.length < 6) {
                throw new InvalidFileException("Invalid player line");
            }

            String id = parts[0];
            String name = parts[1];
            String langs = parts[2];
            String color = parts[3];

            int pos;
            try {
                pos = Integer.parseInt(parts[4]);
            } catch (NumberFormatException e) {
                throw new InvalidFileException("Invalid player position");
            }

            boolean elim = Boolean.parseBoolean(parts[5]);

            Player p = new Player(id, name, langs, color);
            p.setPosicao(pos);
            p.setEliminado(elim);

            if (parts.length >= 7 && !parts[6].isEmpty()) {
                for (String t : parts[6].split(",")) {
                    p.addTool(Integer.parseInt(t));
                }
            }

            loaded.add(p);
        }

        return loaded;
    }

    private Map<Integer, BoardElement> loadElementsBlock(BufferedReader br,
                                                         int count,
                                                         int worldSize)
            throws IOException, InvalidFileException {

        Map<Integer, BoardElement> elems = new HashMap<>();

        for (int i = 0; i < count; i++) {
            String line = br.readLine();
            if (line == null) {
                throw new InvalidFileException("Incomplete elements");
            }

            String[] parts = line.split(";", -1);
            if (parts.length < 3) {
                throw new InvalidFileException("Invalid element line");
            }

            int type, subtype, pos;

            try {
                type = Integer.parseInt(parts[0]);
                subtype = Integer.parseInt(parts[1]);
                pos = Integer.parseInt(parts[2]);
            } catch (NumberFormatException e) {
                throw new InvalidFileException("Invalid element numbers");
            }

            if (pos < 1 || pos > worldSize) {
                throw new InvalidFileException("Element position out of bounds");
            }

            BoardElement be = ElementsIOAdapter.toElement(type, subtype, pos);
            elems.put(pos, be);
        }

        return elems;
    }

    public JPanel getAuthorsPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Desenvolvido por: Miguel Baptista e Gonçalo Almeida"));
        return panel;
    }

    public HashMap<String, String> customizeBoard() {
        return new HashMap<>();
    }

    public List<Player> getPlayersAtPosition(int pos) {
        List<Player> res = new ArrayList<>();
        for (Player p : players) {
            if (p.getPosicao() == pos) {
                res.add(p);
            }
        }
        return res;
    }

    public void eliminatePlayer(Player p) {
        if (p == null) {
            return;
        }
        p.setEliminado(true);
        players.remove(p);
        if (players.isEmpty()) {
            currentPlayerIndex = 0;
            gameOver = true;
        } else {
            if (currentPlayerIndex >= players.size()) {
                currentPlayerIndex = 0;
            }
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public int getPreviousPosition(Player p, int movesBack) {
        return p.getHistoricalPosition(movesBack);
    }
}