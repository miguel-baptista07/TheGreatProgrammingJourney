package pt.ulusofona.lp2.greatprogrammingjourney;

public class AbyssFactory {

    public static Cell create(int id, int pos) {
        return switch (id) {
            case 0 -> new AbyssSyntaxError(pos);
            case 1 -> new AbyssLogicError(pos);
            case 2 -> new AbyssException(pos);
            case 3 -> new AbyssFileNotFound(pos);
            case 4 -> new AbyssCrash(pos);
            case 5 -> new AbyssDuplicatedCode(pos);
            case 6 -> new AbyssSideEffects(pos);
            case 7 -> new AbyssBSOD(pos);
            case 8 -> new AbyssInfiniteLoop(pos);
            case 9 -> new AbyssSegmentationFault(pos);
            default -> null;
        };
    }
}
