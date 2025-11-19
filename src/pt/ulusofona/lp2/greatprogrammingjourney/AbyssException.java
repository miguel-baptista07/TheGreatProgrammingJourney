package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbyssException extends Cell {

    public AbyssException(int pos) {
        super(2, pos);
    }

    @Override
    public String getImagePng() {
        return "exception.png";
    }

    @Override
    public String react(Player p, GameManager gm) {
        p.setPosicao(p.getPosicao() - 2);
        return "O jogador caiu num abismo";
    }
}
