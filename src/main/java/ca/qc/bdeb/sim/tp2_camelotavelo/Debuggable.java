package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.scene.canvas.GraphicsContext;
//Interface pour les objets pouvant afficher du débogage
public interface Debuggable {
    void drawDebugState(GraphicsContext gc, Camera camera);
}
