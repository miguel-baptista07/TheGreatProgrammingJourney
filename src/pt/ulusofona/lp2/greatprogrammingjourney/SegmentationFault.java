package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.List;

/**
 * Abismo Segmentation Fault (id 9)
 * Efeito: TODOS os jogadores nessa casa recuam 3 casas
 */
public class SegmentationFault extends Abismo {

    public SegmentationFault(int posicao) {
        super(9, posicao);
    }

    @Override
    public String getNome() {
        return "Segmentation Fault";
    }

    @Override
    public String aplicarEfeito(Player player, List<Player> allPlayers, int boardSize) {
        int posicaoAbismo = player.getPosicao();
        StringBuilder mensagem = new StringBuilder();
        mensagem.append(player.getNome()).append(" caiu em Segmentation Fault! ");
        
        // Afeta TODOS os jogadores nesta posição
        int jogadoresAfetados = 0;
        for (Player p : allPlayers) {
            if (p.getPosicao() == posicaoAbismo && p.getEstado() == PlayerState.ATIVO) {
                int novaPosicao = validarPosicao(p.getPosicao() - 3, boardSize);
                p.setPosicao(novaPosicao);
                jogadoresAfetados++;
            }
        }
        
        mensagem.append("Todos os jogadores nessa casa recuaram 3 casas!");
        return mensagem.toString();
    }

    @Override
    public boolean aceitaNeutralizacaoDe(Ferramenta ferramenta) {
        return ferramenta.podeNeutralizar(this);
    }

    @Override
    public String getImagemPng() {
        return "core-dumped.png";
    }
}
