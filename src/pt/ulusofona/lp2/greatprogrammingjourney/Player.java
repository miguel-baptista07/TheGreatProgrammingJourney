package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.Arrays;

public class Player {
    private String id;
    private String nome;
    private String linguagens;
    private String cor;
    private int posicao;

    public Player(String id, String nome, String linguagens, String cor) {
        this.id = id;
        this.nome = nome;
        this.linguagens = formatarLinguagens(linguagens);
        this.cor = cor;
        this.posicao = 1;
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

    public String getLinguagens() {
        return linguagens;
    }

    public String getCor() {
        return cor;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLinguagens(String linguagens) {
        this.linguagens = formatarLinguagens(linguagens);
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", linguagens='" + linguagens + '\'' +
                ", cor='" + cor + '\'' +
                ", posicao=" + posicao +
                '}';
    }
}