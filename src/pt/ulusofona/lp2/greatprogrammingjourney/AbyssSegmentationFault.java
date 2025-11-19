package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.List;

public class AbyssSegmentationFault extends Cell {

    public AbyssSegmentationFault(int pos) {
        super(9, pos);
    }

    @Override
    public String getImagePng() {
        return "segfault.png";
    }

    @Override
    public String react(Player p, GameManager gm) {

        List<Player> todos = gm.getAlivePlayersAt(posicao);

        if (todos.size() >= 2) {
            for (Player x : todos) {
                x.setPosicao(x.getPosicao() - 3);
            }
        }

        return "O jogador caiu num abismo";
    }
}
