package pt.ulusofona.lp2.greatprogrammingjourney;

public class ElementsFactory {
    public static BoardElement createAbyss(int id, int position) {
        switch (id) {
            case 0: return new AbyssErroDeSintaxe(position);
            case 1: return new AbyssErroDeLógica(position);
            case 2: return new AbyssException(position);
            case 3: return new AbyssFileNotFoundException(position);
            case 4: return new AbyssCrash(position);
            case 5: return new AbyssCodigoDuplicado(position);
            case 6: return new AbyssEfeitosSecundários(position);
            case 7: return new AbyssBSOD(position);
            case 8: return new AbyssCicloInfinito(position);
            case 9: return new AbyssSegmentationFault(position);
            default: return new AbyssException(position);
        }
    }

    public static BoardElement createTool(int id, int position) {
        switch (id) {
            case 0: return new ToolInheritance(position);
            case 1: return new ToolProgramaçãoFuncional(position);
            case 2: return new ToolUnitTests(position);
            case 3: return new ToolTratamentoDeExcepções(position);
            case 4: return new ToolIDE(position);
            case 5: return new ToolAjudaDoProfessor(position);
            default: return new ToolInheritance(position);
        }
    }
}
