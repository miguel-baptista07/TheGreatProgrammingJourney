package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.List;

public class Abyss extends BoardElement {

    public Abyss(int id, int position) {
        super(id, position);
    }

    @Override
    public boolean isAbyss() {
        return true;
    }

    @Override
    public String getName() {
        return "Abyss-" + id;
    }

    @Override
    public String applyEffect(Player player, GameManager manager) {
        Integer active = player.getFerramentaAtiva();

        if (active != null && toolNeutralizes(active, id)) {
            return "neutralizado";
        }

        switch (id) {
            case 0:
                player.setPosicao(player.getPosicaoAnteriorMovimento());
                return "Erro de sintaxe";
            case 1:
                int move = player.getLastMoveSpaces() / 2;
                player.setPosicao(player.getPosicao() - move);
                return "erro logico";
            case 2:
                player.setPosicao(player.getPosicao() - 2);
                return "excecao";
            case 3:
                player.setPosicao(player.getPosicao() - 3);
                return "file not found";
            case 4:
                player.setPosicao(1);
                return "crash";
            case 5:
                player.setPosicao(player.getPosicaoAnteriorMovimento());
                return "duplicated code";
            case 6:
                int recuo = Math.min(3, player.getLastMoveSpaces());
                player.setPosicao(player.getPosicao() - recuo);
                return "side effects";
            case 7:
                manager.eliminatePlayer(player);
                return "bsod";
            case 8:
                player.setPreso(true);
                return "loop";
            case 9:
                List<Player> same = manager.getPlayersAtPosition(position);
                for (Player p : same) {
                    if (!p.isEliminado()) {
                        p.setPosicao(p.getPosicao() - 3);
                    }
                }
                return "segfault";
            default:
                return "abyss";
        }
    }

    private boolean toolNeutralizes(int toolId, int abyssId) {
        switch (abyssId) {
            case 0:
                return toolId == 4;
            case 1:
                return toolId == 1;
            case 2:
                return toolId == 3;
            case 3:
                return toolId == 3;
            case 4:
                return toolId == 5;
            case 5:
                return toolId == 4;
            case 6:
                return toolId == 1;
            case 7:
                return toolId == 5;
            case 8:
                return toolId == 1;
            case 9:
            default:
                return false;
        }
    }
}