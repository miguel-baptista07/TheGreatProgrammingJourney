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
        player.prender(1);
        return player.getNome() + " ficou preso num Infinite Loop!";
    }
}