package pt.ulusofona.lp2.greatprogrammingjourney;

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
        player.setPreso(true);
        return "Caiu no abismo Infinite Loop: jogador preso.";
    }
}