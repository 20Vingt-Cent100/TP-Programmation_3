package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public class Journal extends GameObject implements Gravity, Collidable{
    public static int journalCount = 10;
    private static float mass;

    private Image sprite = new Image(getClass().getResourceAsStream("/assets/journal.png"));

    private Point2D acceleration;
    private Point2D speed;

    public Journal(double posX, double posY, double width, double height, Point2D speed, Point2D initialMomentum) {
        super(posX, posY, width, height);
        this.acceleration = new Point2D(0,0);
        this.speed = speed.add(initialMomentum.multiply(1/mass));
    }

    @Override
    protected void update() {
        position = position.add(speed.multiply(Time.deltaTimeSec));
        speed = speed.add(acceleration.multiply(Time.deltaTimeSec));

        if (position.getY() < 0)
            this.delete();

    }

    @Override
    protected void draw(GraphicsContext graphicsContext, Camera camera) {
        graphicsContext.drawImage(sprite, position.getX() - camera.getX(), graphicsContext.getCanvas().getHeight() -(size.getY()+position.getY()));
    }

    @Override
    public void applyGravity() {
        acceleration = acceleration.add(gravity.multiply(Time.deltaTimeSec));
    }

    public static void setMass(){
        mass = new Random().nextFloat(1, 2);
    }

    @Override
    public void isColliding(GameObject other) {
        if (other instanceof Fenetre || other instanceof BoiteAuxLettres)
            this.delete();
    }
}
