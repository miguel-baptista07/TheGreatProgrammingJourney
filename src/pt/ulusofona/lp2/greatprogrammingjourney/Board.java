package pt.ulusofona.lp2.greatprogrammingjourney;

public class Board {

    private final int tamanho;
    private final Cell[] cells;

    public Board(int tamanho) {
        this.tamanho = tamanho;
        this.cells = new Cell[tamanho + 1];
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setCell(int pos, Cell c) {
        cells[pos] = c;
    }

    public Cell getCell(int pos) {
        return cells[pos];
    }
}
