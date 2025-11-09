package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Player extends GameObject implements Gravity{
    private final Image[] SPRITES = {new Image("assets/camelot1.png"), new Image("assets/camelot2.png")};
    private boolean isGrounded = true;

    private double timer = 0;

    private Point2D speed;
    private Point2D acceleration;

    public Player(double posX, double posY, double width, double height) {
        super(posX, posY, width, height);
        speed = new Point2D(400,0);
        acceleration = new Point2D(0, 0);
    }

    @Override
    protected void update() {
        timer += Time.deltaTimeSec;
        position = position.add(speed.multiply(Time.deltaTimeSec));
        speed = speed.add(acceleration.multiply(Time.deltaTimeSec));

        handleInput();
    }

    private void handleInput(){
        if(Input.isPressed(KeyCode.D) || Input.isPressed(KeyCode.RIGHT)) {
            acceleration.add(300, 0);
        }

        if(Input.isPressed(KeyCode.A) || Input.isPressed(KeyCode.LEFT)) {
            acceleration.add(-300, 0);
        }

        if(isGrounded && (Input.isPressed(KeyCode.W) || (Input.isPressed(KeyCode.SPACE) || (Input.isPressed(KeyCode.UP))))) {
            isGrounded = false;
            speed = speed.add(0, -500);
        }
    }

    @Override
    protected void draw() {
        CamelotAVelo.getGraphicContext().drawImage(SPRITES[(int) (Math.floor(timer * 4) % 2)], position.getX(), position.getY(), size.getX(), size.getY());
    }

    @Override
    public void applyGravity() {
        Point2D gravity = new Point2D(0, 1500);

        if (!isGrounded){
            acceleration = acceleration.add(gravity.multiply(Time.deltaTimeSec));
        }
    }
}
