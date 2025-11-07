package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public abstract class GameObject {
    private final static ArrayList<GameObject> GAME_OBJECT_ARRAY_LIST = new ArrayList<>();

    protected final Point2D POSITION;
    protected final Point2D SIZE;

    protected abstract void update();
    protected abstract void draw();

    public GameObject(double posX, double posY, double width, double height){
        GAME_OBJECT_ARRAY_LIST.add(this);

        POSITION = new Point2D(posX, posY);
        SIZE = new Point2D(width, height);
    }

    public static void updateAll(){
        GAME_OBJECT_ARRAY_LIST.forEach(GameObject::update);
    }

    public static void drawAll(){
        GAME_OBJECT_ARRAY_LIST.forEach(GameObject::draw);
    }
}
