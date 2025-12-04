package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbyssErroDeSintaxe extends AbyssBase {
    public AbyssErroDeSintaxe(int position) { super(0, position); }

    @Override
    public String getName() { return "Erro de sintaxe"; }

    @Override
    public String applyEffect(Player player, GameManager manager) {
        if (playerHasTool(player, 4)) {
            consumeTool(player, 4);
            return "Erro de sintaxe anulado por " + toolName(4);
        }
        // Sem IDE, o jogador recua 1 casa a partir da posição atual
        int novaPosicao = player.getPosicao() - 1;
        if (novaPosicao < 1) {
            novaPosicao = 1;
        }
        player.setPosicaoSemGuardarHistorico(novaPosicao);
        return "Caiu no abismo Erro de sintaxe: recuou 1 casa.";
    }
}