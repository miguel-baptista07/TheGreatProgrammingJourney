package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbyssErroDeLógica extends AbyssBase {
    public AbyssErroDeLógica(int position) { super(1, position); }

    @Override
    public String getName() { return "Logic Error"; }

    @Override
    public String applyEffect(Player player, GameManager manager) {
        if (playerHasTool(player, 1)) {
            consumeTool(player, 1);
            return "Logic Error anulado por " + toolName(1);
        }
        int move = player.getLastMoveSpaces() / 2;
        player.setPosicao(Math.max(1, player.getPosicao() - move));
        return "Caiu no abismo Logic Error: recuou " + move + " casas.";
    }
}
