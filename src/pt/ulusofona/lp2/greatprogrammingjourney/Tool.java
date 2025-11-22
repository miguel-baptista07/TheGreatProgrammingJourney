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
    public String applyEffect(Player player, GameManager manager) {
        if (!player.hasTool(id)) {
            player.addTool(id);
        }
        player.setFerramentaAtiva(id);
        return "tool";
    }
}