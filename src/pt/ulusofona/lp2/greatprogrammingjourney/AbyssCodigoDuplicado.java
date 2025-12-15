package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbyssCodigoDuplicado extends AbyssBase {
    public AbyssCodigoDuplicado(int position) { super(5, position); }

    @Override
    public String getName() { return "Duplicated Code"; }

    @Override
    public String applyEffect(Player player, GameManager manager) {
        player.setPosicaoSemGuardarHistorico(player.getPosicaoAnteriorMovimento());
        return "Caiu no abismo Duplicated Code: recuou para a casa anterior.";
    }

}
