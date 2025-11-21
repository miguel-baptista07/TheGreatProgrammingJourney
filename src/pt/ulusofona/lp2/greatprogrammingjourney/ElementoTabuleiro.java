package pt.ulusofona.lp2.greatprogrammingjourney;

/**
 * Classe abstrata que representa um elemento do tabuleiro.
 * Serve como classe base para Abismos e Ferramentas.
 */
public abstract class ElementoTabuleiro {
    protected int id;
    protected int posicao;

    public ElementoTabuleiro(int id, int posicao) {
        this.id = id;
        this.posicao = posicao;
    }

    public int getId() {
        return id;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    /**
     * Retorna o tipo do elemento para getSlotInfo
     * @return "A" para abismo, "T" para ferramenta
     */
    public abstract String getTipo();

    /**
     * Retorna a representação do elemento para getSlotInfo
     * @return formato "A:id" ou "T:id"
     */
    public String getRepresentacao() {
        return getTipo() + ":" + id;
    }
}
