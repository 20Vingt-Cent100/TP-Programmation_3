package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Objects;

public abstract class GameObject {
    private final static ArrayList<GameObject> GAME_OBJECT_ARRAY_LIST = new ArrayList<>();
    private final static ArrayList<GameObject> TEMP_ADD_LIST = new ArrayList<>();
    private final static ArrayList<GameObject> TEMP_REMOVE_LIST = new ArrayList<>();

    protected static int imgIndex = 0;

    protected Point2D position;
    protected Point2D size;

    protected abstract void update();
    protected abstract void draw(GraphicsContext graphicsContext, Camera camera);
    protected void delete(){TEMP_REMOVE_LIST.add(this);}

    public GameObject(double posX, double posY, double width, double height){
        TEMP_ADD_LIST.add(this);

        position = new Point2D(posX, posY);
        size = new Point2D(width, height);
    }

    public static void updateAll(){
        GAME_OBJECT_ARRAY_LIST.addAll(TEMP_ADD_LIST);
        TEMP_ADD_LIST.clear();

        GAME_OBJECT_ARRAY_LIST.removeAll(TEMP_REMOVE_LIST);
        TEMP_REMOVE_LIST.clear();

        GAME_OBJECT_ARRAY_LIST.forEach(
                (g) -> {

                    if(g instanceof Gravity)
                        ((Gravity) g).applyGravity();

                    g.update();
                }
        );
    }

    public static void drawAll(GraphicsContext graphicsContext, Camera camera){
        camera.updateFov();
        GAME_OBJECT_ARRAY_LIST.forEach(object -> object.draw(graphicsContext, camera));
    }

    protected String getImagePath(String imagePath){
        return Objects.requireNonNull(this.getClass().getResourceAsStream("assets/" + imagePath)).toString();
    }
}
