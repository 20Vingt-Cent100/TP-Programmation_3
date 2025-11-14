package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Player extends GameObject{
    private final Image[] sprites = new Image []{new Image(getClass().getResourceAsStream("/assets/camelot1.png")) , new Image(getClass().getResourceAsStream("/assets/camelot2.png")) };
    private boolean isGrounded = true;

    public Player(double posX, double posY, double width, double height) {
        super(posX, posY, width, height);
    }

    @Override
    protected void update() {
        if(Input.isPressed(KeyCode.D) || Input.isPressed(KeyCode.RIGHT)) {
            POSITION = POSITION.add(100 * Time.deltaTimeSec, 0);
        }

        if(Input.isPressed(KeyCode.A) || Input.isPressed(KeyCode.LEFT)) {
            POSITION = POSITION.add(-100 * Time.deltaTimeSec, 0);
        }

        if(isGrounded && (Input.isPressed(KeyCode.W) || (Input.isPressed(KeyCode.SPACE) || (Input.isPressed(KeyCode.UP))))) {
            POSITION = POSITION.add(0, -100);
            isGrounded = false;
        }
    }

    @Override
    protected void draw() {

    }
}
