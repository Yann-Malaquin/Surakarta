package fr.surakarta.grid;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Node est la class représentant un noeud sur le jeu
 *
 * @author Billy MORTREUX, Yann MALAQUIN
 */

public class Node extends Circle {

    /**
     * Constructeur Node.
     *
     * Lors de la construction d'un objet Node, le radius est fixé à 15 et la couleur est fixée à Noire
     * @param x
     *         L'abscisse du noeud
     * @param y
     *          L'ordonnée du noeud
     */
    public Node(double x, double y){
        super(x,y,15, Color.BLACK);
    }


    /**
     * Retourne les coordonées d'un noeud
     * @return les coordonnées d'un noeud sous la forme d'une chaîne de caractères.
     */

    public String toString(){
        return getCenterX() +";"+ getCenterY();
    }
}
