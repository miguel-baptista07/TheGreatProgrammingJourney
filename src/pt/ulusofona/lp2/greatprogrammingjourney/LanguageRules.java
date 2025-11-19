package pt.ulusofona.lp2.greatprogrammingjourney;

public class LanguageRules {

    public static boolean isMoveAllowed(Player p, int spaces) {
        String first = p.getFirstLanguage();

        if (first.equalsIgnoreCase("Assembly")) {
            return spaces == 1 || spaces == 2;
        }

        if (first.equalsIgnoreCase("Brainfuck")) {
            return spaces == 1 || spaces == 2 || spaces == 3;
        }

        return spaces >= 1 && spaces <= 6; // normal rule
    }
}
