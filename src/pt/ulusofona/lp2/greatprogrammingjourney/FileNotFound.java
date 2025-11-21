package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.List;

/**
 * Abismo File Not Found (id 3)
 * Efeito: Jogador recua 3 casas
 */
public class FileNotFound extends Abismo {

    public FileNotFound(int posicao) {
        super(3, posicao);
    }

    @Override
    public String getNome() {
        return "File Not Found";
    }

    @Override
    public String aplicarEfeito(Player player, List<Player> allPlayers, int boardSize) {
        int posicaoAtual = player.getPosicao();
        int novaPosicao = validarPosicao(posicaoAtual - 3, boardSize);
        player.setPosicao(novaPosicao);
        return player.getNome() + " caiu em File Not Found e recuou 3 casas (posição " + novaPosicao + ")";
    }

    @Override
    public boolean aceitaNeutralizacaoDe(Ferramenta ferramenta) {
        return ferramenta.podeNeutralizar(this);
    }

    @Override
    public String getImagemPng() {
        return "file-not-found-exception.png";
    }
}
