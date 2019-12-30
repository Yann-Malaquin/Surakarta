package fr.surakarta.player;

import fr.surakarta.piece.Piece;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String pseudo;
    private int score;
    private List<Piece> lPiece;

    public Player(String _pseudo) {
        this.pseudo = _pseudo;
        this.score = 0;
        lPiece = new ArrayList<Piece>();
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Piece> getlPiece() {
        return lPiece;
    }

    public void setlPiece(Piece p) {
        this.lPiece.add(p);
    }

    @Override
    public String toString() {
        return "Player{" +
                "pseudo='" + pseudo + '\'' +
                ", score=" + score +
                ", lPiece=" + lPiece +
                '}';
    }
}
