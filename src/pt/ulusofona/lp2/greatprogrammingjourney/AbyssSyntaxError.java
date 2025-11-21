package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbyssSyntaxError extends Cell {

    public AbyssSyntaxError(int pos) {
        super(0, pos);
    }

    @Override
    public String getImagePng() {
        return "syntax_error.png";
    }

    @Override
    public String react(Player p, GameManager gm) {
        p.setPosicao(p.posAnterior());
        return "Erro de sintaxe";
    }
}
