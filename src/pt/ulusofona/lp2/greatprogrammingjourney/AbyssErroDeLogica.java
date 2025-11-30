package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbyssErroDeLogica extends AbyssBase {
    public AbyssErroDeLogica(int position) { super(1, position); }

    @Override
    public String getName() { return "Logic Error"; }

    @Override
    public String applyEffect(Player player, GameManager manager) {
        if (playerHasTool(player, 1)) {
            consumeTool(player, 1);
            return "Logic Error anulado por " + toolName(1);
        }
        int move = player.getLastMoveSpaces() / 2;
        int novaPosicao = Math.max(1, player.getPosicao() - move);
        player.setPosicaoSemGuardarHistorico(novaPosicao);
        return "Caiu no abismo Logic Error: recuou " + move + " casas.";
    }
}