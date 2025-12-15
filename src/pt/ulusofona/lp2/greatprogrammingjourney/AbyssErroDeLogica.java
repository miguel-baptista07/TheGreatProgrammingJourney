package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbyssErroDeLogica extends AbyssBase {
    public AbyssErroDeLogica(int position) { super(1, position); }

    @Override
    public String getName() { return "Logic Error"; }

    @Override
    public String applyEffect(Player player, GameManager manager) {
        int move = player.getLastMoveSpaces() / 2;
        int novaPosicao = Math.max(1, player.getPosicao() - move);
        player.setPosicaoSemGuardarHistorico(novaPosicao);
        return "Caiu no abismo Logic Error: recuou " + move + " casas.";
    }
}