package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.List;

public class AbyssSegmentationFault extends AbyssBase {
    public AbyssSegmentationFault(int position) { super(9, position); }

    @Override
    public String getName() { return "Segmentation Fault"; }

    @Override
    public String applyEffect(Player player, GameManager manager) {
        if (playerHasTool(player, 0)) {
            consumeTool(player, 0);
            return "Segmentation Fault anulado por " + toolName(0);
        }

        List<Player> playersHere = manager.getPlayersAtPosition(position);

        if (playersHere.size() < 2) {
            return "Caiu no abismo Segmentation Fault, mas está sozinho (nada acontece).";
        }

        for (Player p : playersHere) {
            if (!p.isEliminado()) {
                int novaPosicao = Math.max(1, p.getPosicao() - 3);
                p.setPosicaoSemGuardarHistorico(novaPosicao);
            }
        }
        return "Caiu no abismo Segmentation Fault: todos na casa recuaram 3 casas.";
    }
}