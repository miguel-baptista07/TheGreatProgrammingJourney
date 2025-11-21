package pt.ulusofona.lp2.greatprogrammingjourney;

/**
 * Classe abstrata que representa uma ferramenta (power-up).
 * Ferramentas podem neutralizar abismos específicos.
 */
public abstract class Ferramenta extends ElementoTabuleiro {

    public Ferramenta(int id, int posicao) {
        super(id, posicao);
    }

    @Override
    public String getTipo() {
        return "T";
    }

    /**
     * Verifica se esta ferramenta pode neutralizar o abismo dado.
     * Usa polimorfismo para evitar instanceof.
     * 
     * @param abismo O abismo a verificar
     * @return true se pode neutralizar, false caso contrário
     */
    public abstract boolean podeNeutralizar(Abismo abismo);

    /**
     * Retorna a mensagem apropriada quando a ferramenta neutraliza um abismo.
     * 
     * @param abismo O abismo neutralizado
     * @return Mensagem de neutralização
     */
    public abstract String getMensagemNeutralizacao(Abismo abismo);

    /**
     * Retorna o nome da ferramenta para exibição.
     * 
     * @return Nome da ferramenta
     */
    public abstract String getNome();
}
