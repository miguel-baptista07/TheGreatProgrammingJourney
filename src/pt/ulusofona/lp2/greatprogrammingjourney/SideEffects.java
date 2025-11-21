package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.List;

/**
 * Abismo Side Effects (id 6)
 * Efeito: Jogador recua 2 casas
 */
public class SideEffects extends Abismo {

    public SideEffects(int posicao) {
        super(6, posicao);
    }

    @Override
    public String getNome() {
        return "Side Effects";
    }

    @Override
    public String aplicarEfeito(Player player, List<Player> allPlayers, int boardSize) {
        int posicaoAtual = player.getPosicao();
        int novaPosicao = validarPosicao(posicaoAtual - 2, boardSize);
        player.setPosicao(novaPosicao);
        return player.getNome() + " caiu em Side Effects e recuou 2 casas (posição " + novaPosicao + ")";
    }

    @Override
    public boolean aceitaNeutralizacaoDe(Ferramenta ferramenta) {
        return ferramenta.podeNeutralizar(this);
    }
}
