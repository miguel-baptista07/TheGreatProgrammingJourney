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

    public String applyEffect(Player player, GameManager manager) {
        if (playerHasTool(player, 1)) {
            consumeTool(player, 1);
            return "Infinite Loop anulado por " + toolName(1);
        }

        // ✅ Prende por 1 turno (não 3!)
        player.prender(1);

        return null;
    }
}
