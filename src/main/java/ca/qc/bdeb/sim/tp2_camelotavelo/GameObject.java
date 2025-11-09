package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public abstract class GameObject {
    private final static ArrayList<GameObject> GAME_OBJECT_ARRAY_LIST = new ArrayList<>();

    protected Point2D position;
    protected Point2D size;

    protected abstract void update();
    protected abstract void draw();
    protected void delete(){GAME_OBJECT_ARRAY_LIST.remove(this);}

    public GameObject(double posX, double posY, double width, double height){
        GAME_OBJECT_ARRAY_LIST.add(this);

        position = new Point2D(posX, posY);
        size = new Point2D(width, height);
    }

    public static void updateAll(){
        GAME_OBJECT_ARRAY_LIST.forEach(
                (g) -> {
                    if(g instanceof Gravity)
                        ((Gravity) g).applyGravity();

                    g.update();
                }
        );
    }

    public static void drawAll(){
        GAME_OBJECT_ARRAY_LIST.forEach(GameObject::draw);
    }


}
