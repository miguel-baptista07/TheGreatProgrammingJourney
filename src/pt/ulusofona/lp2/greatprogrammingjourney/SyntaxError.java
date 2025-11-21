package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.List;

/**
 * Abismo Syntax Error (id 0)
 * Efeito: Jogador volta 1 casa
 */
public class SyntaxError extends Abismo {

    public SyntaxError(int posicao) {
        super(0, posicao);
    }

    @Override
    public String getNome() {
        return "Syntax Error";
    }

    @Override
    public String aplicarEfeito(Player player, List<Player> allPlayers, int boardSize) {
        int posicaoAtual = player.getPosicao();
        int novaPosicao = validarPosicao(posicaoAtual - 1, boardSize);
        player.setPosicao(novaPosicao);
        return player.getNome() + " caiu em Syntax Error e voltou 1 casa (posição " + novaPosicao + ")";
    }

    @Override
    public boolean aceitaNeutralizacaoDe(Ferramenta ferramenta) {
        return ferramenta.podeNeutralizar(this);
    }
}
