package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.animation.PauseTransition;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class Level {
    public static int lvlNum = 0;
    private static boolean drawingLevelScreen = false;
    private static boolean drawingEndScreen = false;

    public static void nextLevel(){
        GameObject.clearAllObjects();
        lvlNum++;
        App.activeState = GameState.LEVEL_SCREEN;
    }

    public static void drawLevelScreen(GraphicsContext graphicsContext){
        if(!drawingLevelScreen) {
            drawingLevelScreen = true;

            Thread.startVirtualThread(Level::initObj);

            graphicsContext.setFill(Color.BLACK);
            graphicsContext.fillRect(0, 0, App.WIDTH, App.HEIGHT);

            graphicsContext.setFill(Color.GREEN);
            graphicsContext.setTextAlign(TextAlignment.CENTER);
            graphicsContext.setFont(Font.font("Arial", 60));
            graphicsContext.fillText("Niveau " + lvlNum, App.WIDTH/2, App.HEIGHT / 2);

            PauseTransition wait = new PauseTransition(Duration.seconds(3));
            wait.setOnFinished(e -> {
                App.activeState = GameState.GAME_SCREEN;
                drawingLevelScreen = false;
            });

            wait.play();
        }
    }

    public static void drawEndScreen(GraphicsContext graphicsContext) {
        if(!drawingLevelScreen) {
            drawingLevelScreen = true;

            graphicsContext.setFill(Color.BLACK);
            graphicsContext.fillRect(0, 0, App.WIDTH, App.HEIGHT);

            graphicsContext.setFill(Color.GREEN);
            graphicsContext.setTextAlign(TextAlignment.CENTER);
            graphicsContext.setFont(Font.font("Arial", 60));
            graphicsContext.fillText("Argent collecté " + UI.argent + "$", App.WIDTH/2, App.HEIGHT / 2 - 100);

            PauseTransition wait = new PauseTransition(Duration.seconds(3));
            wait.setOnFinished(e -> {
                App.activeState = GameState.GAME_SCREEN;
                drawingLevelScreen = false;
            });

            wait.play();
        }
    }

    private static void initObj(){
        new Wall(0,0, 192, 96);

        Journal.setMass();
        Journal.addJournal(12);
        Maison.genererMaisons(12);

        Camelot camelot = new Camelot(0, 0, 172, 144);
        App.setCamera(new Camera(camelot));

        new UI(0, App.HEIGHT, App.WIDTH, 75);
    }

    public static void levelFinished(){
        nextLevel();
    }

    public static void gameOver(){
        App.activeState = GameState.END_SCREEN;
    }
}
