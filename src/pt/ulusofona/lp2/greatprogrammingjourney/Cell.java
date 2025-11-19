package pt.ulusofona.lp2.greatprogrammingjourney;

public abstract class Cell {

    protected final int id;
    protected final int posicao;
    public Cell(int id, int posicao) {
        this.id = id;
        this.posicao = posicao;
    }

    public int getId() {
        return id;
    }

    public int getPosicao() {
        return posicao;
    }


    public abstract String getImagePng();


    public abstract String react(Player p, GameManager gm);
}
