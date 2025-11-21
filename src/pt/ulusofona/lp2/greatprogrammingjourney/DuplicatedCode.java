package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.List;

/**
 * Abismo Duplicated Code (id 5)
 * Efeito: Jogador volta à posição anterior
 */
public class DuplicatedCode extends Abismo {

    public DuplicatedCode(int posicao) {
        super(5, posicao);
    }

    @Override
    public String getNome() {
        return "Duplicated Code";
    }

    @Override
    public String aplicarEfeito(Player player, List<Player> allPlayers, int boardSize) {
        int posicaoAnterior = player.getPosicaoAnterior();
        player.setPosicao(posicaoAnterior);
        return player.getNome() + " caiu em Duplicated Code e voltou à posição anterior (" + posicaoAnterior + ")";
    }

    @Override
    public boolean aceitaNeutralizacaoDe(Ferramenta ferramenta) {
        return ferramenta.podeNeutralizar(this);
    }
}
