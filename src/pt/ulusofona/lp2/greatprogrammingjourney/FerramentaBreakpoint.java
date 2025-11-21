package pt.ulusofona.lp2.greatprogrammingjourney;

/**
 * Ferramenta Breakpoint (id 5)
 * Neutraliza: Infinite Loop
 * Liberta o jogador do estado PRESO!
 */
public class FerramentaBreakpoint extends Ferramenta {

    public FerramentaBreakpoint(int posicao) {
        super(5, posicao);
    }

    @Override
    public String getNome() {
        return "Breakpoint";
    }

    @Override
    public boolean podeNeutralizar(Abismo abismo) {
        // Neutraliza Infinite Loop (id 8) - liberta jogador preso!
        return abismo.getId() == 8;
    }

    @Override
    public String getMensagemNeutralizacao(Abismo abismo) {
        return "Breakpoint neutralizou " + abismo.getNome() + " e libertou o jogador!";
    }
}
