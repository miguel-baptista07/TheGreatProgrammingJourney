package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.Map;

public class ElementsIOAdapter {
    public static BoardElement toElement(int type, int subtype, int pos) {
        if (type == 0) {
            return ElementsFactory.createAbyss(subtype, pos);
        }
        return ElementsFactory.createTool(subtype, pos);
    }

    public static int typeOf(BoardElement be) {
        return be.isAbyss() ? 0 : 1;
    }


}
