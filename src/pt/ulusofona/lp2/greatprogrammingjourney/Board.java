package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.*;

public class Board {
    private int tamanhoTabuleiro;
    private final Map<Integer, List<BoardElement>> elementos;

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
        Map<Integer, BoardElement> flat = new HashMap<>();
        for (Map.Entry<Integer, List<BoardElement>> entry : elementos.entrySet()) {
            if (!entry.getValue().isEmpty()) {

                flat.put(entry.getKey(), entry.getValue().get(0));
            }
        }
        return flat;
    }

    public void addElement(BoardElement e) {
        elementos.computeIfAbsent(e.getPosition(), k -> new ArrayList<>()).add(e);
    }


    public BoardElement getElementAt(int pos) {
        List<BoardElement> elems = elementos.get(pos);
        return (elems == null || elems.isEmpty()) ? null : elems.get(0);
    }

    public List<BoardElement> getAllElementsAt(int pos) {
        return elementos.getOrDefault(pos, new ArrayList<>());
    }

    public void clearElements() {
        elementos.clear();
    }
}