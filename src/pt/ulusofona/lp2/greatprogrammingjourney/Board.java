package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe que representa o tabuleiro do jogo.
 * Expandida da parte 1 para incluir abismos e ferramentas.
 */
public class Board {
    private int tamanhoTabuleiro;
    private Map<Integer, Abismo> abismos;
    private Map<Integer, Ferramenta> ferramentas;

    public Board() {
        this.tamanhoTabuleiro = 0;
        this.abismos = new HashMap<>();
        this.ferramentas = new HashMap<>();
    }

    public int getTamanhoTabuleiro() {
        return tamanhoTabuleiro;
    }

    public void setTamanhoTabuleiro(int tamanhoTabuleiro) {
        this.tamanhoTabuleiro = tamanhoTabuleiro;
    }

    /**
     * Adiciona um abismo numa posição específica.
     * 
     * @param posicao Posição no tabuleiro
     * @param abismo O abismo a adicionar
     */
    public void adicionarAbismo(int posicao, Abismo abismo) {
        abismos.put(posicao, abismo);
    }

    /**
     * Adiciona uma ferramenta numa posição específica.
     * 
     * @param posicao Posição no tabuleiro
     * @param ferramenta A ferramenta a adicionar
     */
    public void adicionarFerramenta(int posicao, Ferramenta ferramenta) {
        ferramentas.put(posicao, ferramenta);
    }

    /**
     * Obtém o abismo numa posição específica.
     * 
     * @param posicao Posição no tabuleiro
     * @return O abismo nessa posição, ou null se não houver
     */
    public Abismo getAbismo(int posicao) {
        return abismos.get(posicao);
    }

    /**
     * Obtém a ferramenta numa posição específica.
     * 
     * @param posicao Posição no tabuleiro
     * @return A ferramenta nessa posição, ou null se não houver
     */
    public Ferramenta getFerramenta(int posicao) {
        return ferramentas.get(posicao);
    }

    /**
     * Remove a ferramenta de uma posição (quando é apanhada).
     * 
     * @param posicao Posição no tabuleiro
     */
    public void removerFerramenta(int posicao) {
        ferramentas.remove(posicao);
    }

    /**
     * Verifica se há um abismo numa posição.
     * 
     * @param posicao Posição no tabuleiro
     * @return true se há abismo
     */
    public boolean temAbismo(int posicao) {
        return abismos.containsKey(posicao);
    }

    /**
     * Verifica se há uma ferramenta numa posição.
     * 
     * @param posicao Posição no tabuleiro
     * @return true se há ferramenta
     */
    public boolean temFerramenta(int posicao) {
        return ferramentas.containsKey(posicao);
    }

    /**
     * Limpa todos os abismos e ferramentas do tabuleiro.
     */
    public void limpar() {
        abismos.clear();
        ferramentas.clear();
    }

    /**
     * Obtém todos os abismos do tabuleiro.
     * 
     * @return Mapa de abismos por posição
     */
    public Map<Integer, Abismo> getAbismos() {
        return new HashMap<>(abismos);
    }

    /**
     * Obtém todas as ferramentas do tabuleiro.
     * 
     * @return Mapa de ferramentas por posição
     */
    public Map<Integer, Ferramenta> getFerramentas() {
        return new HashMap<>(ferramentas);
    }
}
