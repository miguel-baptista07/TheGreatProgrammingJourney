package pt.ulusofona.lp2.greatprogrammingjourney;

public class LanguageRules {

    public static boolean isMoveAllowed(Player p, int spaces) {
        String first = p.getFirstLanguage();

        // Assembly: Pode mover 1, 2, 3, 4 (NÃO pode mover 5 ou 6)
        if (first.equalsIgnoreCase("Assembly")) {
            return spaces >= 1 && spaces <= 4;
        }

        // Brainfuck: Pode mover apenas 1, 2, 3
        if (first.equalsIgnoreCase("Brainfuck")) {
            return spaces >= 1 && spaces <= 3;
        }

        // Qualquer outra linguagem: movimento normal (1-6)
        return spaces >= 1 && spaces <= 6;
    }
}