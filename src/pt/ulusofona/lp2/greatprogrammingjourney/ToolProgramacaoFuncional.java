package pt.ulusofona.lp2.greatprogrammingjourney;

public class ToolProgramacaoFuncional extends ToolBase {
    public ToolProgramacaoFuncional(int position) { super(1, position); }

    @Override
    public String getName() { return "Programação Funcional"; }

    @Override
    public String applyEffect(Player p, GameManager gm) {
        p.addTool(1);
        return "Jogador agarrou Programação Funcional";
    }
}
