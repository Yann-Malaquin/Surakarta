package fr.surakarta.player;

import fr.surakarta.piece.Piece;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Player est la class représentant un joueur</b><br>
 * Un joueur à :<br>
 * <p>
 * <ul>
 *     <li>Un pseudo
 *     <li>Un score attribué à chaque fin de partie gagné
 *     <li>Une liste de pion
 * </ul>
 *
 * @author Billy MORTREUX, Yann MALAQUIN
 */

public class Player {

    /**
     * Le pseudo du joueur
     *
     * @see Player#getPseudo()
     * @see Player#setPseudo(String)
     */
    private String pseudo;

    /**
     * Le score du joueur
     *
     * @see Player#getScore()
     * @see Player#setScore(int)
     */
    private int score;

    /**
     * la liste des pions du joueur
     *
     * @see Piece
     * @see Player#getlPiece()
     * @see Player#setlPiece(Piece)
     */
    private List<Piece> lPiece;


    /**
     * Constructeur Player.
     * <p>
     * A la construction d'un objet Player, la liste de pions est créee vide
     *
     * @param _pseudo Le pseudo du joueur
     * @see Player#pseudo
     * @see Player#score
     * @see Player#lPiece
     */
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

    /**
     * Retourne toutes les données d'un Player
     *
     * @return un Player sous forme d'une chaîne de caractères.
     */
    @Override
    public String toString() {
        return "Player{" +
                "pseudo='" + pseudo + '\'' +
                ", score=" + score +
                ", lPiece=" + lPiece +
                '}';
    }
}
