package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;

public abstract class GameObject {
    private final static ArrayList<GameObject> GAME_OBJECT_ARRAY_LIST = new ArrayList<>();
    private final static ArrayList<GameObject> TEMP_ADD_LIST = new ArrayList<>();
    private final static ArrayList<GameObject> TEMP_REMOVE_LIST = new ArrayList<>();

    protected static int imgIndex = 0;
    private static boolean isDebugStateActive = false;

    protected Point2D position;
    protected Point2D size;
    protected Rectangle2D hitBoxe;

    protected abstract void update();
    protected abstract void draw(GraphicsContext graphicsContext, Camera camera);
    protected void delete(){TEMP_REMOVE_LIST.add(this);}

    public GameObject(double posX, double posY, double width, double height){
        TEMP_ADD_LIST.add(this);

        position = new Point2D(posX, posY);
        size = new Point2D(width, height);
        hitBoxe = new Rectangle2D(posX, posY, width, height);
    }

    public static void updateAll(){

        if (Input.isPressed(KeyCode.D) && !Input.wasPressed(KeyCode.D)){
            isDebugStateActive = !isDebugStateActive;
        }

        if (Input.isPressed(KeyCode.P) && !Input.wasPressed(KeyCode.P)){
            imgIndex = -imgIndex + 1;
        }

        GAME_OBJECT_ARRAY_LIST.addAll(TEMP_ADD_LIST);
        TEMP_ADD_LIST.clear();

        GAME_OBJECT_ARRAY_LIST.removeAll(TEMP_REMOVE_LIST);
        TEMP_REMOVE_LIST.clear();

        GAME_OBJECT_ARRAY_LIST.forEach(
                (g) -> {

                    if(g instanceof Gravity)
                        ((Gravity) g).applyGravity();

                    g.update();
                    g.updateHitboxe();
                }
        );
    }

    public static void checkCollision(){
        for (int i = 0; i < GAME_OBJECT_ARRAY_LIST.size() - 1; i++){
            for (int j = i + 1; j < GAME_OBJECT_ARRAY_LIST.size(); j++) {

                GameObject obj1 = GAME_OBJECT_ARRAY_LIST.get(i);
                GameObject obj2 = GAME_OBJECT_ARRAY_LIST.get(j);

                if(isColliding(obj1, obj2)){
                    if(obj1 instanceof Collidable collidableObj)
                        collidableObj.isColliding(obj2);
                    if(obj2 instanceof Collidable collidableObj)
                        collidableObj.isColliding(obj1);
                }
            }
        }
    }

    public static void drawAll(GraphicsContext graphicsContext, Camera camera){
        camera.updateFov();
        GAME_OBJECT_ARRAY_LIST.forEach(object -> {
            object.draw(graphicsContext, camera);

            if (isDebugStateActive && object instanceof Debuggable debuggable) {
                debuggable.drawDebugState(graphicsContext, camera);
            }
        });
    }

    private static boolean isColliding(GameObject obj1, GameObject obj2){
        return obj1.hitBoxe.intersects(obj2.hitBoxe);
    }

    private void updateHitboxe(){
        hitBoxe = new Rectangle2D(position.getX(), position.getY(), size.getX(), size.getY());
    }
}
