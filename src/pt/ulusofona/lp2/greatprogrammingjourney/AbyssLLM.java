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
        int currentTurn = manager.getTurnCounter();

        // A partir da rodada 4: jogador tem experiência e AVANÇA
        if (currentTurn >= 4) {
            int ultimoMovimento = player.getLastMoveSpaces();
            int novaPosicao = player.getPosicao() + ultimoMovimento;

            if (novaPosicao < 1) {
                novaPosicao = 1;
            }

            player.setPosicao(novaPosicao);
            return "Caiu no LLM mas já tem experiência! Avança " + ultimoMovimento + " casas";
        } else {
            // Rodadas 1-3: sem experiência, recua 2 casas
            int novaPosicao = player.getPosicao() - 2;
            if (novaPosicao < 1) {
                novaPosicao = 1;
            }
            player.setPosicao(novaPosicao);
            return "Caiu no abismo LLM: recua 2 casas (rodada " + currentTurn + ")";
        }
    }
}