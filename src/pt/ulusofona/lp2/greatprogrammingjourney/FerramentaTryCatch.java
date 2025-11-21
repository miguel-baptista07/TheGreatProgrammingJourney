package pt.ulusofona.lp2.greatprogrammingjourney;

/**
 * Ferramenta Try-Catch (id 1)
 * Neutraliza: Exception e File Not Found
 */
public class FerramentaTryCatch extends Ferramenta {

    public FerramentaTryCatch(int posicao) {
        super(1, posicao);
    }

    @Override
    public String getNome() {
        return "Try-Catch";
    }

    @Override
    public boolean podeNeutralizar(Abismo abismo) {
        // Neutraliza Exception (id 2) e File Not Found (id 3)
        return abismo.getId() == 2 || abismo.getId() == 3;
    }

    @Override
    public String getMensagemNeutralizacao(Abismo abismo) {
        return "Try-Catch neutralizou " + abismo.getNome() + "!";
    }

    @Override
    public String getImagemPng() {
        return "catch.png";
    }
}
