package pt.ulusofona.lp2.greatprogrammingjourney;

public class GloryCell extends Cell {

    public GloryCell(int posicao) {
        super(999, posicao);
    }

    @Override
    public String getImagePng() {
        return "glory.png";
    }

    @Override
    public String react(Player p, GameManager gm) {
        return "Nada aconteceu"; // a vitória é tratada por GameStatus
    }
}
