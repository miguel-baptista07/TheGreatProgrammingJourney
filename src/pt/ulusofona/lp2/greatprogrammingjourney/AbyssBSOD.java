package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbyssBSOD extends AbyssBase {
    public AbyssBSOD(int position) { super(7, position); }

    @Override
    public String getName() { return "BSOD"; }

    @Override
    public String applyEffect(Player player, GameManager manager) {
        manager.eliminatePlayer(player);
        return "Caiu no abismo BSOD: jogador derrotado.";
    }
}
