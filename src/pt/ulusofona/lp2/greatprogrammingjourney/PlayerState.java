package pt.ulusofona.lp2.greatprogrammingjourney;

/**
 * Enum que representa os possíveis estados de um jogador.
 */
public enum PlayerState {
    ATIVO,      // Jogador está ativo e pode jogar normalmente
    ELIMINADO,  // Jogador foi eliminado (BSOD)
    PRESO,      // Jogador está preso (Infinite Loop)
    VENCEDOR    // Jogador chegou ao fim
}
