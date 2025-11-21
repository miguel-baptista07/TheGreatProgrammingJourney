package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbyssDuplicatedCode extends Cell {

    public AbyssDuplicatedCode(int pos) {
        super(5, pos);
    }

    @Override
    public String getImagePng() {
        return "duplicated_code.png";
    }

    @Override
    public String react(Player p, GameManager gm) {
        p.setPosicao(p.posAnterior());
        return "Código duplicado";
    }
}
