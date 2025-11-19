package pt.ulusofona.lp2.greatprogrammingjourney;

public class NormalCell extends Cell {

    public NormalCell(int posicao) {
        super(0, posicao);
    }

    @Override
    public String getImagePng() {
        return null; // sem imagem
    }

    @Override
    public String react(Player p, GameManager gm) {
        return null;
    }
}
