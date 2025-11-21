package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbyssLogicError extends Cell {

    public AbyssLogicError(int pos) {
        super(1, pos);
    }

    @Override
    public String getImagePng() {
        return "logic_error.png";
    }

    @Override
    public String react(Player p, GameManager gm) {
        p.setPosicao(p.posAnterior());
        return "Erro lógico";
    }
}
