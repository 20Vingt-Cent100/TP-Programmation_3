package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;


public class Wall extends GameObject{
    private final Image[] img = {new Image(getClass().getResourceAsStream("/assets/brique.png")),
            new Image(getClass().getResourceAsStream("/assets/pixelBrique.png"))};

    public Wall(double posX, double posY, double width, double height) {
        super(posX, posY, width, height);
    }

    @Override
    protected void update() {

    }

    @Override
    protected void draw(GraphicsContext graphicsContext, Camera camera) {
        ImagePattern pattern = new ImagePattern(img[GameObject.imgIndex],position.getX() - camera.getX(), 0, size.getX(), size.getY(), false);
        graphicsContext.setFill(pattern);
        graphicsContext.fillRect(0,0, graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight());
    }
}
