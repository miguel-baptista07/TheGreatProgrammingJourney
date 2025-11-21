package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.List;

/**
 * Abismo Logical Error (id 1)
 * Efeito: Jogador volta metade do movimento
 */
public class LogicalError extends Abismo {

    public LogicalError(int posicao) {
        super(1, posicao);
    }

    @Override
    public String getNome() {
        return "Logical Error";
    }

    @Override
    public String aplicarEfeito(Player player, List<Player> allPlayers, int boardSize) {
        int posicaoAnterior = player.getPosicaoAnterior();
        int posicaoAtual = player.getPosicao();
        int movimento = posicaoAtual - posicaoAnterior;
        int metadeMovimento = movimento / 2;
        int novaPosicao = validarPosicao(posicaoAtual - metadeMovimento, boardSize);
        player.setPosicao(novaPosicao);
        return player.getNome() + " caiu em Logical Error e voltou metade do movimento (posição " + novaPosicao + ")";
    }

    @Override
    public boolean aceitaNeutralizacaoDe(Ferramenta ferramenta) {
        return ferramenta.podeNeutralizar(this);
    }
}
