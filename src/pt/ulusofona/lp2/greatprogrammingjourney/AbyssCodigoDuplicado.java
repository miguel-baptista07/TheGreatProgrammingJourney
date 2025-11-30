package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbyssCodigoDuplicado extends AbyssBase {
    public AbyssCodigoDuplicado(int position) { super(5, position); }

    @Override
    public String getName() { return "Duplicated Code"; }

    @Override
    public String applyEffect(Player player, GameManager manager) {
        if (playerHasTool(player, 4)) {
            consumeTool(player, 4);
            return "Duplicated Code anulado por " + toolName(4);
        }
        player.setPosicaoSemGuardarHistorico(player.getPosicaoAnteriorMovimento());
        return "Caiu no abismo Duplicated Code: recuou para a casa anterior.";
    }
}
