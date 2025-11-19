package pt.ulusofona.lp2.greatprogrammingjourney;

public class ToolFactory {

    public static Tool create(int id, int pos) {
        return switch (id) {
            case 0 -> new Tool0(pos);
            case 1 -> new Tool1(pos);
            case 2 -> new Tool2(pos);
            case 3 -> new Tool3(pos);
            case 4 -> new Tool4(pos);
            case 5 -> new Tool5(pos);
            default -> null;
        };
    }
}
