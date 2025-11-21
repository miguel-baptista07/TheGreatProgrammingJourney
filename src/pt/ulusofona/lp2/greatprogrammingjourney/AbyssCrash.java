package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbyssCrash extends Cell {

    public AbyssCrash(int pos) {
        super(4, pos);
    }

    @Override
    public String getImagePng() {
        return "crash.png";
    }

    @Override
    public String react(Player p, GameManager gm) {
        p.setPosicao(1);
        return "Crash do sistema";
    }
}
