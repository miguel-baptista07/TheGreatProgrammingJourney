package pt.ulusofona.lp2.greatprogrammingjourney;

public class Tool5_TutorHelp extends ToolBase {
    public Tool5_TutorHelp(int position) { super(5, position); }

    @Override
    public String getName() { return "Ajuda do Professor"; }

    @Override
    public String applyEffect(Player p, GameManager gm) {
        p.addTool(5);
        return "Ajuda do Professor obtida";
    }
}
