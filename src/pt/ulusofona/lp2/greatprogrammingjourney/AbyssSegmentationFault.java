package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.List;

public class AbyssSegmentationFault extends Cell {

    public AbyssSegmentationFault(int pos) {
        super(9, pos);
    }

    @Override
    public String getImagePng() {
        return "seg_fault.png";
    }

    @Override
    public String react(Player p, GameManager gm) {
        List<Player> same = gm.getAlivePlayersAt(getPosicao());

        for (Player x : same) {
            x.setPosicao(x.posDuasAntes());  // recuar 2
            // OU: x.setPosicao(x.getPosicao() - 3); se o enunciado pedir -3
        }

        return "Falha de segmentação";
    }
}
