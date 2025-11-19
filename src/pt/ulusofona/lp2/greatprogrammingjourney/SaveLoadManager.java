package pt.ulusofona.lp2.greatprogrammingjourney;

import java.io.*;

public class SaveLoadManager {

    public static boolean save(File f, GameManager gm) {
        try (PrintWriter pw = new PrintWriter(f)) {
            pw.println("TGJ-V2");
            pw.println(gm.serializeState());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void load(File f, GameManager gm) throws InvalidFileException, FileNotFoundException {
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String header = br.readLine();
            if (header == null || !header.equals("TGJ-V2")) {
                throw new InvalidFileException("Header inválido");
            }
            gm.deserializeState(br);
        } catch (IOException e) {
            throw new InvalidFileException("Erro ao ler ficheiro");
        }
    }
}
