package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbyssBSOD extends AbyssBase {
    public AbyssBSOD(int position) { super(7, position); }

    @Override
    public String getName() { return "Blue Screen of Death"; }

    @Override
    public String applyEffect(Player player, GameManager manager) {
        manager.eliminatePlayer(player);
        player.setCausaDerrota(getName());
        return "Caiu no abismo BSOD: jogador derrotado.";
    }
}
