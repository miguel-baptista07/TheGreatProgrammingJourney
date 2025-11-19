package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbyssBSOD extends Cell {

    public AbyssBSOD(int pos) {
        super(7, pos);
    }

    @Override
    public String getImagePng() {
        return "bsod.png";
    }

    @Override
    public String react(Player p, GameManager gm) {
        p.eliminar();
        return "O jogador foi eliminado";
    }
}
