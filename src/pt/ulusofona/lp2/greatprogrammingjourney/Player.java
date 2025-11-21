package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe que representa um jogador/programador.
 * Expandida da parte 1 para incluir ferramentas e estados.
 */
public class Player {
    private String id;
    private String nome;
    private String linguagens;
    private String cor;
    private int posicao;
    private int posicaoAnterior;
    private PlayerState estado;
    private List<Ferramenta> ferramentas;

    public Player(String id, String nome, String linguagens, String cor) {
        this.id = id;
        this.nome = nome;
        this.linguagens = formatarLinguagens(linguagens);
        this.cor = cor;
        this.posicao = 1;
        this.posicaoAnterior = 1;
        this.estado = PlayerState.ATIVO;
        this.ferramentas = new ArrayList<>();
    }

    private String formatarLinguagens(String linguagens) {
        if (linguagens == null || linguagens.trim().isEmpty()) {
            return "";
        }

        String[] langs = linguagens.split(";");
        for (int i = 0; i < langs.length; i++) {
            langs[i] = langs[i].trim();
        }
        Arrays.sort(langs);
        return String.join("; ", langs);
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getLinguagens() {
        return linguagens;
    }

    public String getCor() {
        return cor;
    }

    public int getPosicao() {
        return posicao;
    }

    public int getPosicaoAnterior() {
        return posicaoAnterior;
    }

    public PlayerState getEstado() {
        return estado;
    }

    public List<Ferramenta> getFerramentas() {
        return new ArrayList<>(ferramentas);
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLinguagens(String linguagens) {
        this.linguagens = formatarLinguagens(linguagens);
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public void setPosicao(int posicao) {
        this.posicaoAnterior = this.posicao;
        this.posicao = posicao;
    }

    public void setEstado(PlayerState estado) {
        this.estado = estado;
    }



    // Métodos relacionados com ferramentas
    
    /**
     * Adiciona uma ferramenta ao inventário do jogador.
     * 
     * @param ferramenta A ferramenta a adicionar
     */
    public void adicionarFerramenta(Ferramenta ferramenta) {
        ferramentas.add(ferramenta);
    }

    /**
     * Remove uma ferramenta do inventário do jogador.
     * 
     * @param ferramenta A ferramenta a remover
     */
    public void removerFerramenta(Ferramenta ferramenta) {
        ferramentas.remove(ferramenta);
    }

    /**
     * Verifica se o jogador tem uma ferramenta de um tipo específico (por id).
     * 
     * @param ferramentaId O id da ferramenta
     * @return true se o jogador tem essa ferramenta
     */
    public boolean temFerramenta(int ferramentaId) {
        for (Ferramenta f : ferramentas) {
            if (f.getId() == ferramentaId) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtém uma ferramenta que pode neutralizar o abismo dado.
     * 
     * @param abismo O abismo a neutralizar
     * @return A ferramenta que neutraliza, ou null se não tiver
     */
    public Ferramenta getFerramentaParaNeutralizar(Abismo abismo) {
        for (Ferramenta f : ferramentas) {
            if (f.podeNeutralizar(abismo)) {
                return f;
            }
        }
        return null;
    }

    /**
     * Verifica se o jogador pode mover (não está eliminado nem preso).
     * 
     * @return true se pode mover
     */
    public boolean podeMover() {
        return estado == PlayerState.ATIVO || estado == PlayerState.VENCEDOR;
    }

    /**
     * Verifica se o jogador está ativo no jogo.
     * 
     * @return true se está ativo
     */
    public boolean estaAtivo() {
        return estado == PlayerState.ATIVO;
    }

    /**
     * Verifica se o jogador foi eliminado.
     * 
     * @return true se foi eliminado
     */
    public boolean foiEliminado() {
        return estado == PlayerState.ELIMINADO;
    }

    /**
     * Verifica se o jogador está preso.
     * 
     * @return true se está preso
     */
    public boolean estaPreso() {
        return estado == PlayerState.PRESO;
    }

    /**
     * Liberta o jogador do estado PRESO.
     */
    public void libertar() {
        if (estado == PlayerState.PRESO) {
            estado = PlayerState.ATIVO;
        }
    }

    /**
     * Verifica se o jogador usa Assembly (tem restrição de movimento).
     * 
     * @return true se usa Assembly
     */
    public boolean usaAssembly() {
        return linguagens != null && linguagens.toLowerCase().contains("assembly");
    }

    public boolean usaC() {
        return linguagens != null && linguagens.toLowerCase().contains("c");
    }

    /**
     * Verifica se o jogador pode fazer um movimento específico.
     * Assembly só pode mover 1 ou 2 casas.
     * 
     * @param nrSpaces Número de casas a mover
     * @return true se pode fazer o movimento
     */
    public boolean podeMovimentar(int nrSpaces) {
        if (usaAssembly() && nrSpaces > 2) {
            return false;
        }
        if (usaC() && nrSpaces > 3) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", linguagens='" + linguagens + '\'' +
                ", cor='" + cor + '\'' +
                ", posicao=" + posicao +
                ", estado=" + estado +
                ", ferramentas=" + ferramentas.size() +
                '}';
    }
}
