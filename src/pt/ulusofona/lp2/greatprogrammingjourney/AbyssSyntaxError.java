package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbyssSyntaxError extends AbyssBase {
    public AbyssSyntaxError(int position) { super(0, position); }

    @Override
    public String getName() { return "Syntax Error"; }

    @Override
    public String applyEffect(Player player, GameManager manager) {
        if (playerHasTool(player, 4)) {
            consumeTool(player, 4);
            return "Erro de sintaxe anulado por " + toolName(4);
        }
        player.setPosicao(player.getPosicaoAnteriorMovimento());
        return "Caiu no abismo Syntax Error: recuou para a casa onde estava antes.";
    }
}
