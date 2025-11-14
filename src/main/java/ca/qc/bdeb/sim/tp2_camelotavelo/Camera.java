package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.geometry.Point2D;

public class Camera {
    private final GameObject followedObject;

    private Point2D fov;

    public Camera(GameObject followedObject){
        this.followedObject = followedObject;
        updateFov();
    }


    public void updateFov(){
        fov = followedObject.position.add(- 0.20 * App.WIDTH, 0);
    }

    public double getX(){
        return fov.getX();
    }

    public double getY(){
        return fov.getY();
    }
}