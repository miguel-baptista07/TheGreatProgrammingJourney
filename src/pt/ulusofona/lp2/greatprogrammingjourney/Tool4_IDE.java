package pt.ulusofona.lp2.greatprogrammingjourney;

public class Tool4_IDE extends ToolBase {
    public Tool4_IDE(int position) { super(4, position); }

    @Override
    public String getName() { return "IDE"; }

    @Override
    public String applyEffect(Player p, GameManager gm) {
        p.addTool(4);
        return "Jogador agarrou IDE";
    }
}
