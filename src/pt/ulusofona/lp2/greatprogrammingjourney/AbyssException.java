package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbyssException extends AbyssBase {
    public AbyssException(int position) { super(2, position); }

    @Override
    public String getName() { return "Exception"; }

    @Override
    public String applyEffect(Player player, GameManager manager) {
        if (playerHasTool(player, 3)) {
            consumeTool(player, 3);
            return "Exception anulado por " + toolName(3);
        }
        int novaPosicao = Math.max(1, player.getPosicao() - 2);
        player.setPosicaoSemGuardarHistorico(novaPosicao);
        return "Caiu no abismo Exception: recuou 2 casas.";
    }
}