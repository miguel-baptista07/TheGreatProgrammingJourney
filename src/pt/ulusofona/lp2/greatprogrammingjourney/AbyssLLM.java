package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbyssLLM extends AbyssBase {

    public AbyssLLM(int position) {
        super(20, position);
    }

    @Override
    public String getName() {
        return "LLM";
    }

    @Override
    public String applyEffect(Player player, GameManager manager) {
        if (player == null || manager == null) {
            return "Erro: abismo LLM";
        }

        int currentTurn = manager.getTurnCounter();
        int posicaoAtual = player.getPosicao();

        // Rodadas 1-3: RECUA o número de casas que avançou
        if (currentTurn <= 3) {
            int ultimoMovimento = player.getLastMoveSpaces();

            if (ultimoMovimento <= 0) {
                ultimoMovimento = 1;
            }

            // Recua o número de casas do movimento
            int novaPosicao = posicaoAtual - ultimoMovimento;

            if (novaPosicao < 1) {
                novaPosicao = 1;
            }

            player.setPosicaoSemGuardarHistorico(novaPosicao);
            return "Caiu no LLM! Recua para a posição onde estava antes";

        } else {
            // Rodada 4+: AVANÇA
            int ultimoMovimento = player.getLastMoveSpaces();

            if (ultimoMovimento <= 0) {
                ultimoMovimento = 1;
            }

            int novaPosicao = posicaoAtual + ultimoMovimento;
            if (novaPosicao < 1) {
                novaPosicao = 1;
            }

            player.setPosicaoSemGuardarHistorico(novaPosicao);
            return "Caiu no LLM mas já tem experiência! Avança tantas casas quantas as do último movimento";
        }
    }
}