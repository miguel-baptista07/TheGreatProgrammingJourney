package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.List;

public class AbyssCicloInfinito extends AbyssBase {
    public AbyssCicloInfinito(int position) {
        super(8, position);
    }

    @Override
    public String getName() {
        return "Infinite Loop";
    }

    @Override
    public String applyEffect(Player player, GameManager manager) {

        if (playerHasTool(player, 1)) {
            consumeTool(player, 1);
            return "Infinite Loop anulado por " + toolName(1);
        }


        List<Player> playersAqui = manager.getPlayersAtPosition(position);
        for (Player other : playersAqui) {
            if (!other.getId().equals(player.getId()) && other.isPreso()) {
                other.setPreso(false);
            }
        }


        player.setPreso(true);
        return "Caiu no abismo Infinite Loop: jogador preso.";
    }
}