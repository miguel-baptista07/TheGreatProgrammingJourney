package pt.ulusofona.lp2.greatprogrammingjourney;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameManager {

    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<Player> vivos = new ArrayList<>();
    private Board board;
    private int currentPlayerIndex = 0;
    private int turnCounter = 0;
    private String lastReactionMessage = null;

    public GameManager() {
    }

    public boolean createInitialBoard(String[][] playerInfo, int worldSize) {
        return createInitialBoard(playerInfo, worldSize, null);
    }

    public boolean createInitialBoard(String[][] playerInfo, int worldSize, String[][] abyssesAndTools) {
        try {
            players.clear();
            vivos.clear();
            currentPlayerIndex = 0;
            turnCounter = 0;
            lastReactionMessage = null;

            if (playerInfo == null || playerInfo.length < 2 || playerInfo.length > 4) return false;
            if (worldSize < playerInfo.length * 2) return false;

            for (String[] p : playerInfo) {
                if (p == null || p.length != 4) return false;
                Player pl = new Player(p[0], p[1], p[2], p[3]);
                players.add(pl);
                vivos.add(pl);
            }

            players.sort((a, b) -> a.getId().compareTo(b.getId()));

            board = new Board(worldSize);

            for (int i = 1; i <= worldSize; i++) {
                if (i == worldSize) board.setCell(i, CellFactory.glory(i));
                else board.setCell(i, CellFactory.normal(i));
            }

            if (abyssesAndTools != null) {
                for (String[] a : abyssesAndTools) {
                    if (a == null || a.length != 3) return false;
                    int tipo = Integer.parseInt(a[0]);
                    int id = Integer.parseInt(a[1]);
                    int pos = Integer.parseInt(a[2]);

                    if (pos < 1 || pos > worldSize) return false;
                    if (tipo == 0 && (id < 0 || id > 9)) return false;
                    if (tipo == 1 && (id < 0 || id > 5)) return false;

                    Cell c = (tipo == 0) ? AbyssFactory.create(id, pos) : ToolFactory.create(id, pos);
                    if (c == null) return false;
                    board.setCell(pos, c);
                }
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getImagePng(int nrSquare) {
        if (board == null) return null;
        if (nrSquare < 1 || nrSquare > board.getTamanho()) return null;
        return board.getCell(nrSquare).getImagePng();
    }

    // CORREÇÃO 1: Ordem correta -> id, nome, LINGUAGENS, COR, posição
    public String[] getProgrammerInfo(int id) {
        for (Player p : players) {
            if (p.getId().equals(String.valueOf(id))) {
                return new String[]{
                        p.getId(),
                        p.getNome(),
                        p.linguagensAsString(),  // LINGUAGENS primeiro
                        p.getCor(),              // COR depois
                        String.valueOf(p.getPosicao())
                };
            }
        }
        return null;
    }

    private String estadoToString(Player p) {
        if (p.getEstado() == PlayerState.EM_JOGO) return "Em Jogo";
        if (p.getEstado() == PlayerState.PRESO) return "Preso";
        return "Derrotado";
    }

    public String getProgrammerInfoAsStr(int id) {
        for (Player p : players) {
            if (p.getId().equals(String.valueOf(id))) {
                return p.getId() + " | " +
                        p.getNome() + " | " +
                        p.getPosicao() + " | " +
                        p.toolsAsString() + " | " +
                        p.linguagensAsString() + " | " +
                        estadoToString(p);
            }
        }
        return null;
    }

    public String getProgrammersInfo() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Player p : vivos) {
            if (!first) sb.append(" | ");
            first = false;
            sb.append(p.getNome()).append(" : ").append(p.toolsAsString());
        }
        return sb.toString();
    }

    public int getCurrentPlayerID() {
        if (vivos.isEmpty()) return -1;
        return Integer.parseInt(vivos.get(currentPlayerIndex).getId());
    }

    // CORREÇÃO 2: Separar movimento de reação
    public boolean moveCurrentPlayer(int nrSpaces) {
        if (vivos.isEmpty()) return false;

        Player p = vivos.get(currentPlayerIndex);

        // Se está preso, NÃO move e marca para libertar no reactToAbyssOrTool
        if (p.getEstado() == PlayerState.PRESO) {
            lastReactionMessage = "PRESO_LIBERTA";
            return false;
        }

        // Verifica restrições de linguagem
        if (!LanguageRules.isMoveAllowed(p, nrSpaces)) {
            lastReactionMessage = null;
            return false;
        }

        // Move o jogador
        int nova = p.getPosicao() + nrSpaces;

        if (nova > board.getTamanho()) {
            int excesso = nova - board.getTamanho();
            nova = board.getTamanho() - excesso;
            if (nova < 1) nova = 1;
        }

        p.setPosicao(nova);

        // NÃO reage aqui! Será feito no reactToAbyssOrTool()
        lastReactionMessage = null;

        return true;
    }

    // CORREÇÃO 3: React é chamado AQUI, não no moveCurrentPlayer
    public String reactToAbyssOrTool() {
        if (vivos.isEmpty()) return null;

        Player p = vivos.get(currentPlayerIndex);

        // Se estava preso, liberta e avança turno
        if (lastReactionMessage != null && lastReactionMessage.equals("PRESO_LIBERTA")) {
            lastReactionMessage = null;

            // Liberta o jogador
            p.libertar();

            turnCounter++;
            currentPlayerIndex = (currentPlayerIndex + 1) % vivos.size();
            return "O jogador estava preso";
        }

        // IMPORTANTE: Verificar se chegou ao fim ANTES de reagir
        if (p.getPosicao() >= board.getTamanho()) {
            // Jogador venceu! Não avança mais o turno para ele
            turnCounter++;
            if (!vivos.isEmpty()) {
                currentPlayerIndex = (currentPlayerIndex + 1) % vivos.size();
            }
            return "O jogador chegou ao fim!";
        }

        // Reage à célula atual
        Cell c = board.getCell(p.getPosicao());
        String msg = c.react(p, this);

        // Se jogador foi eliminado
        if (msg != null && msg.contains("eliminado")) {
            vivos.remove(currentPlayerIndex);

            turnCounter++;
            if (!vivos.isEmpty()) {
                currentPlayerIndex = currentPlayerIndex % vivos.size();
            }
            return msg;
        }

        // Avança turno
        turnCounter++;
        if (!vivos.isEmpty()) {
            currentPlayerIndex = (currentPlayerIndex + 1) % vivos.size();
        }

        return msg;
    }

    public boolean gameIsOver() {
        GameStatus gs = new GameStatus();
        return gs.checkGameOver(vivos, board.getTamanho());
    }

    public ArrayList<String> getGameResults() {
        Report r = new Report(turnCounter, vivos, board.getTamanho());
        return r.generateReport();
    }

    public JPanel getAuthorsPanel() {
        JPanel p = new JPanel();
        p.add(new JLabel("Desenvolvido por: Miguel Baptista e Gonçalo Almeida"));
        return p;
    }

    public HashMap<String, String> customizeBoard() {
        return new HashMap<>();
    }

    public boolean saveGame(File f) {
        return SaveLoadManager.save(f, this);
    }

    public void loadGame(File f) throws InvalidFileException, FileNotFoundException {
        SaveLoadManager.load(f, this);
    }

    public String serializeState() {
        StringBuilder sb = new StringBuilder();
        sb.append("PLAYERS ").append(players.size()).append("\n");
        for (Player p : players) sb.append(p.toInfoString()).append("\n");
        return sb.toString();
    }

    public void deserializeState(BufferedReader br) throws InvalidFileException {
        try {
            String line = br.readLine();
            if (line == null || !line.startsWith("PLAYERS")) throw new InvalidFileException("Formato inválido");
        } catch (Exception e) {
            throw new InvalidFileException("Erro ao carregar");
        }
    }

    public List<Player> getAlivePlayersAt(int pos) {
        List<Player> r = new ArrayList<>();
        for (Player p : vivos) {
            if (p.getPosicao() == pos && p.getEstado() == PlayerState.EM_JOGO) r.add(p);
        }
        return r;
    }

    // CORREÇÃO 4: Retornar 3 elementos como esperado pelo visualizador
    public String[] getSlotInfo(int position) {
        if (board == null || position < 1 || position > board.getTamanho()) return null;

        // IDs dos jogadores nesta posição
        ArrayList<String> ids = new ArrayList<>();
        for (Player p : players) {
            if (p.getPosicao() == position) {
                ids.add(p.getId());
            }
        }
        String jogadores = ids.isEmpty() ? "" : String.join(",", ids);

        // Informação da célula (abismo ou ferramenta)
        Cell c = board.getCell(position);
        String cellInfo = "";

        if (c != null && !(c instanceof NormalCell) && !(c instanceof GloryCell)) {
            // É um abismo ou ferramenta
            if (c instanceof Tool) {
                cellInfo = "T:" + c.getId();
            } else {
                cellInfo = "A:" + c.getId();
            }
        }

        // Retorna: [jogadores, "", tipo_elemento]
        return new String[]{jogadores, "", cellInfo};
    }
}