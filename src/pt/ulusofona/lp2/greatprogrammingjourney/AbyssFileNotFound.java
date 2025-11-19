package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbyssFileNotFound extends Cell {

    public AbyssFileNotFound(int pos) {
        super(3, pos);
    }

    @Override
    public String getImagePng() {
        return "file_not_found.png";
    }

    @Override
    public String react(Player p, GameManager gm) {
        p.setPosicao(p.getPosicao() - 3);
        return "O jogador caiu num abismo";
    }
}
