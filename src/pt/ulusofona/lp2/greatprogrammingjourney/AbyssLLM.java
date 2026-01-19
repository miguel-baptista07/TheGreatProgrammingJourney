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
        // Proteção: nunca null!
        if (player == null || manager == null) {
            return "Erro: abismo LLM";
        }

        try {
            int currentTurn = manager.getTurnCounter();
            int posicaoAtual = player.getPosicao();

            // Rodada 4+: AVANÇA
            if (currentTurn >= 4) {
                int ultimoMovimento = player.getLastMoveSpaces();
                if (ultimoMovimento <= 0) {
                    ultimoMovimento = 1; // Fallback
                }

                int novaPosicao = posicaoAtual + ultimoMovimento;
                if (novaPosicao < 1) {
                    novaPosicao = 1;
                }

                player.setPosicao(novaPosicao);
                return "Caiu no LLM mas já tem experiência! Avança " + ultimoMovimento + " casas";

            } else {
                // Rodadas 1-3: RECUA
                int novaPosicao = posicaoAtual - 2;
                if (novaPosicao < 1) {
                    novaPosicao = 1;
                }

                player.setPosicao(novaPosicao);
                return "Caiu no abismo LLM: recua 2 casas (rodada " + currentTurn + ")";
            }

        } catch (Exception e) {
            // Fallback final - NUNCA retorna null!
            return "Caiu no abismo LLM";
        }
    }
}