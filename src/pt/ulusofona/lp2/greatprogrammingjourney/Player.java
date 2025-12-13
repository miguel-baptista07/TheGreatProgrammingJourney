package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player {

    private final String id;
    private String nome;
    private String linguagens;
    private String primeiraLinguagem;
    private String cor;

    private int posicao;
    private int posicaoAnteriorMovimento;

    private final List<Integer> ferramentas;
    private Integer ferramentaAtiva;

    private boolean eliminado;
    private int presoTurns; // 🔑 ÚNICA fonte de verdade para "Preso"

    private int lastMoveSpaces;
    private final List<Integer> posicaoHistorico;

    public Player(String id, String nome, String linguagens, String cor) {
        this.id = id;
        this.nome = nome;

        if (linguagens != null && !linguagens.trim().isEmpty()) {
            String[] langs = linguagens.split(";");
            this.primeiraLinguagem = langs[0].trim();
        } else {
            this.primeiraLinguagem = "";
        }

        this.linguagens = formatarLinguagens(linguagens);
        this.cor = cor;

        this.posicao = 1;
        this.posicaoAnteriorMovimento = 1;

        this.ferramentas = new ArrayList<>();
        this.ferramentaAtiva = null;

        this.eliminado = false;
        this.presoTurns = 0;

        this.lastMoveSpaces = 0;
        this.posicaoHistorico = new ArrayList<>();
        this.posicaoHistorico.add(1);
    }

    private String formatarLinguagens(String linguagens) {
        if (linguagens == null || linguagens.trim().isEmpty()) {
            return "";
        }
        String[] langs = linguagens.split(";");
        for (int i = 0; i < langs.length; i++) {
            langs[i] = langs[i].trim();
        }
        Arrays.sort(langs);
        return String.join("; ", langs);
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getLinguagens() { return linguagens; }
    public String getPrimeiraLinguagem() { return primeiraLinguagem; }
    public String getCor() { return cor; }
    public int getPosicao() { return posicao; }

    public void prepararMovimento() {
        this.posicaoAnteriorMovimento = this.posicao;
    }

    public int getPosicaoAnteriorMovimento() {
        return posicaoAnteriorMovimento;
    }

    public void setPosicaoSemGuardarHistorico(int posicao) {
        if (posicao < 1){
            posicao = 1;
        }
        this.posicao = posicao;
        guardarHistorico();
    }

    public void setPosicao(int posicao) {
        if (posicao < 1) {
            posicao = 1;
        }
        this.posicaoAnteriorMovimento = this.posicao;
        this.posicao = posicao;
        guardarHistorico();
    }

    private void guardarHistorico() {
        posicaoHistorico.add(this.posicao);
        if (posicaoHistorico.size() > 10) {
            posicaoHistorico.remove(0);
        }
    }

    public boolean hasTool(int toolId) {
        return ferramentas.contains(toolId);
    }

    public void addTool(int toolId) {
        if (!ferramentas.contains(toolId)) {
            ferramentas.add(toolId);
        }
    }

    public void removeTool(int toolId) {
        ferramentas.remove(Integer.valueOf(toolId));
    }

    public List<Integer> getFerramentas() {
        return new ArrayList<>(ferramentas);
    }

    public Integer getFerramentaAtiva() {
        return ferramentaAtiva;
    }

    public void setFerramentaAtiva(Integer ferramentaAtiva) {
        this.ferramentaAtiva = ferramentaAtiva;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    // 🔑 Prisão por turnos
    public boolean isPreso() {
        return presoTurns > 0;
    }

    public void prender(int turnos) {
        this.presoTurns = Math.max(this.presoTurns, turnos);
    }

    public void consumirTurnoPreso() {
        if (presoTurns > 0) {
            presoTurns--;
        }
    }

    public void setLastMoveSpaces(int lastMoveSpaces) {
        this.lastMoveSpaces = lastMoveSpaces;
    }

    public int getLastMoveSpaces() {
        return lastMoveSpaces;
    }

    public int getHistoricalPosition(int movesBack) {
        int idx = posicaoHistorico.size() - 1 - movesBack;
        if (idx < 0) {
            return posicaoHistorico.get(0);
        }
        return posicaoHistorico.get(idx);
    }

    public boolean hasLanguage(String lang) {
        String[] ls = linguagens.split(";");
        for (String l : ls) {
            if (l.trim().equalsIgnoreCase(lang)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String ferramentasStr =
                ferramentas.isEmpty() ? "No tools" : getFerramentasAsString();

        String estadoStr =
                eliminado ? "Derrotado" :
                        isPreso() ? "Preso" : "Em Jogo";

        return "ID: " + id +
                " | Nome: " + nome +
                " | Posição: " + posicao +
                " | Ferramentas: " + ferramentasStr +
                " | Linguagens: " + linguagens +
                " | Estado: " + estadoStr;
    }

    public String getFerramentasAsString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ferramentas.size(); i++) {
            sb.append(GameManager.toolName(ferramentas.get(i)));
            if (i < ferramentas.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
}
