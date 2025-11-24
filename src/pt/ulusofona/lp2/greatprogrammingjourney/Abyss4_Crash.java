package pt.ulusofona.lp2.greatprogrammingjourney;

public class Abyss4_Crash extends AbyssBase {
    public Abyss4_Crash(int position) { super(4, position); }

    @Override
    public String getName() { return "Crash"; }

    @Override
    public String applyEffect(Player player, GameManager manager) {
        if (playerHasTool(player, 5)) {
            consumeTool(player, 5);
            return "Crash anulado por " + toolName(5);
        }
        player.setPosicao(1);
        return "Caiu no abismo Crash: voltou à primeira casa.";
    }
}
