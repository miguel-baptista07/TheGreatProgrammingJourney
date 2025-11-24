package pt.ulusofona.lp2.greatprogrammingjourney;

public class Tool2_UnitTests extends ToolBase {
    public Tool2_UnitTests(int position) { super(2, position); }

    @Override
    public String getName() { return "Testes Unitários"; }

    @Override
    public String applyEffect(Player p, GameManager gm) {
        p.addTool(2);
        return "Jogador agarrou Testes Unitários";
    }
}
