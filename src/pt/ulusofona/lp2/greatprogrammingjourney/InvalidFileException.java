package pt.ulusofona.lp2.greatprogrammingjourney;

/**
 * Exceção lançada quando um ficheiro de save é inválido.
 */
public class InvalidFileException extends Exception {
    
    public InvalidFileException(String mensagem) {
        super(mensagem);
    }
    
    public InvalidFileException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
