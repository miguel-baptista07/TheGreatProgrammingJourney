package pt.ulusofona.lp2.greatprogrammingjourney;

public class Abyss2_Exception extends AbyssBase {
    public Abyss2_Exception(int position) { super(2, position); }

    @Override
    public String getName() { return "Exception"; }

    @Override
    public String applyEffect(Player player, GameManager manager) {
        if (playerHasTool(player, 3)) {
            consumeTool(player, 3);
            return "Exception anulado por " + toolName(3);
        }
        player.setPosicao(Math.max(1, player.getPosicao() - 2));
        return "Caiu no abismo Exception: recuou 2 casas.";
    }
}
