package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.List;

/**
 * Abismo Infinite Loop (id 8)
 * Efeito: Jogador fica PRESO (não se move até sair)
 */
public class InfiniteLoop extends Abismo {

    public InfiniteLoop(int posicao) {
        super(8, posicao);
    }

    @Override
    public String getNome() {
        return "Infinite Loop";
    }

    @Override
    public String aplicarEfeito(Player player, List<Player> allPlayers, int boardSize) {
        player.setEstado(PlayerState.PRESO);
        return player.getNome() + " caiu em Infinite Loop e ficou PRESO!";
    }

    @Override
    public boolean aceitaNeutralizacaoDe(Ferramenta ferramenta) {
        return ferramenta.podeNeutralizar(this);
    }

    @Override
    public String getImagemPng() {
        return "infinite-loop.png";
    }
}
