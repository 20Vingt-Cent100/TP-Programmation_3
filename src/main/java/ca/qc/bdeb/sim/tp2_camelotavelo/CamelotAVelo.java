package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CamelotAVelo extends Application {

    private double cameraX = 0;
    private final double solY = 500;

    private final List<Maison> maisons = new ArrayList<>();
    private Image background;

    @Override
    public void start(Stage stage) {
        double largeurFenetre = 1200;
        double hauteurFenetre = 600;

        Canvas canvas = new Canvas(largeurFenetre, hauteurFenetre);
        GraphicsContext gc = canvas.getGraphicsContext2D();


        background = new Image(getClass().getResourceAsStream("/assets/brique.png"));


        creerMaisonsDeTest();

        Pane root = new Pane(canvas);
        Scene scene = new Scene(root, largeurFenetre, hauteurFenetre);

        root.setFocusTraversable(true);
        root.requestFocus();


        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case RIGHT -> cameraX += 30;
                case LEFT  -> cameraX -= 30;
            }
        });


        new AnimationTimer() {
            @Override
            public void handle(long now) {
                dessiner(gc, largeurFenetre, hauteurFenetre);
            }
        }.start();

        stage.setScene(scene);
        stage.setTitle("Test Maison + Caméra + Background image");
        stage.show();
    }

    private void dessiner(GraphicsContext gc, double largeur, double hauteur) {

        for (double x = 0; x < largeur; x += background.getWidth()) {
            for (double y = 0; y < hauteur; y += background.getHeight()) {
                gc.drawImage(background, x, y);
            }
        }


        for (Maison m : maisons) {
            m.draw(gc, cameraX, solY);
        }


        gc.setStroke(javafx.scene.paint.Color.YELLOW);
        for (Maison m : maisons) {
            m.drawDebuggage(gc, cameraX);
        }
    }

    private void creerMaisonsDeTest() {
        double[] bases = {200, 900, 1700};

        for (int i = 0; i < bases.length; i++) {
            double baseMaison = bases[i];
            boolean abonnee = (i % 2 == 0);

            BoiteAuxLettres boite = new BoiteAuxLettres(
                    baseMaison + 180,
                    solY - BoiteAuxLettres.hauteur,
                    abonnee
            );

            List<Fenetre> fenetres = new ArrayList<>();
            fenetres.add(new Fenetre(baseMaison + 300, 50, abonnee));
            fenetres.add(new Fenetre(baseMaison + 600, 50, abonnee));

            maisons.add(new Maison(
                    baseMaison,
                    100 + i,
                    abonnee,
                    boite,
                    fenetres
            ));
        }
    }

    public static void main(String[] args) {
        launch();
    }
}