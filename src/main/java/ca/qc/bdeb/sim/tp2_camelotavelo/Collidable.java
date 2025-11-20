package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.geometry.Rectangle2D;

public interface Collidable {
    Rectangle2D getLimite();
    void isColliding(GameObject other);
}
