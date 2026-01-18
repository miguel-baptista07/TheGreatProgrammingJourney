package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbyssCicloInfinito extends AbyssBase {
    public AbyssCicloInfinito(int position) {
        super(8, position);
    }

    @Override
    public String getName() {
        return "Ciclo Infinito";
    }

    @Override
    public String applyEffect(Player player, GameManager manager) {
        player.setPreso(true);
        player.setCausaDerrota(getName());
        return "Caiu no abismo Infinite Loop: jogador preso.";
    }
}