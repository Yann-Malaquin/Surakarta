package fr.surakarta.game;

import fr.surakarta.grid.Node;
import fr.surakarta.piece.Piece;
import fr.surakarta.piece.PieceType;
import fr.surakarta.player.Player;
import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    @FXML
    private Group root = new Group();

    private int Tour = 0;

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
     * Logger permettant de suivre un pion, le déplacement, le pion selectionné etc
     */

    public static Logger logger = Logger.getLogger("Suivie");

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
        logger.setLevel(Level.ALL);
        Handler fh;

        try {
            fh = new FileHandler("./simulog.xml");
            logger.addHandler(fh);
        } catch (IOException e) {
            e.printStackTrace();
        }
        construirePlateauJeu(primaryStage);
    }

    /**
     * Permet la constrution du plateau de jeu
     *
     * @param primaryStage Correspond à la fenêtre entière
     */

    private void construirePlateauJeu(Stage primaryStage) {
        // definir la troupe des acteurs et des decors
        Player p1 = new Player("IA");
        Player p2 = new Player("Joueur");

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
    }

    /**
     * Permet de créer le plateau du jeu avec les lignes et également les arcs de cercle
     */
    public void initBoard() {

        //correspond à l'affichage du score pour les joueurs
        Label p1 = new Label("Score : " + getlPlayer().get(0).getScore());
        Label p2 = new Label("Score : " + getlPlayer().get(1).getScore());

        InputStream font = this.getClass().getResourceAsStream("/resources/Roman SD.ttf");

        Font f = Font.loadFont(font, 24);
        p1.setFont(f);
        p1.setLayoutX(240);
        p1.setLayoutY(10);

        p2.setFont(f);
        p2.setLayoutX(240);
        p2.setLayoutY(580);

        root.getChildren().addAll(p1, p2);


        //dessin des lignes
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


        //dessin des arcs
        initSmallArc(3 * widthStep, 3 * heightStep, 0);
        initSmallArc(8 * widthStep, 3 * heightStep, -90);
        initSmallArc(3 * widthStep, 8 * heightStep, 90);
        initSmallArc(8 * widthStep, 8 * heightStep, 180);

        initBigArc(3 * heightStep, 3 * heightStep, 0);
        initBigArc(8 * widthStep, 3 * heightStep, -90);
        initBigArc(3 * widthStep, 8 * heightStep, 90);
        initBigArc(8 * widthStep, 8 * heightStep, 180);

    }


    /**
     * Création du petit arc de cercle
     *
     * @param x     Position en abscisse de l'arc de cercle
     * @param y     Position en ordonnée de l'arc de cercle
     * @param angle L'angle de l'arc de cercle, permettant ainsi sa rotation et une bonne disposition
     */

    public void initSmallArc(double x, double y, double angle) {

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

    public void initBigArc(double x, double y, double angle) {
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
        if (Tour == 0) {
            //si le clic est sur un jeton, alterne son etat selectionne ou non
            if (mouseEvent.getSource().getClass() == Piece.class && pionSelectionne == null) {
                Piece p = (Piece) mouseEvent.getSource();
                if (p.getColor() == Color.RED) {
                    p.select();
                    pionSelectionne = (p.isSelected() ? p : null);
                }
            }

            //si le clic est sur un croisement, si un jeton a ete selectionne, il s'y deplace
            else if (mouseEvent.getSource().getClass() == Node.class) {
                Node p = (Node) mouseEvent.getSource();
                if (pionSelectionne != null && pionSelectionne.isSelected()) {
                    int startX = (int) Math.round(pionSelectionne.getCenterX() / widthStep - decalage);
                    int startY = (int) Math.round(pionSelectionne.getCenterY() / heightStep - decalage);
                    int endX = (int) Math.round(p.getCenterX() / widthStep - decalage);
                    int endY = (int) Math.round(p.getCenterY() / heightStep - decalage);
                    Piece piece = pionSelectionne;
                    if (move(piece, p, startX, startY, endX, endY)) {
                        Surakarta.logger.log(Level.INFO, "Pion sélectionné : (" + startX + ";" + startY + ")");
                        Surakarta.logger.log(Level.INFO, "Pion déplacé en : (" + endX + ";" + endY + ")");
                        pionSelectionne.select();
                        //pour ne pas permettre le click sur le pion adverse
                        pionSelectionne = null;
                        Tour = 1;
                    }
                }
            } else if (mouseEvent.getSource().getClass() == Piece.class) {
                Piece p = (Piece) mouseEvent.getSource();

                if (pionSelectionne != null && pionSelectionne.isSelected()) {
                    int startX = (int) Math.round(pionSelectionne.getCenterX() / widthStep - decalage);
                    int startY = (int) Math.round(pionSelectionne.getCenterY() / heightStep - decalage);
                    int endX = (int) Math.round(p.getCenterX() / widthStep - decalage);
                    int endY = (int) Math.round(p.getCenterY() / heightStep - decalage);

                    Piece piece = pionSelectionne;

                    //on vérifie si l'on peut prendre le pion
                    if (checkLaunch(piece, p, startX, startY, endX, endY) != 0 && p.getType() != piece.getType()) {
                        for (int i = 1; i <= 4; i++) {
                            allKill(piece, p, startX, startY, endX, endY, i);
                        }
                        Surakarta.logger.log(Level.INFO, "Pion sélectionné : (" + startX + ";" + startY + ")");
                        Surakarta.logger.log(Level.INFO, "Pion pris en: (" + endX + ";" + endY + ")");
                        Tour = 1;
                        pionSelectionne.select();
                        pionSelectionne = null;
                        //pour ne pas permettre le click sur le pion adverse
                    } else if (piece.isSelected()) {
                        piece.select();
                        pionSelectionne = null;
                    } else {
                        p.select();
                        pionSelectionne = null;
                    }
                }
            }
        }

    }

    /**
     * La méthode IA gère les coups fait par l'IA.
     */

    public void IA() {

        if (Tour == 1) {
            if (pionSelectionne == null) {
                int test = 0;
                //on test jusqu'à trouver un pion pouvant se déplacer
                while (test == 0) {
                    int nombreAleatoire = (int) (Math.random() * (lPlayer.get(0).getlPiece().size()));
                    Piece p = (Piece) lPlayer.get(0).getlPiece().get(nombreAleatoire);
                    int startX = (int) Math.round(p.getCenterX() / widthStep - decalage);
                    int startY = (int) Math.round(p.getCenterY() / heightStep - decalage);
                    for (Node n : lNode) {
                        int endX = (int) Math.round(n.getCenterX() / widthStep - decalage);
                        int endY = (int) Math.round(n.getCenterY() / heightStep - decalage);
                        if (p.getColor() == Color.GREY && n.getverification() == false && ((endX - startX == 0 && endY - startY == 0) || (endX - startX == 0 && endY - startY == 1) || (endX - startX == 1 && endY - startY == 0)
                                || (endX - startX == 0 && endY - startY == -1) || (endX - startX == -1 && endY - startY == 0) || (endX - startX == -1 && endY - startY == 1) || (endX - startX == 1 && endY - startY == -1)
                                || (endX - startX == -1 && endY - startY == -1) || (endX - startX == 1 && endY - startY == 1))) {
                            p.select();
                            pionSelectionne = (p.isSelected() ? p : null);
                            test = 1;
                            break;
                        }
                    }
                }
            }

            //condition permettant de prendre un pion adverse
            if (pionSelectionne != null && pionSelectionne.isSelected()) {
                /*kill*/
                int y = 0;
                for (Piece p1 : lPlayer.get(0).getlPiece()) {
                    int startX = (int) Math.round(p1.getCenterX() / widthStep - decalage);
                    int startY = (int) Math.round(p1.getCenterY() / heightStep - decalage);
                    if ((startX == 0 && startY == 0) || (startX == 5 && startY == 5) || (startX == 0 && startY == 5) || (startX == 5 && startY == 0)) {

                    } else {
                        for (Piece p2 : lPlayer.get(1).getlPiece()) {
                            int endX = (int) Math.round(p2.getCenterX() / widthStep - decalage);
                            int endY = (int) Math.round(p2.getCenterY() / heightStep - decalage);
                            if (checkLaunch(p1, p2, startX, startY, endX, endY) != 0) {
                                for (int i = 1; i <= 4; i++) {
                                    allKill(p1, p2, startX, startY, endX, endY, i);
                                }
                                Surakarta.logger.log(Level.INFO, "Pion sélectionné : (" + startX + ";" + startY + ")");
                                Surakarta.logger.log(Level.INFO, "Pion pris en : (" + endX + ";" + endY + ")");
                                y = 1;
                                Tour = 0;
                                pionSelectionne.select();
                                pionSelectionne = null;
                                break;
                            }
                        }
                        if (y == 1) {
                            break;
                        }
                    }
                }
                //condition permettant de déplacer un pion
                if (y == 0) {
                    /*MOVE*/
                    for (Node n : lNode) {
                        if (pionSelectionne != null && pionSelectionne.isSelected()) {
                            int startX = (int) Math.round(pionSelectionne.getCenterX() / widthStep - decalage);
                            int startY = (int) Math.round(pionSelectionne.getCenterY() / heightStep - decalage);
                            int endX = (int) Math.round(n.getCenterX() / widthStep - decalage);
                            int endY = (int) Math.round(n.getCenterY() / heightStep - decalage);
                            Piece piece = pionSelectionne;
                            if (move(piece, n, startX, startY, endX, endY)) {
                                Surakarta.logger.log(Level.INFO, "Pion sélectionné : (" + startX + ";" + startY + ")");
                                Surakarta.logger.log(Level.INFO, "Pion déplacé en  : (" + endX + ";" + endY + ")");
                                pionSelectionne.select();
                                //pour ne pas permettre le click sur le pion adverse
                                pionSelectionne = null;
                            }
                            Tour = 0;
                        }
                    }
                }
            }
        }
    }


    /**
     * Permet de faire le mouvement d'un noeud à un autre
     *
     * @param piece  piece sélectionné
     * @param p      noeud cible
     * @param startX abscisse de départ
     * @param startY ordonnée de départ
     * @param endX   abscisse d'arrivée
     * @param endY   ordonnée d'arrivée
     * @return un boolean pour savoir si le mouvement est possible
     */
    private Boolean move(Piece piece, Node p, int startX, int startY, int endX, int endY) {
        for (int i = 0; i < 36; i++) {
            if (piece.getCenterX() == lNode.get(i).getCenterX() && piece.getCenterY() == lNode.get(i).getCenterY()) {
                if (startX == endX && startY == endY) {
                    return false;
                } else if (p.getverification() == false && ((endX - startX == 0 && endY - startY == 0) || (endX - startX == 0 && endY - startY == 1) || (endX - startX == 1 && endY - startY == 0)
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
                    if (Tour == 0) {
                        timeline.setOnFinished(e -> {
                            IA();
                        });
                    }
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Permet de faire un déplacement tout en prenant le pion adverse
     *
     * @param piece   piece sélectionné
     * @param p       noeud cible
     * @param startX  abscisse de départ
     * @param startY  ordonnée de départ
     * @param endX    abscisse d'arrivée
     * @param endY    ordonnée d'arrivée
     * @param shiftX  le décalage a effectué en abscisse
     * @param shiftY  le déclaage a effectué en ordonnée
     * @param reverse si l'arc doit être inversé ou non
     * @param radius  le rayon du cercle
     */
    private void kill(Piece piece, Piece p, int startX, int startY, int endX, int endY, int shiftX, int shiftY, boolean reverse, int radius) {
        //Chemin à "parcourir" pour l'animation complète
        Path path = new Path();

        PathTransition pt = new PathTransition();
        for (Node n : getlNode()) {
            if (piece.getCenterX() == n.getCenterX() && piece.getCenterY() == n.getCenterY()) {
                n.setVerification(false);
            }
        }

        if ((endX == 1 && startY == 1) || (endX == 2 && startY == 2) || (endY == 1 && startX == 1 && startY != 4) || (endY == 2 && startX == 2 && startY != 3)) {
            System.out.println("renter");
            path.getElements().addAll(new MoveTo(piece.getCenterX(), piece.getCenterY()), new LineTo(shiftX * widthStep, shiftY * heightStep));

        /*
        on ajoute au chemin les données en rapport avec l'arc de cercle
        MoveTo point de départ
        ArcTo l'arc de cercle
        sweepFlag => permet d'inverser l'arc de cercle, ici true car les coordonnées forment l'arc de cercle vers le bas
         */
            path.getElements().addAll(new MoveTo(shiftX * widthStep, shiftY * heightStep), new ArcTo(radius, radius, 0, shiftY * widthStep, shiftX * heightStep, true, reverse), new LineTo(p.getCenterX(), p.getCenterY()));
            //permet de faire l'animation, celle-ci durera 3 secondes
            pt = new PathTransition(Duration.seconds(3), path, piece);

            //nombre de répétition que l'on souhaite avoir, ici 1
            pt.setCycleCount(1);
            //on lance l'animation
            pt.play();

            piece.setCenterX(p.getCenterX());
            piece.setCenterY(p.getCenterY());


        } else if ((endX == 4 && startY == 1 && endY != 3 && endY != 2) || (endY == 4 && startX == 1)) {

            path.getElements().addAll(new MoveTo(piece.getCenterX(), piece.getCenterY()), new LineTo(shiftX * widthStep, shiftY * heightStep));
            path.getElements().addAll(new MoveTo(shiftX * widthStep, shiftY * heightStep), new ArcTo(radius, radius, 0, (shiftX - 1) * widthStep, (shiftY - 1) * heightStep, true, reverse), new LineTo(p.getCenterX(), p.getCenterY()));
            //permet de faire l'animation, celle-ci durera 3 secondes
            pt = new PathTransition(Duration.seconds(3), path, piece);

            //nombre de répétition que l'on souhaite avoir, ici 1
            pt.setCycleCount(1);
            //on lance l'animation
            pt.play();

            piece.setCenterX(p.getCenterX());
            piece.setCenterY(p.getCenterY());


        } else if ((endX == 3 && startY == 2) || (endY == 3 && startX == 2)) {
            path.getElements().addAll(new MoveTo(piece.getCenterX(), piece.getCenterY()), new LineTo(shiftX * widthStep, shiftY * heightStep));
            path.getElements().addAll(new MoveTo(shiftX * widthStep, shiftY * heightStep), new ArcTo(radius, radius, 0, (shiftX - 2) * widthStep, (shiftY - 2) * heightStep, true, reverse), new LineTo(p.getCenterX(), p.getCenterY()));
            //permet de faire l'animation, celle-ci durera 3 secondes
            pt = new PathTransition(Duration.seconds(3), path, piece);

            //nombre de répétition que l'on souhaite avoir, ici 1
            pt.setCycleCount(1);
            //on lance l'animation
            pt.play();

            piece.setCenterX(p.getCenterX());
            piece.setCenterY(p.getCenterY());

        } else if ((endY == 2 && startX == 3 && startY != 3) || (endX == 2 && startY == 3) || (endY == 2 && startX == 2)) {
            path.getElements().addAll(new MoveTo(piece.getCenterX(), piece.getCenterY()), new LineTo(shiftX * widthStep, shiftY * heightStep));
            path.getElements().addAll(new MoveTo(shiftX * widthStep, shiftY * heightStep), new ArcTo(radius, radius, 0, (shiftX + 2) * widthStep, (shiftY + 2) * heightStep, true, reverse), new LineTo(p.getCenterX(), p.getCenterY()));
            //permet de faire l'animation, celle-ci durera 3 secondes

            pt = new PathTransition(Duration.seconds(3), path, piece);

            //nombre de répétition que l'on souhaite avoir, ici 1
            pt.setCycleCount(1);
            //on lance l'animation
            pt.play();

            piece.setCenterX(p.getCenterX());
            piece.setCenterY(p.getCenterY());


        } else if ((endX == 1 && startY == 4) || (endY == 1 && startX == 4)) {
            path.getElements().addAll(new MoveTo(piece.getCenterX(), piece.getCenterY()), new LineTo(shiftX * widthStep, shiftY * heightStep));
            path.getElements().addAll(new MoveTo(shiftX * widthStep, shiftY * heightStep), new ArcTo(radius, radius, 0, (shiftX + 1) * widthStep, (shiftY + 1) * heightStep, true, reverse), new LineTo(p.getCenterX(), p.getCenterY()));
            //permet de faire l'animation, celle-ci durera 3 secondes

            pt = new PathTransition(Duration.seconds(3), path, piece);

            //nombre de répétition que l'on souhaite avoir, ici 1
            pt.setCycleCount(1);
            //on lance l'animation
            pt.play();

            piece.setCenterX(p.getCenterX());
            piece.setCenterY(p.getCenterY());

        } else if (endX == 4 && startY == 4 && endY != 3) {
            path.getElements().addAll(new MoveTo(piece.getCenterX(), piece.getCenterY()), new LineTo(shiftX * widthStep, shiftY * heightStep));
            path.getElements().addAll(new MoveTo(shiftX * widthStep, shiftY * heightStep), new ArcTo(radius, radius, 0, (shiftX - 1) * widthStep, (shiftY + 1) * heightStep, true, reverse), new LineTo(p.getCenterX(), p.getCenterY()));
            //permet de faire l'animation, celle-ci durera 3 secondes

            pt = new PathTransition(Duration.seconds(3), path, piece);

            //nombre de répétition que l'on souhaite avoir, ici 1
            pt.setCycleCount(1);
            //on lance l'animation
            pt.play();

            piece.setCenterX(p.getCenterX());
            piece.setCenterY(p.getCenterY());

        } else if (endX == 3 && startY == 3) {
            path.getElements().addAll(new MoveTo(piece.getCenterX(), piece.getCenterY()), new LineTo(shiftX * widthStep, shiftY * heightStep));
            path.getElements().addAll(new MoveTo(shiftX * widthStep, shiftY * heightStep), new ArcTo(radius, radius, 0, (shiftX - 2) * widthStep, (shiftY + 2) * heightStep, true, reverse), new LineTo(p.getCenterX(), p.getCenterY()));
            //permet de faire l'animation, celle-ci durera 3 secondes

            pt = new PathTransition(Duration.seconds(3), path, piece);

            //nombre de répétition que l'on souhaite avoir, ici 1
            pt.setCycleCount(1);
            //on lance l'animation
            pt.play();

            piece.setCenterX(p.getCenterX());
            piece.setCenterY(p.getCenterY());

        } else if (endY == 4 && startX == 4) {
            path.getElements().addAll(new MoveTo(piece.getCenterX(), piece.getCenterY()), new LineTo(shiftX * widthStep, shiftY * heightStep));
            path.getElements().addAll(new MoveTo(shiftX * widthStep, shiftY * heightStep), new ArcTo(radius, radius, 0, (shiftX + 1) * widthStep, (shiftY - 1) * heightStep, true, reverse), new LineTo(p.getCenterX(), p.getCenterY()));
            //permet de faire l'animation, celle-ci durera 3 secondes

            pt = new PathTransition(Duration.seconds(3), path, piece);

            //nombre de répétition que l'on souhaite avoir, ici 1
            pt.setCycleCount(1);
            //on lance l'animation
            pt.play();

            piece.setCenterX(p.getCenterX());
            piece.setCenterY(p.getCenterY());

        } else if (endY == 3 && startX == 3) {
            path.getElements().addAll(new MoveTo(piece.getCenterX(), piece.getCenterY()), new LineTo(shiftX * widthStep, shiftY * heightStep));
            path.getElements().addAll(new MoveTo(shiftX * widthStep, shiftY * heightStep), new ArcTo(radius, radius, 0, (shiftX + 2) * widthStep, (shiftY - 2) * heightStep, true, reverse), new LineTo(p.getCenterX(), p.getCenterY()));
            //permet de faire l'animation, celle-ci durera 3 secondes

            pt = new PathTransition(Duration.seconds(3), path, piece);


            //nombre de répétition que l'on souhaite avoir, ici 1
            pt.setCycleCount(1);
            //on lance l'animation
            pt.play();

            piece.setCenterX(p.getCenterX());
            piece.setCenterY(p.getCenterY());

            //lorsque l'animation est terminée alors on prend le pion
        }

        //lorsque l'animation est terminée alors on prend le pion
        pt.setOnFinished(e -> {
            if (piece.getType() == PieceType.P1) {
                root.getChildren().remove(p);
                getlPlayer().get(1).getlPiece().remove(p);

                if (getlPlayer().get(1).getScore() == 3) {
                    displayPopupWinner(getlPlayer().get(0));
                } else if (getlPlayer().get(1).getlPiece().size() == 0) {
                    getlPlayer().get(0).setScore(1);
                    cleanUp();
                }

            } else {
                root.getChildren().remove(p);
                getlPlayer().get(0).getlPiece().remove(p);


                if (getlPlayer().get(0).getScore() == 3) {
                    displayPopupWinner(getlPlayer().get(1));
                } else if (getlPlayer().get(0).getlPiece().size() == 0) {
                    getlPlayer().get(1).setScore(1);
                    cleanUp();
                }
            }
            if (Tour == 1) {
                IA();
            }
        });
    }

    /**
     * Méthode qui test si un pion est prenable en fonction des arcs
     *
     * @param piece  piece sélectionné
     * @param p      noeud cible
     * @param startX abscisse de départ
     * @param startY ordonnée de départ
     * @param endX   abscisse d'arrivée
     * @param endY   ordonnée d'arrivée
     */
    public void allKill(Piece piece, Piece p, int startX, int startY, int endX, int endY, int i) {

        //arcs de cercle haut gauche
        if (i == 1) {
            if (endX == 1 && startY == 1 && endY != 3 && startX != 5) {
                System.out.println("kill1");
                kill(piece, p, startX, startY, endX, endY, 3, 4, true, 55);
            } else if (endX == 2 && startY == 2) {
                System.out.println("kill2");
                kill(piece, p, startX, startY, endX, endY, 3, 5, true, 110);
            } else if (endY == 1 && startX == 1) {
                System.out.println("kill3");
                kill(piece, p, startX, startY, endX, endY, 4, 3, false, 55);
            } else if (endY == 2 && startX == 2) {
                System.out.println("kill4");
                kill(piece, p, startX, startY, endX, endY, 5, 3, false, 110);
            }

            //arc de cercle haut droite
        } else if (i == 2) {
            if (endX == 4 && startY == 1 && endY != 3 && endY != 2) {
                System.out.println("kill5");
                kill(piece, p, startX, startY, endX, endY, 8, 4, false, 55);
            } else if (endX == 3 && startY == 2 && endY != 2) {
                System.out.println("kill6");
                kill(piece, p, startX, startY, endX, endY, 8, 5, false, 110);
            } else if (endY == 1 && startX == 4) {
                System.out.println("kill7");
                kill(piece, p, startX, startY, endX, endY, 7, 3, true, 55);
            } else if (endY == 2 && startX == 3) {
                System.out.println("kill8");
                kill(piece, p, startX, startY, endX, endY, 6, 3, true, 110);
            }

            //arc de cercle bas gauche
        } else if (i == 3) {
            if (endX == 1 && startY == 4) {
                System.out.println("kill9");
                kill(piece, p, startX, startY, endX, endY, 3, 7, false, 55);
            } else if (endX == 2 && startY == 3 && startX != 5) {
                System.out.println("kill10");
                kill(piece, p, startX, startY, endX, endY, 3, 6, false, 110);
            } else if (endY == 4 && startX == 1) {
                System.out.println("kill11");
                kill(piece, p, startX, startY, endX, endY, 4, 8, true, 55);
            } else if (endY == 3 && startX == 2 && endX != 3) {
                System.out.println("kill12");
                kill(piece, p, startX, startY, endX, endY, 5, 8, true, 110);
            }

            //arc de cercle bas droite
        } else if (i == 4) {
            if (endX == 4 && startY == 4) {
                System.out.println("kill13");
                kill(piece, p, startX, startY, endX, endY, 8, 7, true, 55);
            } else if (endX == 3 && startY == 3) {
                System.out.println("kill14");
                kill(piece, p, startX, startY, endX, endY, 8, 6, true, 110);
            } else if (endY == 4 && startX == 4) {
                System.out.println("kill15" + startX + " " + endY);
                kill(piece, p, startX, startY, endX, endY, 7, 8, false, 55);
            } else if (endY == 3 && startX == 3) {
                System.out.println("kill16");
                kill(piece, p, startX, startY, endX, endY, 6, 8, false, 110);
            }
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

    public int checkLaunch(Piece piece, Piece p, int startX, int startY, int endX, int endY) {

        int player1, player2;
        if (piece.getType() == PieceType.P1) {
            player1 = 0;
            player2 = 1;
        } else {
            player1 = 1;
            player2 = 0;
        }

        if (checkLittleArcHL(player1, player2, piece, p, startX, startY, endX, endY)) {
            return 1;
        } else if (checkBigArcHL(player1, player2, piece, p, startX, startY, endX, endY)) {
            return 1;
        } else if (checkLittleArcHR(player1, player2, piece, p, startX, startY, endX, endY)) {
            return 2;
        } else if (checkBigArcHR(player1, player2, piece, p, startX, startY, endX, endY)) {
            return 2;
        } else if (checkLittleArcLL(player1, player2, piece, p, startX, startY, endX, endY)) {
            return 3;
        } else if (checkBigArcLL(player1, player2, piece, p, startX, startY, endX, endY)) {
            return 3;
        } else if (checkLittleArcLR(player1, player2, piece, p, startX, startY, endX, endY)) {
            return 4;
        } else if (checkBigArcLR(player1, player2, piece, p, startX, startY, endX, endY)) {
            return 4;
        }
        return 0;
    }

    /**
     * Methode qui verifie si l'on passe par le petit arc en haut à gauche
     *
     * @param player1 le joueur qui joue
     * @param player2 le joueur qui subit
     * @param piece   Le pion qui bougera
     * @param p       le pion visé
     * @param startX  l'abscisse du pion de départ
     * @param startY  l'ordonnée du pion de départ
     * @param endX    l'abscisse du pion d'arrivée
     * @param endY    l'ordonnée du pion d'arrivée
     */

    public boolean checkLittleArcHL(int player1, int player2, Piece piece, Piece p, int startX, int startY, int endX, int endY) {
        int x, y, cpt = 0;


        if (endX == 1 && startY == 1) {
            for (Piece _p : getlPlayer().get(player1).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (y == startY && x < startX && _p != piece) {
                    cpt++;
                }

                if (x == 1 && y < endY && _p != piece) {
                    cpt++;
                }
            }
            for (Piece _p : getlPlayer().get(player2).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (x < startX && y == startY && _p != p) {
                    cpt++;
                }

                if (x == 1 && y < endY && _p != p) {
                    cpt++;
                }
            }
            if (cpt == 0) {
                return true;
            }
        } else if (endY == 1 && startX == 1) {
            for (Piece _p : getlPlayer().get(player1).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (y == endY && x < endX && _p != piece) {
                    cpt++;
                }

                if (x == 1 && y < startY && _p != piece) {
                    cpt++;
                }
            }
            for (Piece _p : getlPlayer().get(player2).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (x < endX && y == endY && _p != p) {
                    cpt++;
                }

                if (x == 1 && y < startY && _p != p) {
                    cpt++;
                }
            }

            if (cpt == 0 && startX != endX) {
                return true;
            }
        }
        return false;
    }

    /**
     * Methode qui verifie si l'on passe par le grand arc en haut à gauche
     *
     * @param player1 le joueur qui joue
     * @param player2 le joueur qui subit
     * @param piece   Le pion qui bougera
     * @param p       le pion visé
     * @param startX  l'abscisse du pion de départ
     * @param startY  l'ordonnée du pion de départ
     * @param endX    l'abscisse du pion d'arrivée
     * @param endY    l'ordonnée du pion d'arrivée
     */

    public boolean checkBigArcHL(int player1, int player2, Piece piece, Piece p, int startX, int startY, int endX, int endY) {
        int x, y, cpt = 0;

        if (endX == 2 && startY == 2) {
            for (Piece _p : getlPlayer().get(player1).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (y == startY && x < startX && _p != piece) {
                    cpt++;
                }

                if (x == 2 && y < endY && _p != piece) {
                    cpt++;
                }
            }
            for (Piece _p : getlPlayer().get(player2).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (x < startX && y == startY && _p != p) {
                    cpt++;
                }

                if (x == 2 && y < endY && _p != p) {
                    cpt++;
                }
            }

            if (cpt == 0) {
                return true;
            }
        } else if (endY == 2 && startX == 2) {
            for (Piece _p : getlPlayer().get(player1).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (y == endY && x < endX && _p != piece) {
                    cpt++;
                }

                if (x == 2 && y < startY && _p != piece) {
                    cpt++;
                }
            }
            for (Piece _p : getlPlayer().get(player2).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (x < endX && y == endY && _p != p) {
                    cpt++;
                }

                if (x == 2 && y < startY && _p != p) {
                    cpt++;
                }
            }

            if (cpt == 0 && startX != endX) {
                return true;
            }

        }
        return false;
    }

    /**
     * Methode qui verifie si l'on passe par le petit arc en haut à droite
     *
     * @param player1 le joueur qui joue
     * @param player2 le joueur qui subit
     * @param piece   Le pion qui bougera
     * @param p       le pion visé
     * @param startX  l'abscisse du pion de départ
     * @param startY  l'ordonnée du pion de départ
     * @param endX    l'abscisse du pion d'arrivée
     * @param endY    l'ordonnée du pion d'arrivée
     */

    public boolean checkLittleArcHR(int player1, int player2, Piece piece, Piece p, int startX, int startY, int endX, int endY) {
        int x, y, cpt = 0;

        if (endX == 4 && startY == 1) {
            for (Piece _p : getlPlayer().get(player1).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (y == startY && x > startX && _p != piece) {
                    cpt++;
                }

                if (x == 4 && y < endY && _p != piece) {
                    cpt++;
                }
            }
            for (Piece _p : getlPlayer().get(player2).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (x > startX && y == startY && _p != p) {
                    cpt++;
                }

                if (x == 4 && y < endY && _p != p) {
                    cpt++;
                }
            }

            if (cpt == 0) {
                return true;
            }
        } else if (endY == 1 && startX == 4) {
            for (Piece _p : getlPlayer().get(player1).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (y == endY && x > endX && _p != piece) {
                    cpt++;
                }

                if (x == 4 && y < startY && _p != piece) {
                    cpt++;
                }
            }
            for (Piece _p : getlPlayer().get(player2).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (x > endX && y == endY && _p != p) {
                    cpt++;
                }

                if (x == 4 && y < startY && _p != p) {
                    cpt++;
                }
            }

            if (cpt == 0 && startX != endX) {
                return true;
            }
        }

        return false;
    }

    /**
     * Methode qui verifie si l'on passe par le grand arc en haut à droite
     *
     * @param player1 le joueur qui joue
     * @param player2 le joueur qui subit
     * @param piece   Le pion qui bougera
     * @param p       le pion visé
     * @param startX  l'abscisse du pion de départ
     * @param startY  l'ordonnée du pion de départ
     * @param endX    l'abscisse du pion d'arrivée
     * @param endY    l'ordonnée du pion d'arrivée
     */

    public boolean checkBigArcHR(int player1, int player2, Piece piece, Piece p, int startX, int startY, int endX, int endY) {
        int x, y, cpt = 0;

        if (endX == 3 && startY == 2 && endY != 3) {
            for (Piece _p : getlPlayer().get(player1).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (y == startY && x > startX && _p != piece) {
                    cpt++;
                }

                if (x == 3 && y < endY && _p != piece) {
                    cpt++;
                }
            }
            for (Piece _p : getlPlayer().get(player2).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (x > startX && y == startY && _p != p) {
                    cpt++;
                }

                if (x == 3 && y < endY && _p != p) {
                    cpt++;
                }
            }

            if (cpt == 0) {
                return true;
            }
        } else if (endY == 2 && startX == 3) {
            for (Piece _p : getlPlayer().get(player1).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (y == endY && x > endX && _p != piece) {
                    cpt++;
                }

                if (x == 3 && y < startY && _p != piece) {
                    cpt++;
                }
            }
            for (Piece _p : getlPlayer().get(player2).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (x > endX && y == endY && _p != p) {
                    cpt++;
                }

                if (x == startX && y < startY && _p != p) {
                    cpt++;
                }
            }

            if (cpt == 0 && startX != endX) {
                return true;
            }
        }

        return false;
    }

    /**
     * Methode qui verifie si l'on passe par le petit arc en bas à gauche
     *
     * @param player1 le joueur qui joue
     * @param player2 le joueur qui subit
     * @param piece   Le pion qui bougera
     * @param p       le pion visé
     * @param startX  l'abscisse du pion de départ
     * @param startY  l'ordonnée du pion de départ
     * @param endX    l'abscisse du pion d'arrivée
     * @param endY    l'ordonnée du pion d'arrivée
     */

    public boolean checkLittleArcLL(int player1, int player2, Piece piece, Piece p, int startX, int startY, int endX, int endY) {
        int x, y, cpt = 0;

        if (endX == 1 && startY == 4) {
            for (Piece _p : getlPlayer().get(player1).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (y == startY && x < startX && _p != piece) {
                    cpt++;
                }

                if (x == 1 && y > endY && _p != piece) {
                    cpt++;
                }
            }
            for (Piece _p : getlPlayer().get(player2).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (x < startX && y == startY && _p != p) {
                    cpt++;
                }

                if (x == 0 && y > endY && _p != p) {
                    cpt++;
                }
            }

            if (cpt == 0) {
                return true;
            }
        } else if (endY == 4 && startX == 1) {
            for (Piece _p : getlPlayer().get(player1).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (y == endY && x < endX && _p != piece) {
                    cpt++;
                }

                if (x == 1 && y > startY && _p != piece) {
                    cpt++;
                }
            }
            for (Piece _p : getlPlayer().get(player2).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (x < endX && y == endY && _p != p) {
                    cpt++;
                }

                if (x == startX && y > startY && _p != p) {
                    cpt++;
                }
            }

            if (cpt == 0 && startX != endX) {
                return true;
            }
        }

        return false;

    }

    /**
     * Methode qui verifie si l'on passe par le grand arc en bas à gauche
     *
     * @param player1 le joueur qui joue
     * @param player2 le joueur qui subit
     * @param piece   Le pion qui bougera
     * @param p       le pion visé
     * @param startX  l'abscisse du pion de départ
     * @param startY  l'ordonnée du pion de départ
     * @param endX    l'abscisse du pion d'arrivée
     * @param endY    l'ordonnée du pion d'arrivée
     */
    public boolean checkBigArcLL(int player1, int player2, Piece piece, Piece p, int startX, int startY, int endX, int endY) {

        int x, y, cpt = 0;

        if (endX == 2 && startY == 3) {
            for (Piece _p : getlPlayer().get(player1).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (y == startY && x < startX && _p != piece) {
                    cpt++;
                }

                if (x == 2 && y > endY && _p != piece) {
                    cpt++;
                }
            }
            for (Piece _p : getlPlayer().get(player2).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (x < startX && y == startY && _p != p) {
                    cpt++;
                }

                if (x == 2 && y > endY && _p != p) {
                    cpt++;
                }
            }

            if (cpt == 0) {
                return true;
            }
        } else if (endY == 3 && startX == 2) {
            for (Piece _p : getlPlayer().get(player1).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (y == endY && x < endX && _p != piece) {
                    cpt++;
                }

                if (x == 2 && y > startY && _p != piece) {
                    cpt++;
                }
            }
            for (Piece _p : getlPlayer().get(player2).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (x < endX && y == endY && _p != p) {
                    cpt++;
                }

                if (x == startX && y > startY && _p != p) {

                    cpt++;
                }
            }

            if (cpt == 0 && startX != endX) {
                return true;
            }
        }

        return false;


    }

    /**
     * Methode qui verifie si l'on passe par le petit arc en bas à droite
     *
     * @param player1 le joueur qui joue
     * @param player2 le joueur qui subit
     * @param piece   Le pion qui bougera
     * @param p       le pion visé
     * @param startX  l'abscisse du pion de départ
     * @param startY  l'ordonnée du pion de départ
     * @param endX    l'abscisse du pion d'arrivée
     * @param endY    l'ordonnée du pion d'arrivée
     */

    boolean checkLittleArcLR(int player1, int player2, Piece piece, Piece p, int startX, int startY, int endX, int endY) {
        int x, y, cpt = 0;
        if (endX == 4 && startY == 4) {
            for (Piece _p : getlPlayer().get(player1).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (y == startY && x > startX && _p != piece) {
                    cpt++;
                }

                if (x == 4 && y > endY && _p != piece) {
                    cpt++;
                }
            }
            for (Piece _p : getlPlayer().get(player2).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (x > startX && y == startY && _p != p) {
                    cpt++;
                }

                if (x == 4 && y > endY && _p != p) {
                    cpt++;
                }
            }

            if (cpt == 0) {
                return true;
            }
        } else if (endY == 4 && startX == 4) {
            for (Piece _p : getlPlayer().get(player1).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (y == endY && x > endX && _p != piece) {
                    cpt++;
                }

                if (x == 4 && y > startY && _p != piece) {
                    cpt++;
                }
            }
            for (Piece _p : getlPlayer().get(player2).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (x > endX && y == endY && _p != p) {
                    cpt++;
                }

                if (x == startX && y > startY && _p != p) {
                    cpt++;
                }
            }

            if (cpt == 0 && startX != endX) {
                return true;
            }
        }
        return false;
    }

    /**
     * Methode qui verifie si l'on passe par le grand arc en bas à droite
     *
     * @param player1 le joueur qui joue
     * @param player2 le joueur qui subit
     * @param piece   Le pion qui bougera
     * @param p       le pion visé
     * @param startX  l'abscisse du pion de départ
     * @param startY  l'ordonnée du pion de départ
     * @param endX    l'abscisse du pion d'arrivée
     * @param endY    l'ordonnée du pion d'arrivée
     */

    public boolean checkBigArcLR(int player1, int player2, Piece piece, Piece p, int startX, int startY, int endX, int endY) {

        int x, y, cpt = 0;

        if (endX == 3 && startY == 3) {
            for (Piece _p : getlPlayer().get(player1).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (y == startY && x > startX && _p != piece) {
                    cpt++;
                }

                if (x == 3 && y > endY && _p != piece) {
                    cpt++;
                }
            }
            for (Piece _p : getlPlayer().get(player2).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (x > startX && y == startY && _p != p) {
                    cpt++;
                }

                if (x == 3 && y > endY && _p != p) {
                    cpt++;
                }
            }

            if (cpt == 0) {
                return true;
            }
        } else if (endY == 3 && startX == 3) {
            for (Piece _p : getlPlayer().get(player1).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (y == endY && x > endX && _p != piece) {
                    cpt++;
                }

                if (x == 3 && y > startY && _p != piece) {
                    cpt++;
                }
            }
            for (Piece _p : getlPlayer().get(player2).getlPiece()) {
                x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (x > endX && y == endY && _p != p) {
                    cpt++;
                }

                if (x == startX && y > startY && _p != p) {
                    cpt++;
                }
            }

            if (cpt == 0 && startX != endX) {
                return true;
            }
        }
        return false;
    }


    /**
     * Permet d'afficher une popup avec le nom et le score du joueur gagnant
     *
     * @param p le joueur gagnant
     */
    public void displayPopupWinner(Player p) {

        Stage primaryStage = new Stage();

        VBox vb = new VBox();
        vb.setPrefSize(400, 200);
        Label winner = new Label(), score = new Label();

        Group root = new Group();
        AnchorPane ap = new AnchorPane();
        ap.setPrefSize(400, 200);
        root.getChildren().add(ap);
        ap.getChildren().add(vb);
        vb.setAlignment(Pos.CENTER);

        InputStream font = this.getClass().getResourceAsStream("/resources/Roman SD.ttf");
        Font f = Font.loadFont(font, 24);

        InputStream background = this.getClass().getResourceAsStream("/resources/fond_winner.jpg");

        Image im = new Image(background, 400, 200, false, false);
        BackgroundImage myBI = new BackgroundImage(im,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        ap.setBackground(new Background(myBI));

        winner.setText(p.getPseudo() + " à gagné");
        score.setText("Avec un score de " + p.getScore());
        winner.setFont(f);
        winner.setTextFill(Color.WHITE);
        score.setFont(f);
        score.setTextFill(Color.WHITE);
        vb.setSpacing(25);

        vb.getChildren().add(winner);
        vb.getChildren().add(score);


        root.getChildren().add(vb);

        primaryStage.setScene(new Scene(root, 400, 200));
        primaryStage.setResizable(false);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setTitle("Winner");
        primaryStage.show();
        //transition permettant d'afficher un certains temps la popup
        PauseTransition delay = new PauseTransition(Duration.seconds(10));
        delay.setOnFinished(e -> {
            Platform.exit();
        });
        delay.play();


    }

    /**
     * cleanUp() permet de redémarrer une nouvelle partie lorsque la partie précédente est terminée.
     */
    public void cleanUp() {
        //on réinitialise root pour tout supprimer
        root.getChildren().clear();
        //on supprime les pions restant dans les listes
        getlPlayer().get(0).getlPiece().clear();
        getlPlayer().get(1).getlPiece().clear();
        // definir le decor
        initBoard();
        makeNode();
        // ajouter les acteurs
        makePiece();
    }

}
