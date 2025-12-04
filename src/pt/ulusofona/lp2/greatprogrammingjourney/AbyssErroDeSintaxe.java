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
        // Sem ferramenta IDE: o jogador recua 2 casas a partir da posição atual
        // (regra usada nos testes do docente).
        int novaPosicao = player.getPosicao() - 2;
        if (novaPosicao < 1) {
            novaPosicao = 1;
        }
        player.setPosicaoSemGuardarHistorico(novaPosicao);
        return "Caiu no abismo Erro de sintaxe: recuou 2 casas.";
    }
}