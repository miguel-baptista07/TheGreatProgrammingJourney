package pt.ulusofona.lp2.greatprogrammingjourney;

public class ElementsFactory {
    public static BoardElement createAbyss(int id, int position) {
        switch (id) {
            case 0: return new AbyssSyntaxError(position);
            case 1: return new AbyssLogicError(position);
            case 2: return new AbyssException(position);
            case 3: return new AbyssFileNotFound(position);
            case 4: return new AbyssCrash(position);
            case 5: return new AbyssDuplicatedCode(position);
            case 6: return new AbyssSideEffects(position);
            case 7: return new AbyssBSOD(position);
            case 8: return new AbyssInfiniteLoop(position);
            case 9: return new AbyssSegmentationFault(position);
            default: return new AbyssException(position);
        }
    }

    public static BoardElement createTool(int id, int position) {
        switch (id) {
            case 0: return new ToolInheritance(position);
            case 1: return new ToolFunctional(position);
            case 2: return new ToolUnitTests(position);
            case 3: return new ToolExceptionHandling(position);
            case 4: return new ToolIDE(position);
            case 5: return new ToolTutorHelp(position);
            default: return new ToolInheritance(position);
        }
    }
}
