package pt.ulusofona.lp2.greatprogrammingjourney;

public class ElementsFactory {
    public static BoardElement createAbyss(int id, int position) {
        switch (id) {
            case 0: return new Abyss0_SyntaxError(position);
            case 1: return new Abyss1_LogicError(position);
            case 2: return new Abyss2_Exception(position);
            case 3: return new Abyss3_FileNotFound(position);
            case 4: return new Abyss4_Crash(position);
            case 5: return new Abyss5_DuplicatedCode(position);
            case 6: return new Abyss6_SideEffects(position);
            case 7: return new Abyss7_BSOD(position);
            case 8: return new Abyss8_InfiniteLoop(position);
            case 9: return new Abyss9_SegmentationFault(position);
            default: return new Abyss2_Exception(position);
        }
    }

    public static BoardElement createTool(int id, int position) {
        switch (id) {
            case 0: return new Tool0_Inheritance(position);
            case 1: return new Tool1_Functional(position);
            case 2: return new Tool2_UnitTests(position);
            case 3: return new Tool3_ExceptionHandling(position);
            case 4: return new Tool4_IDE(position);
            case 5: return new Tool5_TutorHelp(position);
            default: return new Tool0_Inheritance(position);
        }
    }
}
