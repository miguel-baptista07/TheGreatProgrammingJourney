package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbyssInfiniteLoop extends Cell {

    public AbyssInfiniteLoop(int pos) {
        super(8, pos);
    }

    @Override
    public String getImagePng() {
        return "loop.png";
    }

    @Override
    public String react(Player p, GameManager gm) {
        p.prender();
        return "Loop infinito";
    }
}
