package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbyssErroDeSintaxe extends AbyssBase {
    public AbyssErroDeSintaxe(int position) { super(0, position); }

    @Override
    public String getName() { return "Erro de sintaxe"; }

    @Override
    public String applyEffect(Player player, GameManager manager) {
        int novaPosicao = Math.max(1, player.getPosicao() - 1);
        player.setPosicaoSemGuardarHistorico(novaPosicao);
        return "Caiu no abismo Erro de sintaxe: recuou 1 casa.";
    }

}