package pt.ulusofona.lp2.greatprogrammingjourney;

/**
 * Ferramenta Antivirus (id 4)
 * Neutraliza: BSOD (Blue Screen of Death)
 * Esta é a ferramenta mais importante pois previne eliminação!
 */
public class FerramentaAntivirus extends Ferramenta {

    public FerramentaAntivirus(int posicao) {
        super(4, posicao);
    }

    @Override
    public String getNome() {
        return "Antivirus";
    }

    @Override
    public boolean podeNeutralizar(Abismo abismo) {
        // Neutraliza BSOD (id 7) - previne eliminação!
        return abismo.getId() == 7;
    }

    @Override
    public String getMensagemNeutralizacao(Abismo abismo) {
        return "Antivirus neutralizou " + abismo.getNome() + " e salvou o jogador da eliminação!";
    }
}
