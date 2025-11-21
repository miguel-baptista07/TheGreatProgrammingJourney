package pt.ulusofona.lp2.greatprogrammingjourney;

/**
 * Ferramenta Refactoring (id 3)
 * Neutraliza: Side Effects e Segmentation Fault
 */
public class FerramentaRefactoring extends Ferramenta {

    public FerramentaRefactoring(int posicao) {
        super(3, posicao);
    }

    @Override
    public String getNome() {
        return "Refactoring";
    }

    @Override
    public boolean podeNeutralizar(Abismo abismo) {
        // Neutraliza Side Effects (id 6) e Segmentation Fault (id 9)
        return abismo.getId() == 6 || abismo.getId() == 9;
    }

    @Override
    public String getMensagemNeutralizacao(Abismo abismo) {
        return "Refactoring neutralizou " + abismo.getNome() + "!";
    }
}
