package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.geometry.Point2D;

public interface Gravity {
    Point2D gravity = new Point2D(0, -1500);
    void applyGravity();
}
