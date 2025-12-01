package ca.qc.bdeb.sim.tp2_camelotavelo;

import com.sun.tools.javac.Main;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App extends Application {
    public static double WIDTH = 900, HEIGHT = 580;
    private static Stage appStageContext;
    private static final Canvas APP_CANVAS = new Canvas(WIDTH, HEIGHT);
    private static Color backgroundColor = Color.BLACK;

    private static Camera camera;

    public static GameState activeState = GameState.NO_ACTIVE_STATE;


    @Override
    public void start(Stage stage) throws IOException {
        appStageContext = stage;

        initComponents();
        //initGameObjects();
        gameLoop();
    }

    private void initComponents(){
        var root = new VBox();
        var defaultScene = new Scene(root, WIDTH, HEIGHT);

        root.getChildren().add(APP_CANVAS);

        appStageContext.setTitle("Camelot");
        appStageContext.setScene(defaultScene);
        appStageContext.show();

        //Scene Event Listeners
        appStageContext.getScene().setOnKeyPressed((k) -> {
            Input.addKey(k.getCode());

            if (k.getCode() == KeyCode.ESCAPE)
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

                switch (activeState){
                    case LEVEL_SCREEN -> {
                        Level.drawLevelScreen(graphicContext);
                    }

                    case GAME_SCREEN -> {

                        graphicContext.setFill(backgroundColor);
                        graphicContext.fillRect(0,0, WIDTH, HEIGHT);

                        Time.deltaTime(now);

                        GameObject.updateAll();
                        GameObject.checkCollision();
                        GameObject.drawAll(graphicContext, camera);

                        Input.endOfFrame();
                    }

                    case END_SCREEN -> {
                        Level.drawEndScreen(graphicContext);
                    }

                    default -> Level.nextLevel();
                }



            }
        };
        loop.start();
    }

    public static void main(String[] args) {
        launch();
    }
    public static void setCanvasBackground(Color color){
        backgroundColor = color;
    }

    public static void setCamera(Camera cam){
        camera = cam;
    }
}