package pt.ulusofona.lp2.greatprogrammingjourney;

public abstract class Tool extends Cell {

    protected final int toolId;

    public Tool(int toolId, int posicao) {
        super(toolId, posicao);
        this.toolId = toolId;
    }

    @Override
    public String getImagePng() {
        return "tool_" + toolId + ".png";
    }

    @Override
    public String react(Player p, GameManager gm) {

        if (p.hasTool(toolId)) {
            return "O jogador já possui a ferramenta necessária";
        }

        p.addTool(toolId);
        return "O jogador recolheu a ferramenta";
    }
}
