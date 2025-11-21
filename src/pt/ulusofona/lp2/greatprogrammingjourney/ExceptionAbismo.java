package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.List;

/**
 * Abismo Exception (id 2)
 * Efeito: Jogador recua 2 casas
 * Nota: Nome da classe é ExceptionAbismo para evitar conflito com java.lang.Exception
 */
public class ExceptionAbismo extends Abismo {

    public ExceptionAbismo(int posicao) {
        super(2, posicao);
    }

    @Override
    public String getNome() {
        return "Exception";
    }

    @Override
    public String aplicarEfeito(Player player, List<Player> allPlayers, int boardSize) {
        int posicaoAtual = player.getPosicao();
        int novaPosicao = validarPosicao(posicaoAtual - 2, boardSize);
        player.setPosicao(novaPosicao);
        return player.getNome() + " caiu em Exception e recuou 2 casas (posição " + novaPosicao + ")";
    }

    @Override
    public boolean aceitaNeutralizacaoDe(Ferramenta ferramenta) {
        return ferramenta.podeNeutralizar(this);
    }

    @Override
    public String getImagemPng() {
        return "exception.png";
    }
}
