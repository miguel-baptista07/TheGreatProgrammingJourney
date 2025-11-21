package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbyssSideEffects extends Cell {

    public AbyssSideEffects(int pos) {
        super(6, pos);
    }

    @Override
    public String getImagePng() {
        return "side_effects.png";
    }

    @Override
    public String react(Player p, GameManager gm) {
        p.setPosicao(p.posDuasAntes());
        return "Efeitos secundários";
    }
}
