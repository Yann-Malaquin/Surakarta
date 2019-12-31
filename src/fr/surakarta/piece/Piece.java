package fr.surakarta.piece;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * <b>Piece est la class représentant un pion</b>
 *
 * @author Billy MORTREUX, Yann MALAQUIN
 */

public class Piece extends Circle {

    /**
     * Savoir si le pion est selectionné ou non
     *
     * @see Piece#isSelected()
     */
    private boolean selected;

    /**
     * Permet de préciser à qui est destiné le pion
     *
     * @see PieceType
     */
    private PieceType type;

    /**
     * Couleur du pion
     */
    private Color color;

    /**
     * Constructeur Piece.
     *
     * A la construction d'un objet Piece, on lui attribut ses coordonnées, son radius sa couleur.
     * la couleur de la bordure est fixée à blanc et son épaisseur à 3.
     *
     * @param _type
     *          Le joueur à qui appartient le pion
     * @param x
     *         L'abscisse du pion
     * @param y
     *          L'ordonnée du pion
     * @param _color
     *      La couleur du pion
     *
     * @see Piece#type
     * @see Piece#color
     */


    public Piece(PieceType _type, double x, double y, Color _color) {
        super(x, y, 10, _color);
        this.color = _color;
        this.type = _type;
        setStroke(Color.WHITE);
        setStrokeWidth(3);
    }

    public PieceType getType() {
        return type;
    }

    /**
     * Méthode permettant le changement de couleur de la bordure lors de la sélection ou la déselection d'un pion.
     * La bordure est blanche lorsque le pion n'est pas sélectionné, jaune lorsque celui-ci est sélectionné
     *
     * @see Piece#selected
     */

    public void select() {
        selected = !selected;

        if (selected) {
            setStroke(Color.YELLOW);
        } else {
            this.setStroke(Color.WHITE);
        }
    }

    public boolean isSelected() {
        return selected;
    }

    /**
     * Retourne les coordonnées d'un pion
     *
     * @return les coordonnées d'un pion sous la forme d'une chaîne de caractères
     */
    public String toString() {
        return getCenterX() + ";" + getCenterY();
    }
}
