package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbyssEfeitosSecundarios extends AbyssBase {
    public AbyssEfeitosSecundarios(int position) { super(6, position); }

    @Override
    public String getName() { return "Side Effects"; }

    @Override
    public String applyEffect(Player player, GameManager manager) {
        if (playerHasTool(player, 1)) {
            consumeTool(player, 1);
            return "Side Effects anulado por " + toolName(1);
        }


        int backPos = player.getHistoricalPosition(2);
        player.setPosicaoSemGuardarHistorico(Math.max(1, backPos));
        return "Caiu no abismo Side Effects: recuou para a posição de 2 movimentos atrás.";
    }
}