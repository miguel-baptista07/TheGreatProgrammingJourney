package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.ArrayList;

public class Board {
    private int tamanhoTabuleiro;

    private ArrayList<Integer> posicoesJogadores;

    public Board() {
        this.tamanhoTabuleiro = 0;
        this.posicoesJogadores = new ArrayList<>();
    }

    public int getTamanhoTabuleiro() {
        return tamanhoTabuleiro;
    }

    public void setTamanhoTabuleiro(int tamanhoTabuleiro) {
        this.tamanhoTabuleiro = tamanhoTabuleiro;
    }

    public ArrayList<Integer> getPosicoesJogadores() {
        return posicoesJogadores;
    }

    public void setPosicoesJogadores(ArrayList<Integer> posicoesJogadores) {
        this.posicoesJogadores = posicoesJogadores;
    }

    public void adicionarPosicaoJogador(int posicao) {
        this.posicoesJogadores.add(posicao);
    }

    public void limparPosicoesJogadores() {
        this.posicoesJogadores.clear();
    }
}