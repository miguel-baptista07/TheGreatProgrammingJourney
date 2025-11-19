package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.Arrays;
import java.util.List;

public class Player {

    private final String id;
    private final String nome;
    private final List<String> linguagensOrdenadas;
    private final String cor;

    private int posicao = 1;
    private PlayerState estado = PlayerState.EM_JOGO;

    private final PlayerHistory history = new PlayerHistory();
    private final ToolInventory inventory = new ToolInventory();

    public Player(String id, String nome, String linguagens, String cor) {
        this.id = id;
        this.nome = nome;
        this.cor = cor;
        List<String> langs = Arrays.asList(linguagens.split(";"));
        langs.replaceAll(String::trim);
        langs.sort(String::compareToIgnoreCase);
        this.linguagensOrdenadas = langs;

        history.record(posicao);
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getCor() { return cor; }
    public int getPosicao() { return posicao; }
    public PlayerState getEstado() { return estado; }

    public String getFirstLanguage() {
        return linguagensOrdenadas.isEmpty() ? "" : linguagensOrdenadas.get(0);
    }

    public void addTool(int toolId) {
        inventory.addTool(toolId);
    }

    public boolean hasTool(int toolId) {
        return inventory.hasTool(toolId);
    }

    public boolean hasAnyToolForAbyss(int... ids) {
        return inventory.hasAnyOf(ids);
    }

    public void consumeTool(int id) {
        inventory.removeTool(id);
    }

    public String toolsAsString() {
        return inventory.toStringList();
    }

    public void setPosicao(int novaPosicao) {
        this.posicao = Math.max(1, novaPosicao);
        history.record(this.posicao);
    }

    public int posAnterior() {
        return history.beforeLast();
    }

    public int posDuasAntes() {
        return history.twoBefore();
    }

    public void eliminar() {
        this.estado = PlayerState.DERROTADO;
    }

    public void prender() {
        this.estado = PlayerState.PRESO;
    }

    public void libertar() {
        this.estado = PlayerState.EM_JOGO;
    }

    public String linguagensAsString() {
        return String.join("; ", linguagensOrdenadas);
    }

    public String toInfoString() {
        return id + " | " + nome + " | " + posicao + " | " + toolsAsString() + " | " + linguagensAsString() + " | " + estadoString();
    }

    private String estadoString() {
        return switch (estado) {
            case EM_JOGO -> "Em Jogo";
            case PRESO -> "Preso";
            case DERROTADO -> "Derrotado";
        };
    }
}
