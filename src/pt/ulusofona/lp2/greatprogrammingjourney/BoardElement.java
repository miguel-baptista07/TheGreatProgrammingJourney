package pt.ulusofona.lp2.greatprogrammingjourney;

public abstract class BoardElement {
    protected int id;
    protected int position;

    public BoardElement(int id, int position) {
        this.id = id;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public int getPosition() {
        return position;
    }

    public abstract boolean isAbyss();

    public abstract String getName();

    public abstract String applyEffect(Player player, GameManager manager);
}