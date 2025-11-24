package pt.ulusofona.lp2.greatprogrammingjourney;

public class Abyss5_DuplicatedCode extends AbyssBase {
    public Abyss5_DuplicatedCode(int position) { super(5, position); }

    @Override
    public String getName() { return "Duplicated Code"; }

    @Override
    public String applyEffect(Player player, GameManager manager) {
        if (playerHasTool(player, 4)) {
            consumeTool(player, 4);
            return "Duplicated Code anulado por " + toolName(4);
        }
        player.setPosicao(player.getPosicaoAnteriorMovimento());
        return "Caiu no abismo Duplicated Code: recuou para a casa anterior.";
    }
}
