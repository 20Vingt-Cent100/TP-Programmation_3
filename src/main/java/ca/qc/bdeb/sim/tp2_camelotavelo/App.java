package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    public static double WIDTH = 900, HEIGHT = 580;
    private static Stage appStageContext;
    private static Canvas appCanvas = new Canvas(WIDTH, HEIGHT);

    private static Camera camera;


    @Override
    public void start(Stage stage) throws IOException {
        appStageContext = stage;
        initComponents();
        initGameObjects();
        gameLoop();
    }

    private void initComponents(){
        var root = new VBox();
        var defaultScene = new Scene(root, WIDTH, HEIGHT);

        root.getChildren().add(appCanvas);

        appStageContext.setTitle("Camelot");
        appStageContext.setScene(defaultScene);
        appStageContext.show();

        //Scene Event Listeners
        appStageContext.getScene().setOnKeyPressed((k) -> Input.addKey(k.getCode()));
        appStageContext.getScene().setOnKeyReleased((k) -> Input.removeKey(k.getCode()));
    }

    private void initGameObjects(){
        Wall wall = new Wall(0,0, 192, 96);
        Camelot camelot = new Camelot(0, 0, 172, 144);
        camera = new Camera(camelot);
        Journal.setMass();

        UI ui = new UI(0, HEIGHT, WIDTH, 75);
    }

    private void gameLoop(){
        var graphicContext = appCanvas.getGraphicsContext2D();

        Time.loadPastTime();
        var loop = new AnimationTimer(){

            @Override
            public void handle(long now) {
                graphicContext.setFill(Color.BLACK);
                graphicContext.fillRect(0,0, WIDTH, HEIGHT);

                Time.deltaTime(now);

                ca.qc.bdeb.sim.tp2_secondVersion.GameObject.updateAll();
                ca.qc.bdeb.sim.tp2_secondVersion.GameObject.drawAll(graphicContext, camera);
            }
        };
        loop.start();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void changeScene(Scene gameScene){
        appStageContext.setScene(gameScene);

        //Scene Event Listeners
        gameScene.setOnKeyPressed((k) -> Input.addKey(k.getCode()));
        gameScene.setOnKeyReleased((k) -> Input.removeKey(k.getCode()));
    }
}