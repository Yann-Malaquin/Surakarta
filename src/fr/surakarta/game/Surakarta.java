package fr.surakarta.game;

import fr.surakarta.grid.Node;
import fr.surakarta.piece.Piece;
import fr.surakarta.piece.PieceType;
import fr.surakarta.player.Player;
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
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

    private Group root = new Group();

    private int Tour=0;

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
                    Surakarta.logger.log(Level.INFO, "Déplacement pion :startX, startY, endX, endY= " + startX + "," + startY + "," + endX + "," + endY);
                    System.err.println("startX, startY, endX, endY=" + startX + "," + startY + "," + endX + "," + endY);
                    Piece piece = pionSelectionne;
                    if(move(piece, p, startX, startY, endX, endY)){
                    pionSelectionne.select();
                    //pour ne pas permettre le click sur le pion adverse
                    pionSelectionne = null;
                    Tour = 1;
                    /*PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished(event -> IA());
                    delay.play();*/
                }}
            } else if (mouseEvent.getSource().getClass() == Piece.class) {
                Piece p = (Piece) mouseEvent.getSource();

                if (pionSelectionne != null && pionSelectionne.isSelected()) {
                    int startX = (int) Math.round(pionSelectionne.getCenterX() / widthStep - decalage);
                    int startY = (int) Math.round(pionSelectionne.getCenterY() / heightStep - decalage);
                    int endX = (int) Math.round(p.getCenterX() / widthStep - decalage);
                    int endY = (int) Math.round(p.getCenterY() / heightStep - decalage);
                    Surakarta.logger.log(Level.INFO, "prise pion :startX, startY, endX, endY= " + startX + "," + startY + "," + endX + "," + endY);
                    System.err.println("startX, startY, endX, endY=" + startX + "," + startY + "," + endX + "," + endY);
                    Piece piece = pionSelectionne;

                    //on vérifie si l'on peut prendre le pion
                    if (checkLaunch(piece, p, startX, startY, endX, endY) && p.getType() != piece.getType()) {
                        allKill(piece, p, startX, startY, endX, endY);
                        Tour = 1;
                        pionSelectionne.select();
                        pionSelectionne = null;
                        /*PauseTransition delay = new PauseTransition(Duration.seconds(1));
                        delay.setOnFinished(event -> IA());
                        delay.play();*/
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

    }

    public void IA() {
        if (Tour == 1) {
            if (pionSelectionne == null) {
                int test = 0;
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
            if (pionSelectionne != null && pionSelectionne.isSelected()) {
                /*kill*/
                int y = 0;
                for (Piece p1 : lPlayer.get(0).getlPiece()) {
                    System.out.println("cpt");
                    int startX = (int) Math.round(p1.getCenterX() / widthStep - decalage);
                    int startY = (int) Math.round(p1.getCenterY() / heightStep - decalage);
                    if ((startX == 0 && startY == 0) || (startX == 5 && startY == 5) || (startX == 0 && startY == 5) || (startX == 5 && startY == 0)) {

                    }
                    else {
                        for (Piece p2 : lPlayer.get(1).getlPiece()) {
                            int endX = (int) Math.round(p2.getCenterX() / widthStep - decalage);
                            int endY = (int) Math.round(p2.getCenterY() / heightStep - decalage);
                            System.out.println(p1.toString());
                            System.out.println(p2.toString());
                            System.out.println(startX + " " + startY);
                            System.out.println(endX + " " + endY);
                            if (checkLaunch(p1, p2, startX, startY, endX, endY)) {
                                allKill(p1, p2, startX, startY, endX, endY);
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
                if (y == 0) {
                    /*MOVE*/
                    for (Node n : lNode) {
                        if (pionSelectionne != null && pionSelectionne.isSelected()) {
                            int startX = (int) Math.round(pionSelectionne.getCenterX() / widthStep - decalage);
                            int startY = (int) Math.round(pionSelectionne.getCenterY() / heightStep - decalage);
                            int endX = (int) Math.round(n.getCenterX() / widthStep - decalage);
                            int endY = (int) Math.round(n.getCenterY() / heightStep - decalage);
                            Surakarta.logger.log(Level.INFO, "Déplacement pion :startX, startY, endX, endY= " + startX + "," + startY + "," + endX + "," + endY);
                            System.err.println("startX, startY, endX, endY=" + startX + "," + startY + "," + endX + "," + endY);
                            Piece piece = pionSelectionne;
                            if (move(piece, n, startX, startY, endX, endY)) {
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
     * @param piece Le pion qui bougera
     * @param p     Le noeud vers lequel le pion se déplacera
     */

    private Boolean move(Piece piece, Node p, int startX, int startY, int endX, int endY) {
        for (int i = 0; i < 36; i++) {
            if (piece.getCenterX() == lNode.get(i).getCenterX() && piece.getCenterY() == lNode.get(i).getCenterY()) {
                if (startX==endX && startY==endY){
                    return false;
                }
                else if (p.getverification() == false && ((endX - startX == 0 && endY - startY == 0) || (endX - startX == 0 && endY - startY == 1) || (endX - startX == 1 && endY - startY == 0)
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
                    if(Tour==0){
                   timeline.setOnFinished(e->{
                        IA();
                    });}
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
     * @param piece le pion qui se déplacera
     * @param p     Le pion qui se fera prendre
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

        if ((endX == 1 && startY == 1) || (endX == 2 && startY == 2) || (endY == 1 && startX == 1) || (endY == 2 && startX == 2 && startY != 3)) {
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


        } else if ((endX == 4 && startY == 1) || (endY == 4 && startX == 1) ) {

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

        } else if (endX == 4 && startY == 4) {
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
                getlPlayer().get(1).setScore(10);
            } else {
                root.getChildren().remove(p);
                getlPlayer().get(0).getlPiece().remove(p);
                getlPlayer().get(0).setScore(10);
            }
            if(Tour==1){
                IA();
            }
        });
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
    public boolean checkLaunch(Piece piece, Piece p, int startX, int startY, int endX, int endY) {
        int cpt, x, y;

        if (piece.getType() == PieceType.P1) {
            cpt = 0;

            if (endX == 1 && startY == 1) {
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == startY && x < startX && _p != piece) {
                        cpt++;
                    }

                    if (x == 1 && y < endY && _p != piece) {
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x < endX && y == startY && _p != p) {
                        cpt++;
                    }

                    if (x == endX && y < endY && _p != p) {
                        cpt++;
                    }
                }
                if (cpt == 0) {
                    return true;
                }
            } else if (endX == 2 && startY == 2) {
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == startY && x < startX && _p != piece) {
                        cpt++;
                    }

                    if (x == 2 && y < endY && _p != piece) {
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x < endX && y == startY && _p != p) {
                        cpt++;
                    }

                    if (x == endX && y < endY && _p != p) {
                        cpt++;
                    }
                }

                if (cpt == 0) {
                    return true;
                }
            } else if (endX == 4 && startY == 1) {
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == startY && x > startX && _p != piece) {
                        cpt++;
                    }

                    if (x == 4 && y < endY && _p != piece) {
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x > endX && y == startY && _p != p) {
                        cpt++;
                    }

                    if (x == endX && y < endY && _p != p) {
                        cpt++;
                    }
                }

                if (cpt == 0) {
                    return true;
                }
            } else if (endX == 3 && startY == 2) {
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == startY && x > startX && _p != piece) {
                        cpt++;
                    }

                    if (x == 3 && y < endY && _p != piece) {
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x > endX && y == startY && _p != p) {
                        cpt++;
                    }

                    if (x == endX && y < endY && _p != p) {
                        cpt++;
                    }
                }

                if (cpt == 0) {
                    return true;
                }
            } else if (endX == 1 && startY == 4) {
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == startY && x < startX && _p != piece) {
                        cpt++;
                    }

                    if (x == 1 && y > endY && _p != piece) {
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x < endX && y == startY && _p != p) {
                        cpt++;
                    }

                    if (x == endX && y > endY && _p != p) {
                        cpt++;
                    }
                }

                if (cpt == 0) {
                    return true;
                }
            } else if (endX == 2 && startY == 3) {
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == startY && x < startX && _p != piece) {
                        cpt++;
                    }

                    if (x == 2 && y > endY && _p != piece) {
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x < endX && y == startY && _p != p) {
                        cpt++;
                    }

                    if (x == endX && y > endY && _p != p) {
                        cpt++;
                    }
                }

                if (cpt == 0) {
                    return true;
                }
            } else if (endX == 4 && startY == 4) {
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == startY && x > startX && _p != piece) {
                        cpt++;
                    }

                    if (x == 4 && y > endY && _p != piece) {
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x > endX && y == startY && _p != p) {
                        cpt++;
                    }

                    if (x == endX && y > endY && _p != p) {
                        cpt++;
                    }
                }

                if (cpt == 0) {
                    return true;
                }
            } else if (endX == 3 && startY == 3) {
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == startY && x > startX && _p != piece) {
                        cpt++;
                    }

                    if (x == 3 && y > endY && _p != piece) {
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x > endX && y == startY && _p != p) {
                        cpt++;
                    }

                    if (x == endX && y > endY && _p != p) {
                        cpt++;
                    }
                }

                if (cpt == 0) {
                    return true;
                }
            } else if (endY == 1 && startX == 1) {
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == endY && x < endX && _p != piece) {
                        cpt++;
                    }

                    if (x == 1 && y < startY && _p != piece) {
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x < endX && y == endY && _p != p) {
                        System.out.println("rentre3");
                        cpt++;
                    }

                    if (x == endX && y < endY && _p != p) {
                        System.out.println("rentre4");
                        cpt++;
                    }
                }

                if (cpt == 0) {
                    return true;
                }
            } else if (endY == 2 && startX == 2) {
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == endY && x < endX && _p != piece) {
                        cpt++;
                    }

                    if (x == 2 && y < startY && _p != piece) {
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x < endX && y == endY && _p != p) {
                        cpt++;
                    }

                    if (x == endX && y < endY && _p != p) {
                        cpt++;
                    }
                }

                if (cpt == 0) {
                    return true;
                }
            } else if (endY == 1 && startX == 4) {
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == endY && x > endX && _p != piece) {
                        cpt++;
                    }

                    if (x == 4 && y < startY && _p != piece) {
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x > endX && y == endY && _p != p) {
                        cpt++;
                    }

                    if (x == startX && y < startY && _p != p) {
                        cpt++;
                    }
                }

                if (cpt == 0) {
                    return true;
                }
            } else if (endY == 2 && startX == 3) {
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == endY && x > endX && _p != piece) {
                        cpt++;
                    }

                    if (x == 3 && y < startY && _p != piece) {
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x > endX && y == endY && _p != p) {
                        cpt++;
                    }

                    if (x == startX && y < startY && _p != p) {
                        cpt++;
                    }
                }

                if (cpt == 0) {
                    return true;
                }
            } else if (endY == 4 && startX == 1) {
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == endY && x < endX && _p != piece) {
                        cpt++;
                    }

                    if (x == 1 && y > startY && _p != piece) {
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x < endX && y == endY && _p != p) {
                        cpt++;
                    }

                    if (x == startX && y > endY && _p != p) {

                        cpt++;
                    }
                }

                if (cpt == 0) {
                    return true;
                }
            } else if (endY == 3 && startX == 2) {
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == endY && x < endX && _p != piece) {
                        cpt++;
                    }

                    if (x == 2 && y > startY && _p != piece) {
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x < endX && y == endY && _p != p) {
                        cpt++;
                    }

                    if (x == startX && y > endY && _p != p) {

                        cpt++;
                    }
                }

                if (cpt == 0) {
                    return true;
                }
            } else if (endY == 4 && startX == 4) {
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == endY && x > endX && _p != piece) {
                        cpt++;
                    }

                    if (x == 4 && y > startY && _p != piece) {
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x > endX && y == endY && _p != p) {
                        cpt++;
                    }

                    if (x == startX && y > endY && _p != p) {
                        cpt++;
                    }
                }

                if (cpt == 0) {
                    return true;
                }
            } else if (endY == 3 && startX == 3) {
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == endY && x > endX && _p != piece) {
                        cpt++;
                    }

                    if (x == 3 && y > startY && _p != piece) {
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x > endX && y == endY && _p != p) {
                        cpt++;
                    }

                    if (x == startX && y > endY && _p != p) {
                        cpt++;
                    }
                }

                if (cpt == 0) {
                    return true;
                }
            }

        } else if (piece.getType() == PieceType.P2) {
            cpt = 0;

            System.out.println("P2");
            if (endX == 1 && startY == 1) {
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == startY && x < startX && _p != piece) {
                        cpt++;
                    }

                    if (x == 1 && y < endY && _p != piece) {
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x < endX && y == startY && _p != p) {
                        cpt++;
                    }

                    if (x == endX && y < endY && _p != p) {
                        cpt++;
                    }
                }
                if (cpt == 0) {
                    return true;
                }
            } else if (endX == 2 && startY == 2) {
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == startY && x < startX && _p != piece) {
                        cpt++;
                    }

                    if (x == 2 && y < endY && _p != piece) {
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x < endX && y == startY && _p != p) {
                        cpt++;
                    }

                    if (x == endX && y < endY && _p != p) {
                        cpt++;
                    }
                }

                if (cpt == 0) {
                    return true;
                }
            } else if (endX == 4 && startY == 1) {
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == startY && x > startX && _p != piece) {
                        cpt++;
                    }

                    if (x == 4 && y < endY && _p != piece) {
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x > endX && y == startY && _p != p) {
                        cpt++;
                    }

                    if (x == endX && y < endY && _p != p) {
                        cpt++;
                    }
                }

                if (cpt == 0) {
                    return true;
                }
            } else if (endX == 3 && startY == 2) {
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == startY && x > startX && _p != piece) {
                        cpt++;
                    }

                    if (x == 3 && y < endY && _p != piece) {
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x > endX && y == startY && _p != p) {
                        cpt++;
                    }

                    if (x == endX && y < endY && _p != p) {
                        cpt++;
                    }
                }

                if (cpt == 0) {
                    return true;
                }
            } else if (endX == 1 && startY == 4) {
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == startY && x < startX && _p != piece) {
                        cpt++;
                    }

                    if (x == 1 && y > endY && _p != piece) {
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x < endX && y == startY && _p != p) {
                        cpt++;
                    }

                    if (x == endX && y > endY && _p != p) {
                        cpt++;
                    }
                }

                if (cpt == 0) {
                    return true;
                }
            } else if (endX == 2 && startY == 3) {
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == startY && x < startX && _p != piece) {
                        cpt++;
                    }

                    if (x == 2 && y > endY && _p != piece) {
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x < endX && y == startY && _p != p) {
                        cpt++;
                    }

                    if (x == endX && y > endY && _p != p) {
                        cpt++;
                    }
                }

                if (cpt == 0) {
                    return true;
                }
            } else if (endX == 4 && startY == 4) {
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == startY && x > startX && _p != piece) {
                        cpt++;
                    }

                    if (x == 4 && y > endY && _p != piece) {
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x > endX && y == startY && _p != p) {
                        cpt++;
                    }

                    if (x == endX && y > endY && _p != p) {
                        cpt++;
                    }
                }

                if (cpt == 0) {
                    return true;
                }
            } else if (endX == 3 && startY == 3) {
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == startY && x > startX && _p != piece) {
                        cpt++;
                    }

                    if (x == 3 && y > endY && _p != piece) {
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x > endX && y == startY && _p != p) {
                        cpt++;
                    }

                    if (x == endX && y > endY && _p != p) {
                        cpt++;
                    }
                }

                if (cpt == 0) {
                    return true;
                }
            } else if (endY == 1 && startX == 1) {
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == endY && x < endX && _p != piece) {
                        System.out.println("endY rentre1");
                        cpt++;
                    }

                    if (x == 1 && y < startY && _p != piece) {
                        System.out.println("endY rentre1");
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x < endX && y == endY && _p != p) {
                        System.out.println("endY rentre1");
                        cpt++;
                    }

                    if (x == endX && x < startX && _p != p) {
                        System.out.println("endY rentre1");
                        cpt++;
                    }
                }

                if (cpt == 0) {
                    return true;
                }
            } else if (endY == 2 && startX == 2) {
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == endY && x < endX && _p != piece) {
                        cpt++;
                    }

                    if (x == 2 && y < startY && _p != piece) {
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x < endX && y == endY && _p != p) {
                        cpt++;
                    }

                    if (x == endX && y < endY && _p != p) {
                        cpt++;
                    }
                }

                if (cpt == 0) {
                    return true;
                }
            } else if (endY == 1 && startX == 4) {
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == endY && x > endX && _p != piece) {
                        cpt++;
                    }

                    if (x == 4 && y < startY && _p != piece) {
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x > endX && y == endY && _p != p) {
                        cpt++;
                    }

                    if (x == startX && y < startY && _p != p) {
                        cpt++;
                    }
                }

                if (cpt == 0) {
                    return true;
                }
            } else if (endY == 2 && startX == 3) {
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == endY && x > endX && _p != piece) {
                        cpt++;
                    }

                    if (x == 3 && y < startY && _p != piece) {
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x > endX && y == endY && _p != p) {
                        cpt++;
                    }

                    if (x == startX && y < startY && _p != p) {
                        cpt++;
                    }
                }

                if (cpt == 0) {
                    return true;
                }
            } else if (endY == 4 && startX == 1) {
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == endY && x < endX && _p != piece) {
                        cpt++;
                    }

                    if (x == 1 && y > startY && _p != piece) {
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x < endX && y == endY && _p != p) {
                        cpt++;
                    }

                    if (x == startX && y > endY && _p != p) {

                        cpt++;
                    }
                }

                if (cpt == 0) {
                    return true;
                }
            } else if (endY == 3 && startX == 2) {
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == endY && x < endX && _p != piece) {
                        cpt++;
                    }

                    if (x == 2 && y > startY && _p != piece) {
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x < endX && y == endY && _p != p) {
                        cpt++;
                    }

                    if (x == startX && y > endY && _p != p) {

                        cpt++;
                    }
                }

                if (cpt == 0) {
                    return true;
                }
            } else if (endY == 4 && startX == 4) {
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == endY && x > endX && _p != piece) {
                        cpt++;
                    }

                    if (x == 4 && y > startY && _p != piece) {
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x > endX && y == endY && _p != p) {
                        cpt++;
                    }

                    if (x == startX && y > endY && _p != p) {
                        cpt++;
                    }
                }

                if (cpt == 0) {
                    return true;
                }
            } else if (endY == 3 && startX == 3) {
                for (Piece _p : getlPlayer().get(1).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (y == endY && x > endX && _p != piece) {
                        System.out.println("rentre1");
                        cpt++;
                    }

                    if (x == 3 && y > startY && _p != piece) {
                        System.out.println("rentre2");
                        cpt++;
                    }
                }
                for (Piece _p : getlPlayer().get(0).getlPiece()) {
                    x = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                    y = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                    if (x > endX && y == endY && _p != p) {
                        System.out.println("rentre3");
                        cpt++;
                    }

                    if (x == startX && y > endY && _p != p) {
                        System.out.println("rentre4");
                        cpt++;
                    }
                }

                if (cpt == 0) {
                    return true;
                }
            }
        }

        return false;
    }

    public void allKill(Piece piece, Piece p, int startX, int startY, int endX, int endY) {

        //arcs de cercle haut gauche
        if (endX == 1 && startY == 1) {
            System.out.println("1");
            kill(piece, p, startX, startY, endX, endY, 3, 4, true, 55);
        } else if (endX == 2 && startY == 2) {
            System.out.println("2");
            kill(piece, p, startX, startY, endX, endY, 3, 5, true, 110);
        } else if (endY == 1 && startX == 1) {
            System.out.println("3");
            kill(piece, p, startX, startY, endX, endY, 4, 3, false, 55);
        } else if (endY == 2 && startX == 2 && startY != 3) {
            System.out.println("4");
            kill(piece, p, startX, startY, endX, endY, 5, 3, false, 110);

            //arc de cercle haut droite
        } else if (endX == 4 && startY == 1) {
            System.out.println("5");
            kill(piece, p, startX, startY, endX, endY, 8, 4, false, 55);
        } else if (endX == 3 && startY == 2) {
            System.out.println("6");
            kill(piece, p, startX, startY, endX, endY, 8, 5, false, 110);
        } else if (endY == 1 && startX == 4) {
            System.out.println("7");
            kill(piece, p, startX, startY, endX, endY, 7, 3, true, 55);
        } else if (endY == 2 && startX == 3 && startY != 3) {
            System.out.println("8");
            kill(piece, p, startX, startY, endX, endY, 6, 3, true, 110);

            //arc de cercle bas gauche
        } else if (endX == 1 && startY == 4) {
            System.out.println("9");
            kill(piece, p, startX, startY, endX, endY, 3, 7, false, 55);
        } else if (endX == 2 && startY == 3) {
            System.out.println("10");
            kill(piece, p, startX, startY, endX, endY, 3, 6, false, 110);
        } else if (endY == 4 && startX == 1) {
            System.out.println("11");
            kill(piece, p, startX, startY, endX, endY, 4, 8, true, 55);
        } else if (endY == 3 && startX == 2) {
            System.out.println("12");
            kill(piece, p, startX, startY, endX, endY, 5, 8, true, 110);

            //arc de cercle bas droite
        } else if (endX == 4 && startY == 4) {
            System.out.println("13");
            kill(piece, p, startX, startY, endX, endY, 8, 7, true, 55);
        } else if (endX == 3 && startY == 3) {
            System.out.println("14");
            kill(piece, p, startX, startY, endX, endY, 8, 6, true, 110);
        } else if (endY == 4 && startX == 4) {
            System.out.println("15");
            kill(piece, p, startX, startY, endX, endY, 7, 8, false, 55);
        } else if (endY == 3 && startX == 3) {
            System.out.println("16");
            kill(piece, p, startX, startY, endX, endY, 6, 8, false, 110);
        }
    }
}
