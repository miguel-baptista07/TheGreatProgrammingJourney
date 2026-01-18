package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbyssLLM extends AbyssBase {

    private static final int AJUDA_DO_PROFESSOR = 5; // confirma se este é o ID correto

    public AbyssLLM(int position) {
        super(20, position);
    }

    @Override
    public String getName() {
        return "LLM";
    }

    @Override
    public String applyEffect(Player player, GameManager manager) {

        boolean temAjuda = playerHasTool(player, AJUDA_DO_PROFESSOR);

        // Antes da 4ª ronda
        if (manager.getTurnCounter() < 3) {

            if (temAjuda) {
                consumeTool(player, AJUDA_DO_PROFESSOR);
                return "Caiu no LLM mas a Ajuda do Professor anulou o efeito.";
            }

            int posicaoAnterior = player.getPosicaoAnteriorMovimento();
            player.setPosicaoSemGuardarHistorico(posicaoAnterior);

            return "Caiu no LLM! Recuas para a posição onde estavas antes";
        }

        // A partir da 4ª ronda
        if (temAjuda) {
            consumeTool(player, AJUDA_DO_PROFESSOR);
        }

        int movimentoAnterior =
                player.getPosicao() - player.getPosicaoAnteriorMovimento();

        int novaPosicao = player.getPosicao() + movimentoAnterior;
        player.setPosicaoSemGuardarHistorico(novaPosicao);

        return "Caiu no LLM mas já tem experiência! Avança tantas casas quantas as do último movimento";
    }
}
