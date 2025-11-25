package pt.ulusofona.lp2.greatprogrammingjourney;

public class ToolExceptionHandling extends ToolBase {
    public ToolExceptionHandling(int position) { super(3, position); }

    @Override
    public String getName() { return "Tratamento de Excepções"; }

    @Override
    public String applyEffect(Player p, GameManager gm) {
        p.addTool(3);
        return "Jogador agarrou Tratamento de Excepções";
    }
}
