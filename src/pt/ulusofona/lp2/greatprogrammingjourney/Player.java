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
    private final ArrayList<Integer> historicoPosicoes = new ArrayList<>(); // NOVO!

    public Player(String id, String nome, String linguagens, String cor) {
        this.id = id;
        this.nome = nome;
        this.cor = cor;
        this.linguagens = formatarLinguagens(linguagens);
        historicoPosicoes.add(1); // Começa na posição 1
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

    // MODIFICADO: Registar histórico quando muda posição
    public void setPosicao(int pos) {
        if (pos < 1) pos = 1;
        this.posicao = pos;
        historicoPosicoes.add(pos);

        // Manter apenas últimas 10 posições para não gastar memória
        if (historicoPosicoes.size() > 10) {
            historicoPosicoes.remove(0);
        }
    }

    public void eliminar() {
        estado = PlayerState.DERROTADO;
    }

    public void prender() {
        estado = PlayerState.PRESO;
    }

    public void libertar() {
        if (estado == PlayerState.PRESO) {
            estado = PlayerState.EM_JOGO;
        }
    }

    // CORRIGIDO: Usar histórico real
    public int posAnterior() {
        if (historicoPosicoes.size() >= 2) {
            return historicoPosicoes.get(historicoPosicoes.size() - 2);
        }
        return Math.max(1, posicao - 1);
    }

    // CORRIGIDO: Usar histórico real
    public int posDuasAntes() {
        if (historicoPosicoes.size() >= 3) {
            return historicoPosicoes.get(historicoPosicoes.size() - 3);
        }
        return Math.max(1, posicao - 2);
    }

    public void addTool(int id) {
        if (!tools.contains(id)) {
            tools.add(id);
        }
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

    public String getFirstLanguage() {
        if (linguagens == null || linguagens.isEmpty()) {
            return "";
        }
        if (!linguagens.contains(";")) {
            return linguagens;
        }
        return linguagens.split(";")[0].trim();
    }

    public String toInfoString() {
        return id + " | " + nome + " | " + posicao + " | " +
                toolsAsString() + " | " + linguagens + " | " +
                (estado == PlayerState.EM_JOGO ? "Em Jogo" :
                        estado == PlayerState.PRESO ? "Preso" : "Derrotado");
    }
}