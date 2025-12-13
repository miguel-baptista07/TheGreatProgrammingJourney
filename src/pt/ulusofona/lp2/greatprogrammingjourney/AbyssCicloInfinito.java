package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.Collections;

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

        // Count how many IDE tools (id 4) the player has
        int ideCount = Collections.frequency(player.getFerramentas(), 4);
        
        // If player has IDE tools, imprison for that many turns
        // Otherwise, imprison for 3 turns
        int turnos = ideCount > 0 ? ideCount : 3;
        player.prender(turnos);

        return null;
    }
}
