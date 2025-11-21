package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.List;

/**
 * Abismo Crash (id 4)
 * Efeito: Jogador vai para posição 1
 */
public class Crash extends Abismo {

    public Crash(int posicao) {
        super(4, posicao);
    }

    @Override
    public String getNome() {
        return "Crash";
    }

    @Override
    public String aplicarEfeito(Player player, List<Player> allPlayers, int boardSize) {
        player.setPosicao(1);
        return player.getNome() + " caiu em Crash e voltou para a posição 1";
    }

    @Override
    public boolean aceitaNeutralizacaoDe(Ferramenta ferramenta) {
        return ferramenta.podeNeutralizar(this);
    }
}
