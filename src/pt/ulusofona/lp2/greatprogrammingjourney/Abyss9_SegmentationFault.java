package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.List;

public class Abyss9_SegmentationFault extends AbyssBase {
    public Abyss9_SegmentationFault(int position) { super(9, position); }

    @Override
    public String getName() { return "Segmentation Fault"; }

    @Override
    public String applyEffect(Player player, GameManager manager) {
        if (playerHasTool(player, 0)) {
            consumeTool(player, 0);
            return "Segmentation Fault anulado por " + toolName(0);
        }
        List<Player> same = manager.getPlayersAtPosition(position);
        for (Player p : same) {
            if (!p.isEliminado()) {
                p.setPosicao(Math.max(1, p.getPosicao() - 3));
            }
        }
        return "Caiu no abismo Segmentation Fault: todos na casa recuaram 3 casas.";
    }
}
