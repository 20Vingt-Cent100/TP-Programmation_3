package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class CamelotAVelo extends Application {
    private final static int WIDTH = 900, HEIGHT = 580;

    //Canvas utilisé pour l'ensemble du jeu
    private final static Canvas C_WINDOW = new Canvas(WIDTH, HEIGHT);


    @Override
    public void start(Stage stage) throws IOException {
        var root = new VBox();
        root.getChildren().add(C_WINDOW);

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        //Scene Event Listeners
        scene.setOnKeyPressed((k) -> Input.addKey(k.getCode()));
        scene.setOnKeyReleased((k) -> Input.removeKey(k.getCode()));

        //Configuration du stage
        stage.setTitle("CamelotAVelo");
        stage.setScene(scene);
        stage.show();

        gameObjectInit();
        gameLoop(stage);
    }

    /**
     * Initialise tous les objets de jeux nécessaire au début du jeu
     */
    private void gameObjectInit(){
        Player camelot = new Player(100,HEIGHT - 144, 172, 144);
    }

    /**
     * Boucle de jeu
     */
    private void gameLoop(Stage st){
        //Initialise la variable pastTime
        Time.loadPastTime();

        var loop = new AnimationTimer(){
            double t = 0;
            @Override
            public void handle(long now) {
                getGraphicContext().setFill(Color.BROWN);
                getGraphicContext().fillRect(0, 0, WIDTH, HEIGHT);

                //Calcule le différence de temps entre maintenant et la dernière frame
                Time.deltaTime(now);

                GameObject.updateAll();
                GameObject.drawAll();
                t += Time.deltaTimeSec;

                if(t > 1) {
                    t -= 1;
                    st.setTitle(String.format("FPS: %d", (int) (1 / Time.deltaTimeSec)));
                }
            }
        };
        loop.start();
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * Permet d'obtenir le contexte graphique de n'importe où dans l'application
     * pour pouvoir y dessiner les objets
     * @return Le contexte graphique du canvass C_WINDOW
     */
    public static GraphicsContext getGraphicContext(){
        return C_WINDOW.getGraphicsContext2D();
    }
}