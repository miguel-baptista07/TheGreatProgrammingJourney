package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.List;

/**
 * Abismo BSOD - Blue Screen of Death (id 7)
 * Efeito: Jogador é ELIMINADO do jogo
 */
public class BSOD extends Abismo {

    public BSOD(int posicao) {
        super(7, posicao);
    }

    @Override
    public String getNome() {
        return "BSOD";
    }

    @Override
    public String aplicarEfeito(Player player, List<Player> allPlayers, int boardSize) {
        player.setEstado(PlayerState.ELIMINADO);
        return player.getNome() + " caiu em BSOD e foi ELIMINADO do jogo!";
    }

    @Override
    public boolean aceitaNeutralizacaoDe(Ferramenta ferramenta) {
        return ferramenta.podeNeutralizar(this);
    }

    @Override
    public String getImagemPng() {
        return "bsod.png";
    }
}
