package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.scene.canvas.GraphicsContext;

public interface Debuggable {
    void drawDebugState(GraphicsContext gc, Camera camera);
}
