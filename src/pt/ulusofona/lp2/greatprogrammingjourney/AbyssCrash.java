package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbyssCrash extends AbyssBase {
    public AbyssCrash(int position) { super(4, position); }

    @Override
    public String getName() { return "Crash"; }

    @Override
    public String applyEffect(Player player, GameManager manager) {
        player.setPosicaoSemGuardarHistorico(1);
        return "Caiu no abismo Crash: voltou à primeira casa.";
    }
}