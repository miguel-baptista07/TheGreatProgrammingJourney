package pt.ulusofona.lp2.greatprogrammingjourney;

public abstract class AbyssBase extends BoardElement {
    public AbyssBase(int id, int position) {
        super(id, position);
    }

    @Override
    public boolean isAbyss() {
        return true;
    }

    protected boolean playerHasTool(Player p, int toolId) {
        return p.getFerramentas().contains(toolId);
    }

    protected void consumeTool(Player p, int toolId) {
        p.getFerramentas().removeIf(t -> t == toolId);
    }

    protected String toolName(int toolId) {
        return GameManager.toolName(toolId);
    }
}
