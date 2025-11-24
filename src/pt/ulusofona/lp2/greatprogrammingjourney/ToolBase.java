package pt.ulusofona.lp2.greatprogrammingjourney;

public abstract class ToolBase extends BoardElement {
    public ToolBase(int id, int position) { super(id, position); }

    @Override
    public boolean isAbyss() { return false; }
}
