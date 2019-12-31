package fr.surakarta.game;

import fr.surakarta.grid.Node;
import fr.surakarta.piece.Piece;
import fr.surakarta.piece.PieceType;
import fr.surakarta.player.Player;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;
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

    public List<Player> getlPlayer() {
        return lPlayer;
    }

    public void setlPlayer(Player p) {
        this.lPlayer.add(p);
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
                Node c = new Node(x, y);
                // on les rend clickable
                c.setOnMouseClicked(this);
                // on ajoute au groupe root qui permet de regrouper la scene
                root.getChildren().add(c);
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

            initPetitArc(170, 170, 0);
            initPetitArc(440, 170, -90);
            initPetitArc(170, 440, 90);
            initPetitArc(440, 440, 180);

            initGrandArc(170, 170, 0);
            initGrandArc(440, 170, -90);
            initGrandArc(170, 440, 90);
            initGrandArc(440, 440, 180);

        }
    }


    /**
     * Création du petit arc de cercle
     *
     * @param x     Position en abscisse de l'arc de cercle
     * @param y     Position en ordonnée de l'arc de cercle
     * @param angle L'angle de l'arc de cercle, permettant ainsi sa rotation et une bonne disposition
     */

    public void initPetitArc(int x, int y, double angle) {

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

    public void initGrandArc(int x, int y, double angle) {
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
                move(piece, p);
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
                if (checkKill(p, endX, endY)) {
                    kill(piece, p);
                    pionSelectionne.select();
                    //pour ne pas permettre le click sur le pion adverse
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

    private void move(Piece piece, Node p) {
        Timeline timeline = new Timeline();
        double xdest = p.getCenterX();
        double ydest = p.getCenterY();

        KeyFrame move = new KeyFrame(new Duration(500),
                new KeyValue(pionSelectionne.centerXProperty(), xdest),
                new KeyValue(pionSelectionne.centerYProperty(), ydest));
        timeline.getKeyFrames().add(move);

        timeline.play();
    }

    /**
     * Permet de faire un déplacement tout en prenant le pion adverse
     *
     * @param piece le pion qui se déplacera
     * @param p     Le pion qui se fera prendre
     */
    private void kill(Piece piece, Piece p) {
        Timeline timeline = new Timeline();
        double xdest = p.getCenterX();
        double ydest = p.getCenterY();


        KeyFrame kill = new KeyFrame(new Duration(500),

                // événement qui prendra effet lors de l'écoulement total des 500ms
                new EventHandler<ActionEvent>() {
                    /**
                     * A la fin des 500ms, suivant le joueur le pion adverse sera supprimé du plateau et également de sa liste de pion
                     * @param event
                     *          Le type d'événement reccueilli
                     */
                    @Override
                    public void handle(ActionEvent event) {
                        if (piece.getType() == PieceType.P1) {
                            root.getChildren().remove(p);
                            System.out.println(getlPlayer().get(0).getlPiece());
                            getlPlayer().get(0).getlPiece().remove(p);
                            System.out.println(getlPlayer().get(0).getlPiece());
                        } else {
                            root.getChildren().remove(p);
                            System.out.println(getlPlayer().get(1).getlPiece());
                            getlPlayer().get(1).getlPiece().remove(p);
                            System.out.println(getlPlayer().get(1).getlPiece());
                        }
                    }
                },

                new KeyValue(pionSelectionne.centerXProperty(), xdest),
                new KeyValue(pionSelectionne.centerYProperty(), ydest));

        timeline.getKeyFrames().add(kill);
        timeline.play();

    }

    /**
     * Permet de vérifier si une prise est possible ou non
     *
     * @param p Le pion visé
     * @param x l'abscisse du pion visé
     * @param y L'ordonnée du pion visé
     * @return true si l'on peut prendre un pion. Il est de type boolean
     */
    public boolean checkKill(Piece p, int x, int y) {

        //si l'on est sur un coin on return false
        if (((x == 0) && (y == 0 || y == 5)) || ((x == 5) && (y == 0 || y == 5))) {
            return false;
        }

        //s'il s'agit d'un pion du joueur 1
        if (p.getType() == PieceType.P1) {
            /* on parcourt sa liste de pion pour voir si un pion est collé à lui.
            On vérifie d'abord horizontalement. S'il en existe un alors on renvoie false, le pion n'est pas prenable.
            On vérifie ensuite vericalement. S'il en existe un alors on renvoie false, le pion n'est pas prenable.
             */

            for (Piece _p : getlPlayer().get(0).getlPiece()) {

                int endX = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                int endY = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (x == endX && y + 1 == endY) {
                    return false;
                } else if (x + 1 == endX && y == endY) {
                    return false;
                }
            }
        }

        if (p.getType() == PieceType.P2) {
            for (Piece _p : getlPlayer().get(1).getlPiece()) {

                int endX = (int) Math.round(_p.getCenterX() / widthStep - decalage);
                int endY = (int) Math.round(_p.getCenterY() / heightStep - decalage);

                if (x == endX && y + 1 == endY) {
                    return false;
                } else if (x + 1 == endX && y == endY) {
                    return false;
                }
            }
        }

        //Si après vérification on arrive ici alors le pion est prenable.
        return true;
    }


}
