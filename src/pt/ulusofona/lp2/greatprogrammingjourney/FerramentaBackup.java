package pt.ulusofona.lp2.greatprogrammingjourney;

/**
 * Ferramenta Backup (id 2)
 * Neutraliza: Crash e Duplicated Code
 */
public class FerramentaBackup extends Ferramenta {

    public FerramentaBackup(int posicao) {
        super(2, posicao);
    }

    @Override
    public String getNome() {
        return "Backup";
    }

    @Override
    public boolean podeNeutralizar(Abismo abismo) {
        // Neutraliza Crash (id 4) e Duplicated Code (id 5)
        return abismo.getId() == 4 || abismo.getId() == 5;
    }

    @Override
    public String getMensagemNeutralizacao(Abismo abismo) {
        return "Backup neutralizou " + abismo.getNome() + "!";
    }

    @Override
    public String getImagemPng() {
        return "inheritance.png";
    }
}
