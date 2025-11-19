package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbyssInfiniteLoop extends Cell {

    public AbyssInfiniteLoop(int pos) {
        super(8, pos);
    }

    @Override
    public String getImagePng() {
        return "infinite_loop.png";
    }

    @Override
    public String react(Player p, GameManager gm) {
        p.prender();
        return "O jogador caiu num abismo";
    }
}
