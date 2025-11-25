package pt.ulusofona.lp2.greatprogrammingjourney;

public class ToolInheritance extends ToolBase {
    public ToolInheritance(int position) { super(0, position); }

    @Override
    public String getName() { return "Herança"; }

    @Override
    public String applyEffect(Player p, GameManager gm) {
        p.addTool(0);
        return "Jogador agarrou Herança";
    }
}
