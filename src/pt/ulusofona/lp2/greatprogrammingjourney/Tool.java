package pt.ulusofona.lp2.greatprogrammingjourney;

public class Tool extends BoardElement {

    public Tool(int id, int position) {
        super(id, position);
    }

    @Override
    public boolean isAbyss() {
        return false;
    }

    @Override
    public String getName() {
        return "Tool-" + id;
    }

    @Override
    public String applyEffect(Player p, GameManager gm) {
        switch (id) {
            case 0:
                p.setFerramentaAtiva(0);
                return "Jogador agarrou Herança";

            case 1:
                p.setFerramentaAtiva(1);
                return "Jogador agarrou Programação Funcional";

            case 2:
                p.setFerramentaAtiva(2);
                return "Jogador agarrou Testes Unitários";

            case 3:
                p.setFerramentaAtiva(3);
                return "Jogador agarrou Tratamento de Exceções";

            case 4:
                p.setFerramentaAtiva(4);
                return "Jogador agarrou IDE";

            case 5:
                p.setFerramentaAtiva(5);
                return "Ajuda do Professor obtida";

            default:
                return "Ferramenta desconhecida";
        }
    }

}