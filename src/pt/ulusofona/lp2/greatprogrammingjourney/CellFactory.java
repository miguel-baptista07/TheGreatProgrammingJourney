package pt.ulusofona.lp2.greatprogrammingjourney;

public class CellFactory {

    public static Cell normal(int pos) {
        return new NormalCell(pos);
    }

    public static Cell glory(int pos) {
        return new GloryCell(pos);
    }
}
