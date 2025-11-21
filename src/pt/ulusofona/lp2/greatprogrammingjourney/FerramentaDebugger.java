package pt.ulusofona.lp2.greatprogrammingjourney;

/**
 * Ferramenta Debugger (id 0)
 * Neutraliza: Syntax Error e Logical Error
 */
public class FerramentaDebugger extends Ferramenta {

    public FerramentaDebugger(int posicao) {
        super(0, posicao);
    }

    @Override
    public String getNome() {
        return "Debugger";
    }

    @Override
    public boolean podeNeutralizar(Abismo abismo) {
        // Neutraliza Syntax Error (id 0) e Logical Error (id 1)
        return abismo.getId() == 0 || abismo.getId() == 1;
    }

    @Override
    public String getMensagemNeutralizacao(Abismo abismo) {
        return "Debugger neutralizou " + abismo.getNome() + "!";
    }

    @Override
    public String getImagemPng() {
        return "IDE.png";
    }
}
