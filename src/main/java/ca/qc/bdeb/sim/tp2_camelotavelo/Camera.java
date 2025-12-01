package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Camera implements Debuggable{
    //Objet suivi par la caméra
    private final GameObject followedObject;
    //Position actuelle du champ de vision
    private Point2D fov;

    /**
     * Constructeur de la caméra.
     * @param followedObject l’objet que la caméra doit suivre
     */
    public Camera(GameObject followedObject){
        this.followedObject = followedObject;
        updateFov();
    }


    //La caméra reste toujours 20% à gauche de l’écran.
    public void updateFov(){
        fov = followedObject.position.add(- 0.20 * App.WIDTH, 0);
    }

    public double getX(){
        return fov.getX();
    }

    public double getY(){
        return fov.getY();
    }

    //Dessine une ligne verticale indiquant la limite du champ de vision
    @Override
    public void drawDebugState(GraphicsContext gc, Camera camera) {
        gc.strokeLine(App.WIDTH * 0.2, 0, App.WIDTH * 0.2, App.HEIGHT);
    }
}