package pt.ulusofona.lp2.greatprogrammingjourney;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe principal que gere o jogo.
 * Expandida da parte 1 para incluir abismos, ferramentas e save/load.
 */
public class GameManager {
    private ArrayList<Player> players;
    private Board board;
    private int currentPlayerIndex;
    private int turnCounter;
    private String ultimaMensagemReact;
    private int lastDiceValue;

    public GameManager() {
        this.players = new ArrayList<>();
        this.board = new Board();
        this.currentPlayerIndex = 0;
        this.turnCounter = 0;
        this.ultimaMensagemReact = "";
        this.lastDiceValue = 0;
    }

    /**
     * Cria o tabuleiro inicial (versão da parte 1, sem abismos/ferramentas).
     */
    public boolean createInitialBoard(String[][] playerInfo, int worldSize) {
        return createInitialBoard(playerInfo, worldSize, null);
    }

    /**
     * Cria o tabuleiro inicial com abismos e ferramentas (versão da parte 2).
     */
    public boolean createInitialBoard(String[][] playerInfo, int worldSize, String[][] abyssesAndTools) {
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
        board.limpar();
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

            // Validar ID >= 1
            try {
                int idNum = Integer.parseInt(id);
                if (idNum < 1) {
                    return false;
                }
            } catch (NumberFormatException e) {
                // Se não for número, aceitar (mas validar unicidade)
            }

            // Validar IDs únicos
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

            // Validar cores únicas
            for (Player p : players) {
                if (p.getCor().equalsIgnoreCase(cor)) {
                    return false;
                }
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

        if (abyssesAndTools != null) {
            for (String[] elemento : abyssesAndTools) {
                if (elemento == null || elemento.length != 3) {
                    return false;
                }

                try {
                    int posicao = Integer.parseInt(elemento[0]);
                    String tipo = elemento[1];
                    int id = Integer.parseInt(elemento[2]);

                    if (tipo.equals("A")) {
                        // Validar ID de abismo (0-9)
                        if (id < 0 || id > 9) {
                            return false;
                        }
                        Abismo abismo = criarAbismo(id, posicao);
                        if (abismo == null) { // Se criarAbismo retornar null, é um ID inválido
                            return false;
                        }
                        board.adicionarAbismo(posicao, abismo);
                    } else if (tipo.equals("T")) {
                        // Validar ID de ferramenta (0-5)
                        if (id < 0 || id > 5) {
                            return false;
                        }
                        Ferramenta ferramenta = criarFerramenta(id, posicao);
                        if (ferramenta == null) { // Se criarFerramenta retornar null, é um ID inválido
                            return false;
                        }
                        board.adicionarFerramenta(posicao, ferramenta);
                    } else {
                        // Tipo inválido
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }

        return true;
    }

    private Abismo criarAbismo(int id, int posicao) {
        switch (id) {
            case 0: return new SyntaxError(posicao);
            case 1: return new LogicalError(posicao);
            case 2: return new ExceptionAbismo(posicao);
            case 3: return new FileNotFound(posicao);
            case 4: return new Crash(posicao);
            case 5: return new DuplicatedCode(posicao);
            case 6: return new SideEffects(posicao);
            case 7: return new BSOD(posicao);
            case 8: return new InfiniteLoop(posicao);
            case 9: return new SegmentationFault(posicao);
            default: return null;
        }
    }

    private Ferramenta criarFerramenta(int id, int posicao) {
        switch (id) {
            case 0: return new FerramentaDebugger(posicao);
            case 1: return new FerramentaTryCatch(posicao);
            case 2: return new FerramentaBackup(posicao);
            case 3: return new FerramentaRefactoring(posicao);
            case 4: return new FerramentaAntivirus(posicao);
            case 5: return new FerramentaBreakpoint(posicao);
            default: return null;
        }
    }

    private boolean isValidColor(String cor) {
        return cor.equalsIgnoreCase("Purple") ||
                cor.equalsIgnoreCase("Green") ||
                cor.equalsIgnoreCase("Brown") ||
                cor.equalsIgnoreCase("Blue");
    }

    public boolean moveCurrentPlayer(int nrSpaces) {
        if (gameIsOver()) {
            return false;
        }

        if (nrSpaces < 1 || nrSpaces > 6) {
            return false;
        }

        // Guardar valor do dado para exibição
        this.lastDiceValue = nrSpaces;

        Player currentPlayer = players.get(currentPlayerIndex);

        // Validar se jogador está "Em Jogo" (não ELIMINADO e não PRESO)
        if (currentPlayer.foiEliminado()) {
            return false;
        }

        if (currentPlayer.estaPreso()) {
            return false;
        }

        if (!currentPlayer.podeMovimentar(nrSpaces)) {
            return false;
        }

        int novaPosicao = currentPlayer.getPosicao() + nrSpaces;

        if (novaPosicao > board.getTamanhoTabuleiro()) {
            int excesso = novaPosicao - board.getTamanhoTabuleiro();
            novaPosicao = board.getTamanhoTabuleiro() - excesso;
        }

        currentPlayer.setPosicao(novaPosicao);

        return true;
    }

    public String reactToAbyssOrTool() {
        Player currentPlayer = players.get(currentPlayerIndex);
        int posicao = currentPlayer.getPosicao();
        StringBuilder mensagem = new StringBuilder();

        if (board.temFerramenta(posicao)) {
            Ferramenta ferramenta = board.getFerramenta(posicao);

            if (!currentPlayer.temFerramenta(ferramenta.getId())) {
                currentPlayer.adicionarFerramenta(ferramenta);
                board.removerFerramenta(posicao);
                mensagem.append(currentPlayer.getNome())
                        .append(" apanhou ")
                        .append(ferramenta.getNome())
                        .append("!");
            } else {
                mensagem.append(currentPlayer.getNome())
                        .append(" já tem ")
                        .append(ferramenta.getNome());
            }
        }

        if (board.temAbismo(posicao)) {
            Abismo abismo = board.getAbismo(posicao);
            Ferramenta ferramentaNeutralizadora = currentPlayer.getFerramentaParaNeutralizar(abismo);

            if (ferramentaNeutralizadora != null) {
                if (mensagem.length() > 0) {
                    mensagem.append(" ");
                }
                mensagem.append(ferramentaNeutralizadora.getMensagemNeutralizacao(abismo));
                currentPlayer.removerFerramenta(ferramentaNeutralizadora);
            } else {
                if (mensagem.length() > 0) {
                    mensagem.append(" ");
                }
                String efeitoAbismo = abismo.aplicarEfeito(currentPlayer, players, board.getTamanhoTabuleiro());
                mensagem.append(efeitoAbismo);
            }
        }

        if (currentPlayer.estaPreso() && !board.temAbismo(posicao)) {
            currentPlayer.libertar();
            if (mensagem.length() > 0) {
                mensagem.append(" ");
            }
            mensagem.append(currentPlayer.getNome()).append(" foi libertado!");
        }

        // Avançar para o próximo jogador
        nextPlayer();

        // Retornar null se não houve interação (sem ferramenta, sem abismo, sem libertação)
        if (mensagem.length() == 0) {
            ultimaMensagemReact = null;
        } else {
            ultimaMensagemReact = mensagem.toString();
        }

        return ultimaMensagemReact;
    }

    /**
     * Avança para o próximo jogador ativo (não eliminado e não preso).
     * Incrementa o contador de turnos.
     */
    public void nextPlayer() {
        turnCounter++;

        int tentativas = 0;
        do {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            tentativas++;

            if (tentativas > players.size()) {
                break;
            }
        } while (players.get(currentPlayerIndex).foiEliminado() || players.get(currentPlayerIndex).estaPreso());
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

    public boolean gameIsOver() {
        int jogadoresAtivos = 0;
        boolean alguemChegouAoFim = false;

        for (Player player : players) {
            if (player.getPosicao() >= board.getTamanhoTabuleiro()) {
                alguemChegouAoFim = true;
            }
            // Contar apenas jogadores que não estão eliminados nem presos
            if (!player.foiEliminado() && !player.estaPreso()) {
                jogadoresAtivos++;
            }
        }

        return alguemChegouAoFim || jogadoresAtivos <= 1;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public Board getBoard() {
        return board;
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
        String ferramentasStr = "No tools";

        for (Player p : players) {
            try {
                if (Integer.parseInt(p.getId()) == id) {
                    if (p.foiEliminado()) {
                        estado = "Eliminado";
                    } else if (p.getPosicao() >= board.getTamanhoTabuleiro() && gameIsOver()) {
                        estado = "Vencedor";
                    }

                    // Obter lista de ferramentas
                    List<Ferramenta> ferramentas = p.getFerramentas();
                    if (!ferramentas.isEmpty()) {
                        List<String> nomes = new ArrayList<>();
                        for (Ferramenta f : ferramentas) {
                            nomes.add(f.getNome());
                        }
                        ferramentasStr = String.join("; ", nomes);
                    }
                    break;
                }
            } catch (NumberFormatException e) {
                // Ignorar
            }
        }

        return info[0] + " | " + info[1] + " | " + info[4] + " | " + ferramentasStr + " | " + info[2] + " | " + estado;
    }

    public String getProgrammersInfo() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);

            if (sb.length() > 0) {
                sb.append(" | ");
            }

            sb.append(player.getNome()).append(" : ");

            List<Ferramenta> ferramentas = player.getFerramentas();
            if (ferramentas.isEmpty()) {
                sb.append("No tools");
            } else {
                for (int j = 0; j < ferramentas.size(); j++) {
                    if (j > 0) {
                        sb.append("; ");
                    }
                    sb.append(ferramentas.get(j).getNome());
                }
            }
        }
        return sb.toString();
    }

    public String[] getSlotInfo(int position) {
        if (position < 1 || position > board.getTamanhoTabuleiro()) {
            return null;
        }

        ArrayList<String> playersInPosition = new ArrayList<>();

        for (Player player : players) {
            // Incluir apenas jogadores vivos (não ELIMINADO e não PRESO)
            if (player.getPosicao() == position && !player.foiEliminado() && !player.estaPreso()) {
                playersInPosition.add(player.getId());
            }
        }

        String ids = playersInPosition.isEmpty() ? "" : String.join(",", playersInPosition);
        String elemento = "";

        if (board.temAbismo(position)) {
            Abismo abismo = board.getAbismo(position);
            elemento = abismo.getRepresentacao();
        } else if (board.temFerramenta(position)) {
            Ferramenta ferramenta = board.getFerramenta(position);
            elemento = ferramenta.getRepresentacao();
        }

        return new String[] {ids, "", elemento};
    }

    public String getImagePng(int nrSquare) {
        if (nrSquare < 1 || nrSquare > board.getTamanhoTabuleiro()) {
            return null;
        }

        if (nrSquare == board.getTamanhoTabuleiro()) {
            return "glory.png";
        }

        // Verificar se há abismo na posição
        if (board.temAbismo(nrSquare)) {
            Abismo abismo = board.getAbismo(nrSquare);
            return abismo.getImagemPng();
        }

        // Verificar se há ferramenta na posição
        if (board.temFerramenta(nrSquare)) {
            Ferramenta ferramenta = board.getFerramenta(nrSquare);
            return ferramenta.getImagemPng();
        }

        return null;
    }

    public String getDiceImage() {
        if (lastDiceValue >= 1 && lastDiceValue <= 6) {
            return "dice_" + lastDiceValue + ".png";
        }
        return "dice.png";
    }

    public ArrayList<String> getGameResults() {
        ArrayList<String> results = new ArrayList<>();

        results.add("THE GREAT PROGRAMMING JOURNEY");
        results.add("");
        results.add("NR. DE TURNOS");
        results.add(String.valueOf(turnCounter));
        results.add("");

        String vencedor = "Nenhum";
        int maxPosicao = 0;

        for (Player player : players) {
            if (player.getPosicao() > maxPosicao) {
                maxPosicao = player.getPosicao();
                vencedor = player.getNome();
            }
        }

        results.add("VENCEDOR");
        results.add(vencedor);
        results.add("");

        ArrayList<Player> restantes = new ArrayList<>();
        for (Player p : players) {
            if (p.getPosicao() < board.getTamanhoTabuleiro() && !p.foiEliminado()) {
                restantes.add(p);
            }
        }

        restantes.sort((p1, p2) -> {
            int comparePosicao = Integer.compare(p2.getPosicao(), p1.getPosicao());
            if (comparePosicao != 0) {
                return comparePosicao;
            }
            return p1.getNome().compareTo(p2.getNome());
        });

        results.add("RESTANTES");
        for (Player p : restantes) {
            results.add(p.getNome() + " " + p.getPosicao());
        }

        return results;
    }

    public boolean saveGame(File file) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println(board.getTamanhoTabuleiro());
            writer.println(turnCounter);
            writer.println(currentPlayerIndex);
            writer.println(players.size());

            for (Player p : players) {
                writer.println(p.getId() + ";" + p.getNome() + ";" + p.getLinguagens() + ";" +
                        p.getCor() + ";" + p.getPosicao() + ";" + p.getPosicaoAnterior() + ";" +
                        p.getEstado().name());

                List<Ferramenta> ferramentas = p.getFerramentas();
                writer.println(ferramentas.size());
                for (Ferramenta f : ferramentas) {
                    writer.println(f.getId());
                }
            }

            Map<Integer, Abismo> abismos = board.getAbismos();
            writer.println(abismos.size());
            for (Map.Entry<Integer, Abismo> entry : abismos.entrySet()) {
                writer.println(entry.getKey() + ";" + entry.getValue().getId());
            }

            Map<Integer, Ferramenta> ferramentas = board.getFerramentas();
            writer.println(ferramentas.size());
            for (Map.Entry<Integer, Ferramenta> entry : ferramentas.entrySet()) {
                writer.println(entry.getKey() + ";" + entry.getValue().getId());
            }

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void loadGame(File file) throws InvalidFileException, FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException("Ficheiro não encontrado: " + file.getPath());
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            players.clear();
            board.limpar();

            int tamanho = Integer.parseInt(reader.readLine());
            board.setTamanhoTabuleiro(tamanho);

            // Restaurar turnCounter e currentPlayerIndex
            turnCounter = Integer.parseInt(reader.readLine());
            currentPlayerIndex = Integer.parseInt(reader.readLine());

            int numJogadores = Integer.parseInt(reader.readLine());

            for (int i = 0; i < numJogadores; i++) {
                String[] dados = reader.readLine().split(";");
                if (dados.length != 7) {
                    throw new InvalidFileException("Dados de jogador inválidos");
                }

                Player p = new Player(dados[0], dados[1], dados[2], dados[3]);
                p.setPosicao(Integer.parseInt(dados[4]));
                p.setPosicaoAnterior(Integer.parseInt(dados[5]));
                p.setEstado(PlayerState.valueOf(dados[6]));

                int numFerramentas = Integer.parseInt(reader.readLine());
                for (int j = 0; j < numFerramentas; j++) {
                    int ferramentaId = Integer.parseInt(reader.readLine());
                    Ferramenta f = criarFerramenta(ferramentaId, 0);
                    if (f != null) {
                        p.adicionarFerramenta(f);
                    }
                }

                players.add(p);
            }

            int numAbismos = Integer.parseInt(reader.readLine());
            for (int i = 0; i < numAbismos; i++) {
                String[] dados = reader.readLine().split(";");
                int posicao = Integer.parseInt(dados[0]);
                int id = Integer.parseInt(dados[1]);
                Abismo a = criarAbismo(id, posicao);
                if (a != null) {
                    board.adicionarAbismo(posicao, a);
                }
            }

            int numFerramentas = Integer.parseInt(reader.readLine());
            for (int i = 0; i < numFerramentas; i++) {
                String[] dados = reader.readLine().split(";");
                int posicao = Integer.parseInt(dados[0]);
                int id = Integer.parseInt(dados[1]);
                Ferramenta f = criarFerramenta(id, posicao);
                if (f != null) {
                    board.adicionarFerramenta(posicao, f);
                }
            }

        } catch (IOException | IllegalArgumentException e) {
            throw new InvalidFileException("Erro ao carregar ficheiro: " + e.getMessage(), e);
        }
    }

    public JPanel getAuthorsPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Desenvolvido por: [Seu Nome Aqui]"));
        return panel;
    }

    public HashMap<String, String> customizeBoard() {
        HashMap<String, String> customization = new HashMap<>();
        return customization;
    }
}
