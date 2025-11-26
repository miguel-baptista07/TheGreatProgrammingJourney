package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player {
    private final String id;
    private String nome;
    private String linguagens;
    private String cor;
    private int posicao;
    private int posicaoAnteriorMovimento;
    private final List<Integer> ferramentas;
    private Integer ferramentaAtiva;
    private boolean eliminado;
    private boolean preso;
    private int lastMoveSpaces;
    private final List<Integer> posicaoHistorico;

    public Player(String id, String nome, String linguagens, String cor) {
        this.id = id;
        this.nome = nome;
        this.linguagens = formatarLinguagens(linguagens);
        this.cor = cor;
        this.posicao = 1;
        this.posicaoAnteriorMovimento = 1;
        this.ferramentas = new ArrayList<>();
        this.ferramentaAtiva = null;
        this.eliminado = false;
        this.preso = false;
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
    public String getCor() { return cor; }
    public int getPosicao() { return posicao; }

    public void setPosicao(int posicao) {
        if (posicao < 1) {
            posicao = 1;
        }
        this.posicaoAnteriorMovimento = this.posicao;
        this.posicao = posicao;
        this.posicaoHistorico.add(this.posicao);
        if (this.posicaoHistorico.size() > 10) {
            this.posicaoHistorico.remove(0);
        }
    }

    public void prepararMovimento() {
        this.posicaoAnteriorMovimento = this.posicao;
    }

    public int getPosicaoAnteriorMovimento() { return posicaoAnteriorMovimento; }

    public boolean hasTool(int toolId) { return ferramentas.contains(toolId); }

    public void addTool(int toolId) {
        if (!ferramentas.contains(toolId)) {
            ferramentas.add(toolId);
        }
    }

    // NOVO MÉTODO: Remover ferramenta
    public void removeTool(int toolId) {
        ferramentas.remove(Integer.valueOf(toolId));
    }

    public List<Integer> getFerramentas() { return new ArrayList<>(ferramentas); }

    public Integer getFerramentaAtiva() { return ferramentaAtiva; }

    public void setFerramentaAtiva(Integer ferramentaAtiva) { this.ferramentaAtiva = ferramentaAtiva; }

    public boolean isEliminado() { return eliminado; }

    public void setEliminado(boolean eliminado) { this.eliminado = eliminado; }

    public boolean isPreso() { return preso; }

    public void setPreso(boolean preso) { this.preso = preso; }

    public void setLastMoveSpaces(int lastMoveSpaces) { this.lastMoveSpaces = lastMoveSpaces; }

    public int getLastMoveSpaces() { return lastMoveSpaces; }

    public String getFerramentasAsString() {
        if (ferramentas.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ferramentas.size(); i++) {
            sb.append(GameManager.toolName(ferramentas.get(i)));
            if (i < ferramentas.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
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
}