package fr.surakarta.game;

import fr.surakarta.grid.Node;
import fr.surakarta.piece.Piece;
import fr.surakarta.player.Player;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
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

    private Piece pionSelectionne;

    private List<Player> lPlayer = new ArrayList<Player>();

    public List<Player> getlPlayer() {
        return lPlayer;
    }

    public void setlPlayer(Player p) {
        this.lPlayer.add(p);
    }

    /**
     * @param primaryStage correspond à la fenêtre entière
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

    private void construirePlateauJeu(Stage primaryStage) {
        // definir la troupe des acteurs et des decors
        Group root = new Group();

        Player p1 = new Player("Michel");
        Player p2 = new Player("Jean");

        setlPlayer(p1); setlPlayer(p2);

        // definir la scene principale
        Scene scene = new Scene(root, 2 * widthStep + width, 2 * heightStep + height, Color.ANTIQUEWHITE);
        scene.setFill(Color.BLANCHEDALMOND);
        // definir le decor
        initBoard(root);
        makeNode(root);
        // ajouter les acteurs
        makePiece(root);

        primaryStage.setTitle("Surakarta");
        primaryStage.setScene(scene);
        // afficher le theatre
        primaryStage.show();
    }

    public void makeNode(Group root) {

        for (int i = 0; i <= 5; i++) {
            double x = (i + decalage) * widthStep;
            for (int j = 0; j <= 5; j++) {
                double y = (j + decalage) * heightStep;
                Node c = new Node(x, y);
                c.setOnMouseClicked(this);
                root.getChildren().add(c);
            }
        }

    }

    public void makePiece(Group root) {

        for (int i = 0; i <= 5; i++) {
            double x = (i + decalage) * widthStep;
            for (int j = 0; j <= 1; j++) {
                double y = (j + decalage) * heightStep;
                Piece p = new Piece(x, y, Color.GREY);
                p.setOnMouseClicked(this);
                getlPlayer().get(0).setlPiece(p);
                root.getChildren().add(p);

                p = new Piece(x, y + 4 * heightStep, Color.RED);
                p.setOnMouseClicked(this);
                getlPlayer().get(1).setlPiece(p);
                root.getChildren().add(p);
            }
        }


    }

    public void initBoard(Group root) {
        for (int i = 0; i < 6; i++) {
            Line line1 = new Line(decalage * widthStep, (i + decalage) * heightStep, (5 + decalage) * widthStep, (i + decalage) * heightStep);
            Line line2 = new Line((i + decalage) * widthStep, decalage * heightStep, (i + decalage) * widthStep, (5 + decalage) * heightStep);
            line1.setStrokeWidth(6);
            line1.setStroke(Color.BLACK);
            line2.setStrokeWidth(6);
            line2.setStroke(Color.BLACK);
            root.getChildren().add(line1);
            root.getChildren().add(line2);

            initPetitArc(root, 170, 170, 0);
            initPetitArc(root, 440, 170, -90);
            initPetitArc(root, 170, 440, 90);
            initPetitArc(root, 440, 440, 180);

            initGrandArc(root, 170, 170, 0);
            initGrandArc(root, 440, 170, -90);
            initGrandArc(root, 170, 440, 90);
            initGrandArc(root, 440, 440, 180);

        }
    }

    public void initPetitArc(Group root, int x, int y, double angle) {
        Arc arc = new Arc();
        arc.setCenterX(x);
        arc.setCenterY(y);
        arc.setRadiusX(55);
        arc.setRadiusY(55);
        arc.setStartAngle(angle);
        arc.setLength(270.0f);
        arc.setType(ArcType.OPEN);
        arc.setStroke(Color.RED);
        arc.setFill(null);
        root.getChildren().add(arc);

    }

    public void initGrandArc(Group root, int x, int y, double angle) {
        Arc arc = new Arc();
        arc.setCenterX(x);
        arc.setCenterY(y);
        arc.setRadiusX(110);
        arc.setRadiusY(110);
        arc.setStartAngle(angle);
        arc.setLength(270);
        arc.setType(ArcType.OPEN);
        arc.setStroke(Color.GREEN);
        arc.setFill(null);
        root.getChildren().add(arc);

    }


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void handle(MouseEvent mouseEvent) {


        //si le clic est sur un jeton, alterne son etat selectionne ou non
        if (mouseEvent.getSource().getClass() == Piece.class) {
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
                animPionVers(p);
                pionSelectionne.select();
            }
        }
    }

    private void animPionVers(Node p) {
        Timeline timeline = new Timeline();
        double xdest = p.getCenterX();
        double ydest = p.getCenterY();
        KeyFrame bougeVoiture = new KeyFrame(new Duration(500),
                new KeyValue(pionSelectionne.centerXProperty(), xdest),
                new KeyValue(pionSelectionne.centerYProperty(), ydest));
        timeline.getKeyFrames().add(bougeVoiture);
        timeline.play();

    }







}
