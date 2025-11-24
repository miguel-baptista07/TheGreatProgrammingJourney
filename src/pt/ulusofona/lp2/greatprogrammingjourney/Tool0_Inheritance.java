package pt.ulusofona.lp2.greatprogrammingjourney;

public class Tool0_Inheritance extends ToolBase {
    public Tool0_Inheritance(int position) { super(0, position); }

    @Override
    public String getName() { return "Herança"; }

    @Override
    public String applyEffect(Player p, GameManager gm) {
        p.addTool(0);
        return "Jogador agarrou Herança";
    }
}
