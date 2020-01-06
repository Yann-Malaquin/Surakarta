package fr.surakarta.menu;


import fr.surakarta.game.Surakarta;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.InputStream;


/**
 * <b>MenuController est la class représentant le menu principal</b>
 *
 * @author Billy MORTREUX, Yann MALAQUIN
 */
public class MenuController extends Application {

    /**
     * Méthode permettant d'initiliser le contenu du menu, les différentes actions exécutées par les différents boutons.
     *
     * @param primaryStage
     *      fenetre principal
     */
    public void initMenu(Stage primaryStage) {
        Group root = new Group();

        int width = 500;
        int height = 500;
        int heightStep = height / 9;
        int widthStep = width / 9;

        //définition des boutons 1 VS IA et Quitter ainsi que le label situé en haut
        Label title = new Label();
        title.setText("Surakarta");
        Font f;
        Button ia = new Button("1 vs IA");
        Button quitter = new Button("Quitter");
        AnchorPane ap = new AnchorPane();
        Surakarta sk = new Surakarta();
        root.getChildren().add(ap);

        //on ouvre les outils nécessaires, ici le type de police ainsi que le fond
        InputStream font = this.getClass().getResourceAsStream("/resources/AlphaClouds.ttf");
        InputStream background = this.getClass().getResourceAsStream("/resources/fond.jpg");

        //on initialise le fond de l'anchorpane
        ap = (AnchorPane) root.getChildren().get(0);
        ap.setPrefSize(2 * widthStep + width, 2 * heightStep + height);
        Image im = new Image(background, 2 * widthStep + width, 2 * heightStep + height, false, false);
        BackgroundImage myBI = new BackgroundImage(im,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        ap.setBackground(new Background(myBI));

        // on affecte les différentes police et taille au objet qui le requiert
        f = Font.loadFont(font, 40);
        title.setFont(f);
        title.setTextFill(Color.WHITE);
        title.setLayoutX(200);
        title.setLayoutY(20);
        font = this.getClass().getResourceAsStream("/resources/Roman SD.ttf");
        f = Font.loadFont(font, 14);
        ia.setFont(f);

        ia.setLayoutX(270);
        ia.setLayoutY(200);

        font = this.getClass().getResourceAsStream("/resources/Roman SD.ttf");
        f = Font.loadFont(font, 14);
        quitter.setFont(f);
        quitter.setLayoutX(500);
        quitter.setLayoutY(550);

        root.getChildren().add(title);
        root.getChildren().add(ia);
        root.getChildren().add(quitter);

        Scene scene = new Scene(root, 2 * widthStep + width, 2 * heightStep + height);
        //css pour habiller les 2 boutons
        scene.getStylesheets().add("/resources/stylesheet.css");

        /*
        changement du cursor lorsque l'on passe sur les boutons
         */
        ia.setOnMouseEntered(e -> {
            scene.setCursor(Cursor.HAND);
        });
        ia.setOnMouseExited(e -> {
            scene.setCursor(Cursor.DEFAULT);
        });

        quitter.setOnMouseEntered(e -> {
            scene.setCursor(Cursor.HAND);
        });
        quitter.setOnMouseExited(e -> {
            scene.setCursor(Cursor.DEFAULT);
        });


        //lancement du jeu
        ia.setOnMouseClicked(e -> {
            sk.start((Stage) ia.getScene().getWindow());

        });

        //quitter le jeu
        quitter.setOnMouseClicked(e -> {
            primaryStage.close();
        });

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Surakarta");
        primaryStage.show();
    }

    @Override
    public void start(Stage stage) throws Exception {
        initMenu(stage);
    }
}