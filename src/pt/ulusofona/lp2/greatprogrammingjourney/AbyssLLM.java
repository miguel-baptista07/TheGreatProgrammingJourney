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

        int currentTurn;
        int posicaoAtual;
        
        try {
            currentTurn = manager.getTurnCounter();
            posicaoAtual = player.getPosicao();
        } catch (Exception e) {
            // Se não conseguir obter turn ou posição, tenta voltar para posição anterior
            int posicaoAnterior = player.getPosicaoAnteriorMovimento();
            if (posicaoAnterior < 1) {
                posicaoAnterior = 1;
            }
            player.setPosicaoSemGuardarHistorico(posicaoAnterior);
            return "Caiu no abismo LLM";
        }

        // Rodada 4+: AVANÇA
        if (currentTurn >= 4) {
            int ultimoMovimento = 0;
            try {
                ultimoMovimento = player.getLastMoveSpaces();
            } catch (Exception e) {
                // Continua com fallback
            }
            
            if (ultimoMovimento <= 0) {
                ultimoMovimento = 1; // Fallback
            }

            int novaPosicao = posicaoAtual + ultimoMovimento;
            if (novaPosicao < 1) {
                novaPosicao = 1;
            }

            player.setPosicaoSemGuardarHistorico(novaPosicao);
            return "Caiu no LLM mas já tem experiência! Avança " + ultimoMovimento + " casas";

        } else {
            // Rodadas 1-3: RECUA para a posição anterior
            int posicaoAnterior = player.getPosicaoAnteriorMovimento();
            if (posicaoAnterior < 1) {
                posicaoAnterior = 1;
            }

            player.setPosicaoSemGuardarHistorico(posicaoAnterior);
            return "Caiu no abismo LLM: recua 2 casas (rodada " + currentTurn + ")";
        }
    }
}