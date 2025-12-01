package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    public static double WIDTH = 900, HEIGHT = 580;
    private static Stage appStageContext;
    private static final Canvas APP_CANVAS = new Canvas(WIDTH, HEIGHT);

    private Jeu jeu;

    @Override
    public void start(Stage stage) throws IOException {
        appStageContext = stage;
        initComponents();

        jeu = new Jeu();

        gameLoop();
    }

    private void initComponents(){
        var root = new VBox();
        var defaultScene = new Scene(root, WIDTH, HEIGHT);

        root.getChildren().add(APP_CANVAS);

        appStageContext.setResizable(false);
        appStageContext.setTitle("Camelot");
        appStageContext.getIcons().add(new Image(getClass().getResourceAsStream("/assets/journal.png")));
        appStageContext.setScene(defaultScene);
        appStageContext.show();

        appStageContext.getScene().setOnKeyPressed((k) ->
        {
            Input.addKey(k.getCode());
            if(k.getCode() == KeyCode.ESCAPE)
                appStageContext.close();
        });
        appStageContext.getScene().setOnKeyReleased((k) -> Input.removeKey(k.getCode()));
    }

    private void gameLoop(){
        var graphicContext = APP_CANVAS.getGraphicsContext2D();

        Time.loadPastTime();
        var loop = new AnimationTimer(){

            @Override
            public void handle(long now) {
                Color fond = Color.BLACK;
                graphicContext.setFill(fond);
                graphicContext.fillRect(0,0, WIDTH, HEIGHT);

                Time.deltaTime(now);
                double dt = Time.deltaTimeSec;

                jeu.update(dt);
                jeu.draw(graphicContext);

                Input.endOfFrame();
            }
        };
        loop.start();
    }

    public static void main(String[] args) {
        launch();
    }
}