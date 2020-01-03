package fr.surakarta.game;

import fr.surakarta.grid.Node;
import fr.surakarta.piece.Piece;
import fr.surakarta.piece.PieceType;
import fr.surakarta.player.Player;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Surkarta est la class principale du programme. Elle permet le bon déroulé du jeu</b><br>
 * Surakarta à : <br>
 * <p>
 * <ul>
 *     <li>Une hauteur
 *     <li>Une largeur
 *     <li>Un décalage
 *     <li>Une largeur de case
 *     <li>Une hauteur de case
 *     <li>Une selection de pion
 *     <li>Une liste de joueur
 *     <li>Une sélection de pion
 * </ul>
 *
 * @author Billy MORTREUX, Yann MALAQUIN
 */

public class Surakarta extends Application implements EventHandler<MouseEvent> {

    private static int i = 0;

    /**
     * hauteur du panneau
     */
    private double height;
    /**
     * largeur du panneau
     */
    private double width;
    /**
     * decalage pour centrer le dessin
     */
    private int decalage = 3;

    /**
     * nb pixels utilise en largeur pour une "case de la grille"
     */
    private double widthStep;
    /**
     * nb pixels utilise en hauteur pour une "case de la grille"
     */
    private double heightStep;

    /**
     * Permet de savoir si un pion est sélectionné ou non
     */

    private Piece pionSelectionne;

    private Group root = new Group();

    /**
     * Liste regroupant les 2 joueurs de la partie
     *
     * @see Player
     */

    private List<Player> lPlayer = new ArrayList<Player>();

    private List<Node> lNode = new ArrayList<Node>();

    public List<Player> getlPlayer() {
        return lPlayer;
    }

    public void setlPlayer(Player p) {
        this.lPlayer.add(p);
    }

    public List<Node> getlNode() {
        return this.lNode;
    }

    /**
     * Permet le lancement de la fenêtre
     *
     * @param primaryStage Correspond à la fenêtre entière
     */
    @Override
    public void start(Stage primaryStage) {

        decalage = 3;
        width = 500;
        height = 500;
        heightStep = height / 9;
        widthStep = width / 9;
        construirePlateauJeu(primaryStage);
    }

    /**
     * Permet la constrution du plateau de jeu
     *
     * @param primaryStage Correspond à la fenêtre entière
     */

    private void construirePlateauJeu(Stage primaryStage) {
        // definir la troupe des acteurs et des decors
        Player p1 = new Player("Michel");
        Player p2 = new Player("Jean");

        setlPlayer(p1);
        setlPlayer(p2);

        // definir la scene principale
        Scene scene = new Scene(root, 2 * widthStep + width, 2 * heightStep + height, Color.ANTIQUEWHITE);
        scene.setFill(Color.BLANCHEDALMOND);
        // definir le decor
        initBoard();
        makeNode();
        // ajouter les acteurs
        makePiece();

        primaryStage.setTitle("Surakarta");
        primaryStage.setScene(scene);
        // afficher le theatre
        primaryStage.show();
    }


    /**
     * Permet la création des noeuds, de les ajouter à la scene également.
     */
    public void makeNode() {

        for (int i = 0; i <= 5; i++) {
            double x = (i + decalage) * widthStep;
            for (int j = 0; j <= 5; j++) {
                double y = (j + decalage) * heightStep;
                //instanciation de l'objet avec affectation des coordonnées
                Node c = new Node(x, y, false);
                // on les rend clickable
                c.setOnMouseClicked(this);
                // on ajoute au groupe root qui permet de regrouper la scene
                root.getChildren().add(c);
                lNode.add(c);
            }
        }

    }

    /**
     * Permet la création des pions, attribués également aux différents joueurs
     */

    public void makePiece() {

        for (int i = 0; i <= 5; i++) {
            double x = (i + decalage) * widthStep;
            for (int j = 0; j <= 1; j++) {
                double y = (j + decalage) * heightStep;
                //instanciation de l'objet avec attribution joueur, des coordonnées et de la couleur
                Piece p = new Piece(PieceType.P1, x, y, Color.GREY);
                //pion rendu clickable
                p.setOnMouseClicked(this);
                getlPlayer().get(0).setlPiece(p);
                //ajout au groupe
                root.getChildren().add(p);

                //instanciation de l'objet avec attribution joueur, des coordonnées et de la couleur
                p = new Piece(PieceType.P2, x, y + 4 * heightStep, Color.RED);
                //pion rendu clickable
                p.setOnMouseClicked(this);
                getlPlayer().get(1).setlPiece(p);
                //ajout au groupe
                root.getChildren().add(p);
            }
        }
        int t = 0;
        while (t != 36) {
            lNode.get(t).setVerification(true);
            t++;
            lNode.get(t).setVerification(true);
            t += 5;
        }
        int t1 = 4;
        while (t1 != 40) {
            lNode.get(t1).setVerification(true);
            t1++;
            lNode.get(t1).setVerification(true);
            t1 += 5;
        }
        /*for (int t=0;t<12;t++){
            lNode.get(t).setVerification(true);
        }

        for (int t1=24;t1<36;t1++){
            lNode.get(t1).setVerification(true);
        }*/
    }

    /**
     * Permet de créer le plateau du jeu avec les lignes et également les arcs de cercle
     */
    public void initBoard() {
        for (int i = 0; i < 6; i++) {
            Line line1 = new Line(decalage * widthStep, (i + decalage) * heightStep, (5 + decalage) * widthStep, (i + decalage) * heightStep);
            Line line2 = new Line((i + decalage) * widthStep, decalage * heightStep, (i + decalage) * widthStep, (5 + decalage) * heightStep);
            line1.setStrokeWidth(6);
            line1.setStroke(Color.BLACK);
            line2.setStrokeWidth(6);
            line2.setStroke(Color.BLACK);
            root.getChildren().add(line1);
            root.getChildren().add(line2);

        }


        initSmallArc(170, 170, 0);
        initSmallArc(440, 170, -90);
        initSmallArc(170, 440, 90);
        initSmallArc(440, 440, 180);

        initBigArc(170, 170, 0);
        initBigArc(440, 170, -90);
        initBigArc(170, 440, 90);
        initBigArc(440, 440, 180);

    }


    /**
     * Création du petit arc de cercle
     *
     * @param x     Position en abscisse de l'arc de cercle
     * @param y     Position en ordonnée de l'arc de cercle
     * @param angle L'angle de l'arc de cercle, permettant ainsi sa rotation et une bonne disposition
     */

    public void initSmallArc(int x, int y, double angle) {

        Arc arc = new Arc();
        arc.setCenterX(x);
        arc.setCenterY(y);
        arc.setRadiusX(55);
        arc.setRadiusY(55);
        arc.setStartAngle(angle);
        arc.setLength(270.0f);
        // type de l'arc, ouvert évitant ainsi le prolongement jusqu'au centre pour fermer l'arc
        arc.setType(ArcType.OPEN);
        //Couleur rouge pour la bordure
        arc.setStroke(Color.RED);
        //le remplissage est null assurant une "transparence"
        arc.setFill(null);
        arc.setId("Arc" + i);
        i++;
        //ajout au groupe
        root.getChildren().add(arc);

    }


    /**
     * Création du grand arc de cercle
     *
     * @param x     Position en abscisse de l'arc de cercle
     * @param y     Position en ordonnée de l'arc de cercle
     * @param angle L'angle de l'arc de cercle, permettant ainsi sa rotation et une bonne disposition
     */

    public void initBigArc(int x, int y, double angle) {
        Arc arc = new Arc();
        arc.setCenterX(x);
        arc.setCenterY(y);
        arc.setRadiusX(110);
        arc.setRadiusY(110);
        arc.setStartAngle(angle);
        arc.setLength(270.0f);
        // type de l'arc, ouvert évitant ainsi le prolongement jusqu'au centre pour fermer l'arc
        arc.setType(ArcType.OPEN);
        //Couleur rouge pour la bordure
        arc.setStroke(Color.GREEN);
        //le remplissage est null assurant une "transparence"
        arc.setFill(null);
        arc.setId("Arc" + i);
        i++;
        //ajout au groupe
        root.getChildren().add(arc);

    }


    public static void main(String[] args) {
        launch(args);
    }


    /**
     * Méthode permettant la gestion des événements liés à la souris
     *
     * @param mouseEvent Récupére le type d'événement
     */
    @Override
    public void handle(MouseEvent mouseEvent) {

        //si le clic est sur un jeton, alterne son etat selectionne ou non
        if (mouseEvent.getSource().getClass() == Piece.class && pionSelectionne == null) {
            Piece p = (Piece) mouseEvent.getSource();
            p.select();
            pionSelectionne = (p.isSelected() ? p : null);
        }

        //si le clic est sur un croisement, si un jeton a ete selectionne, il s'y deplace
        else if (mouseEvent.getSource().getClass() == Node.class) {
            Node p = (Node) mouseEvent.getSource();
            if (pionSelectionne != null && pionSelectionne.isSelected()) {
                int startX = (int) Math.round(pionSelectionne.getCenterX() / widthStep - decalage);
                int startY = (int) Math.round(pionSelectionne.getCenterY() / heightStep - decalage);
                int endX = (int) Math.round(p.getCenterX() / widthStep - decalage);
                int endY = (int) Math.round(p.getCenterY() / heightStep - decalage);
                System.err.println("startX, startY, endX, endY=" + startX + "," + startY + "," + endX + "," + endY);
                Piece piece = pionSelectionne;
                move(piece, p, startX, startY, endX, endY);
                pionSelectionne.select();
                //pour ne pas permettre le click sur le pion adverse
                pionSelectionne = null;
            }
        } else if (mouseEvent.getSource().getClass() == Piece.class) {
            Piece p = (Piece) mouseEvent.getSource();

            if (pionSelectionne != null && pionSelectionne.isSelected()) {
                int startX = (int) Math.round(pionSelectionne.getCenterX() / widthStep - decalage);
                int startY = (int) Math.round(pionSelectionne.getCenterY() / heightStep - decalage);
                int endX = (int) Math.round(p.getCenterX() / widthStep - decalage);
                int endY = (int) Math.round(p.getCenterY() / heightStep - decalage);
                System.err.println("startX, startY, endX, endY=" + startX + "," + startY + "," + endX + "," + endY);
                Piece piece = pionSelectionne;

                //on vérifie si l'on peut prendre le pion
                if (checkLaunch(piece, startX, startY, endX, endY) && p.getType() != piece.getType()) {
                    allKill(piece, p, startX, startY, endX, endY);
                    //pour ne pas permettre le click sur le pion adverse
                } else if (piece.isSelected()) {

                    /*PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished(event -> piece.select());
                    delay.play();*/
                    piece.select();
                    pionSelectionne = null;
                } else {
                    p.select();
                    pionSelectionne = null;
                }
            }
        }
    }


    /**
     * Permet de faire le mouvement d'un noeud à un autre
     *
     * @param piece Le pion qui bougera
     * @param p     Le noeud vers lequel le pion se déplacera
     */

    private void move(Piece piece, Node p, int startX, int startY, int endX, int endY) {
        for (int i = 0; i < 36; i++) {
            if (piece.getCenterX() == lNode.get(i).getCenterX() && piece.getCenterY() == lNode.get(i).getCenterY()) {
                if (p.getverification() == false && ((endX - startX == 0 && endY - startY == 0) || (endX - startX == 0 && endY - startY == 1) || (endX - startX == 1 && endY - startY == 0)
                        || (endX - startX == 0 && endY - startY == -1) || (endX - startX == -1 && endY - startY == 0) || (endX - startX == -1 && endY - startY == 1) || (endX - startX == 1 && endY - startY == -1)
                        || (endX - startX == -1 && endY - startY == -1) || (endX - startX == 1 && endY - startY == 1))) {
                    p.setVerification(true);
                    lNode.get(i).setVerification(false);
                    Timeline timeline = new Timeline();
                    double xdest = p.getCenterX();
                    double ydest = p.getCenterY();

                    KeyFrame move = new KeyFrame(new Duration(500),
                            new KeyValue(pionSelectionne.centerXProperty(), xdest),
                            new KeyValue(pionSelectionne.centerYProperty(), ydest));
                    timeline.getKeyFrames().add(move);

                    timeline.play();
                    break;
                } else {
                    break;
                }
            }
        }
    }

    /**
     * Permet de faire un déplacement tout en prenant le pion adverse
     *
     * @param piece le pion qui se déplacera
     * @param p     Le pion qui se fera prendre
     */
    private void kill(Piece piece, Piece p, int startX, int startY, int endX, int endY, int shiftX, int shiftY, boolean reverse, int radius) {
        //Chemin à "parcourir" pour l'animation complète
        Path path = new Path();

        for (Node n : getlNode()) {
            if (piece.getCenterX() == n.getCenterX() && piece.getCenterY() == n.getCenterY()) {
                n.setVerification(false);
            }
        }

        if((endX == 1 && startY == 1) || (endX == 2 && startY == 2) || (endY == 1 && startX == 1) || (endY == 2 && startX == 2)){
            path.getElements().addAll(new MoveTo(piece.getCenterX(), piece.getCenterY()), new LineTo(shiftX * widthStep, shiftY * heightStep));

        /*
        on ajoute au chemin les données en rapport avec l'arc de cercle
        MoveTo point de départ
        ArcTo l'arc de cercle
        sweepFlag => permet d'inverser l'arc de cercle, ici true car les coordonnées forment l'arc de cercle vers le bas
         */

            path.getElements().addAll(new MoveTo(shiftX * widthStep, shiftY * heightStep), new ArcTo(radius, radius, 0, shiftY * widthStep, shiftX * heightStep, true, reverse), new LineTo(p.getCenterX(), p.getCenterY()));
            //permet de faire l'animation, celle-ci durera 3 secondes
            PathTransition pt = new PathTransition(Duration.seconds(3), path, piece);

            //nombre de répétition que l'on souhaite avoir, ici 1
            pt.setCycleCount(1);
            //on lance l'animation
            pt.play();

            piece.setCenterX(p.getCenterX());
            piece.setCenterY(p.getCenterY());

            //lorsque l'animation est terminée alors on prend le pion
            pt.setOnFinished(e -> {
                if (piece.getType() == PieceType.P1) {
                    root.getChildren().remove(p);
                    getlPlayer().get(1).getlPiece().remove(p);
                } else {
                    System.out.println(root.getChildren().indexOf(p));
                    getlPlayer().get(0).getlPiece().remove(p);
                }
            });
        }

        }


    /**
     * Permet de vérifier si une prise est possible ou non
     *
     * @param piece  Le pion visé
     * @param startX l'abscisse du pion de départ
     * @param startY l'ordonnée du pion de départ
     * @param endX   l'abscisse du pion d'arrivée
     * @param endY   l'ordonnée du pion d'arrivée
     * @return true si l'on peut prendre un pion. Il est de type boolean
     */
    public boolean checkLaunch(Piece piece, int startX, int startY, int endX, int endY) {
        int cpt;

        if (piece.getType() == PieceType.P1) {
            System.out.println("P1");

            cpt = 0;
            if (startX == 1 || startY == 1) {
                if (endY == 1) {
                    for (Piece p : getlPlayer().get(0).getlPiece()) {
                        int _x = (int) Math.round(p.getCenterX() / widthStep - decalage);
                        int _y = (int) Math.round(p.getCenterY() / heightStep - decalage);
                        if (_x == 1) {
                            if (_y < endY && _y != startY) {
                                cpt++;
                                if (cpt > 0) {
                                    return false;
                                }
                            }
                        }
                        if (_y == 1) {
                            if (_x < endX && _x != startX) {
                                cpt++;
                                if (cpt > 0) {
                                    return false;
                                }
                            }
                        }
                    }
                } else if (endX == 1) {
                    for (Piece p : getlPlayer().get(0).getlPiece()) {
                        int _x = (int) Math.round(p.getCenterX() / widthStep - decalage);
                        int _y = (int) Math.round(p.getCenterY() / heightStep - decalage);
                        if (_x == 1) {
                            if (_y < endY && _y != startY) {
                                cpt++;
                                if (cpt > 0) {
                                    return false;
                                }
                            }
                        }
                        if (_y == 1) {
                            if (_x < endX && _x != startX) {
                                cpt++;
                                if (cpt > 0) {
                                    return false;
                                }
                            }
                        }
                    }
                } else if (endX == 4) {
                    for (Piece p : getlPlayer().get(0).getlPiece()) {
                        int _x = (int) Math.round(p.getCenterX() / widthStep - decalage);
                        int _y = (int) Math.round(p.getCenterY() / heightStep - decalage);
                        if (_x == 4) {
                            if (_y < endY && _y != startY) {
                                cpt++;
                                if (cpt > 0) {
                                    return false;
                                }
                            }
                        }
                        if (_y == 1) {
                            if (_x > endX && _x != startX) {
                                cpt++;
                                if (cpt > 0) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            } else if (startX == 2 || startY == 2) {
                if (endY == 2) {
                    for (Piece p : getlPlayer().get(0).getlPiece()) {
                        int _x = (int) Math.round(p.getCenterX() / widthStep - decalage);
                        int _y = (int) Math.round(p.getCenterY() / heightStep - decalage);
                        if (_x == 2) {
                            if (_y < endY && _y != startY) {
                                cpt++;
                                if (cpt > 0) {
                                    return false;
                                }
                            }
                        }
                        if (_y == 2) {
                            if (_x < endX && _x != startX) {
                                cpt++;
                                if (cpt > 0) {
                                    return false;
                                }
                            }
                        }
                    }
                } else if (endX == 2) {
                    for (Piece p : getlPlayer().get(0).getlPiece()) {
                        int _x = (int) Math.round(p.getCenterX() / widthStep - decalage);
                        int _y = (int) Math.round(p.getCenterY() / heightStep - decalage);
                        if (_x == 2) {
                            if (_y < endY && _y != startY) {
                                cpt++;
                                if (cpt > 0) {
                                    return false;
                                }
                            }
                        }
                        if (_y == 2) {
                            if (_x < endX && _x != startX) {
                                cpt++;
                                if (cpt > 0) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        } else if (piece.getType() == PieceType.P2) {
            System.out.println("P2");
            cpt = 0;
            if (startX == 1 || startY == 1) {
                if (endY == 1) {
                    for (Piece p : getlPlayer().get(1).getlPiece()) {
                        int _x = (int) Math.round(p.getCenterX() / widthStep - decalage);
                        int _y = (int) Math.round(p.getCenterY() / heightStep - decalage);
                        if (_x == 1) {
                            if (_y < endY && _y != startY) {
                                cpt++;
                                if (cpt > 0) {
                                    return false;
                                }
                            }
                        }
                        if (_y == 1) {
                            if (_x < endX && _x != startX) {
                                cpt++;
                                if (cpt > 0) {
                                    return false;
                                }
                            }
                        }
                    }
                } else if (endX == 1) {
                    for (Piece p : getlPlayer().get(1).getlPiece()) {
                        int _x = (int) Math.round(p.getCenterX() / widthStep - decalage);
                        int _y = (int) Math.round(p.getCenterY() / heightStep - decalage);
                        if (_x == 1) {
                            if (_y < endY && _y != startY) {
                                cpt++;
                                if (cpt > 0) {
                                    return false;
                                }
                            }
                        }
                        if (_y == 1) {
                            if (_x < endX && _x != startX) {
                                cpt++;
                                if (cpt > 0) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            } else if (startX == 2 || startY == 2) {
                if (endY == 2) {
                    for (Piece p : getlPlayer().get(1).getlPiece()) {
                        int _x = (int) Math.round(p.getCenterX() / widthStep - decalage);
                        int _y = (int) Math.round(p.getCenterY() / heightStep - decalage);
                        if (_x == 2) {
                            if (_y < endY && _y != startY) {
                                cpt++;
                                if (cpt > 0) {
                                    return false;
                                }
                            }
                        }
                        if (_y == 2) {
                            if (_x < endX && _x != startX) {
                                cpt++;
                                if (cpt > 0) {
                                    return false;
                                }
                            }
                        }
                    }
                } else if (endX == 2) {
                    for (Piece p : getlPlayer().get(1).getlPiece()) {
                        int _x = (int) Math.round(p.getCenterX() / widthStep - decalage);
                        int _y = (int) Math.round(p.getCenterY() / heightStep - decalage);
                        if (_x == 2) {
                            if (_y < endY && _y != startY) {
                                cpt++;
                                if (cpt > 0) {
                                    return false;
                                }
                            }
                        }
                        if (_y == 2) {
                            if (_x < endX && _x != startX) {
                                cpt++;
                                if (cpt > 0) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public void allKill(Piece piece, Piece p, int startX, int startY, int endX, int endY) {

        if (endX == 1 && startY == 1) {
            System.out.println("1");
            kill(piece, p, startX,startY,endX,endY,3, (decalage + startY), true, 55);
        } else if (endX == 2 && startY == 2) {
            System.out.println("2");
            kill(piece, p, startX,startY,endX,endY, 3, (decalage + startY), true, 110);
        } else if (endY == 1 && startX == 1) {
            System.out.println("3");
            kill(piece, p, startX,startY,endX,endY, (decalage + endY), 3, false, 55);
        } else if (endY == 2 && startX == 2) {
            System.out.println("4");
            kill(piece, p,  startX,startY,endX,endY,(decalage + endY), 3, false, 110);
        } else if (endX == 4 && startY == 1) {
            System.out.println("4");
            kill(piece, p,  startX,startY,endX,endY,8, 4, false, 55);
        }
    }
}
