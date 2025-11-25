package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbyssFileNotFound extends AbyssBase {
    public AbyssFileNotFound(int position) { super(3, position); }

    @Override
    public String getName() { return "File Not Found"; }

    @Override
    public String applyEffect(Player player, GameManager manager) {
        if (playerHasTool(player, 3)) {
            consumeTool(player, 3);
            return "File Not Found anulado por " + toolName(3);
        }
        player.setPosicao(Math.max(1, player.getPosicao() - 3));
        return "Caiu no abismo File Not Found: recuou 3 casas.";
    }
}
