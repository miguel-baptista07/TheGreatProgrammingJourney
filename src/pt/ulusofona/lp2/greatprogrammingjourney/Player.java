package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.ArrayList;
import java.util.Arrays;

public class Player {

    private String id;
    private String nome;
    private String linguagens;
    private String cor;
    private int posicao = 1;
    private PlayerState estado = PlayerState.EM_JOGO;
    private final ArrayList<Integer> tools = new ArrayList<>();

    public Player(String id, String nome, String linguagens, String cor) {
        this.id = id;
        this.nome = nome;
        this.cor = cor;
        this.linguagens = formatarLinguagens(linguagens);
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

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCor() {
        return cor;
    }

    public int getPosicao() {
        return posicao;
    }

    public PlayerState getEstado() {
        return estado;
    }

    public void setPosicao(int pos) {
        this.posicao = pos;
    }

    public void eliminar() {
        estado = PlayerState.DERROTADO;
    }

    public void prender() {
        estado = PlayerState.PRESO;
    }

    public int posAnterior() {
        return Math.max(1, posicao - 1);
    }

    public int posDuasAntes() {
        return Math.max(1, posicao - 2);
    }

    public void addTool(int id) {
        tools.add(id);
    }

    public boolean hasTool(int id) {
        return tools.contains(id);
    }

    public boolean hasAnyToolForAbyss(int... ids) {
        for (int t : tools) {
            for (int x : ids) {
                if (t == x) {
                    return true;
                }
            }
        }
        return false;
    }

    public String toolsAsString() {
        if (tools.isEmpty()) {
            return "No tools";
        }
        ArrayList<String> nomes = new ArrayList<>();
        for (int t : tools) {
            nomes.add(ToolFactory.getToolName(t));
        }
        return String.join(", ", nomes);
    }

    public String linguagensAsString() {
        return linguagens;
    }

    public String toInfoString() {
        return id + " | " + nome + " | " + posicao + " | " +
                toolsAsString() + " | " + linguagens + " | " +
                (estado == PlayerState.EM_JOGO ? "Em Jogo" :
                        estado == PlayerState.PRESO ? "Preso" : "Derrotado");
    }
}
