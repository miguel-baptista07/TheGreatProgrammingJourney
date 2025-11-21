package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.List;

/**
 * Classe abstrata que representa um abismo (obstáculo).
 * Cada tipo de abismo tem um efeito específico sobre os jogadores.
 */
public abstract class Abismo extends ElementoTabuleiro {

    public Abismo(int id, int posicao) {
        super(id, posicao);
    }

    @Override
    public String getTipo() {
        return "A";
    }

    /**
     * Aplica o efeito do abismo ao jogador.
     * Este método implementa a lógica específica de cada tipo de abismo.
     * 
     * @param player O jogador afetado
     * @param allPlayers Lista de todos os jogadores (necessário para Segmentation Fault)
     * @param boardSize Tamanho do tabuleiro
     * @return Mensagem descrevendo o efeito
     */
    public abstract String aplicarEfeito(Player player, List<Player> allPlayers, int boardSize);

    /**
     * Retorna o nome do abismo para exibição.
     * 
     * @return Nome do abismo
     */
    public abstract String getNome();

    /**
     * Método auxiliar para validar posição dentro dos limites do tabuleiro.
     * 
     * @param posicao Posição a validar
     * @param boardSize Tamanho do tabuleiro
     * @return Posição válida (entre 1 e boardSize)
     */
    protected int validarPosicao(int posicao, int boardSize) {
        if (posicao < 1) {
            return 1;
        }
        if (posicao > boardSize) {
            return boardSize;
        }
        return posicao;
    }

    /**
     * Permite que a ferramenta verifique se pode neutralizar este abismo.
     * Usa double dispatch para evitar instanceof.
     * 
     * @param ferramenta A ferramenta que tenta neutralizar
     * @return true se pode ser neutralizado por esta ferramenta
     */
    public abstract boolean aceitaNeutralizacaoDe(Ferramenta ferramenta);
}
