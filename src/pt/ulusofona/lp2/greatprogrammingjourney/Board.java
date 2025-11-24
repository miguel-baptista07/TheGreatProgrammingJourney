package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private int tamanhoTabuleiro;
    private final Map<Integer, BoardElement> elementos;

    public Board() {
        this.tamanhoTabuleiro = 0;
        this.elementos = new HashMap<>();
    }

    public int getTamanhoTabuleiro() {
        return tamanhoTabuleiro;
    }

    public void setTamanhoTabuleiro(int tamanhoTabuleiro) {
        this.tamanhoTabuleiro = tamanhoTabuleiro;
    }

    public Map<Integer, BoardElement> getElementos() {
        return elementos;
    }

    public void addElement(BoardElement e) {
        elementos.put(e.getPosition(), e);
    }

    public BoardElement getElementAt(int pos) {
        return elementos.get(pos);
    }

    public void clearElements() {
        elementos.clear();
    }
}