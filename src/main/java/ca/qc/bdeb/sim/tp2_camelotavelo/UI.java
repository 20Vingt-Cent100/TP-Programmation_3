package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class UI extends GameObject{

    public UI(double posX, double posY, double width, double height) {
        super(posX, posY, width, height);
    }

    @Override
    protected void update() {

    }

    @Override
    protected void draw(GraphicsContext graphicsContext, Camera camera) {
        graphicsContext.setFill(Color.color(0,0,0,0.5));
        graphicsContext.fillRect(position.getX(),
                graphicsContext.getCanvas().getHeight() - position.getY(),
                size.getX(),
                size.getY());


    }
}
