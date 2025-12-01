package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Camelot extends GameObject implements Gravity{

    /*Les images du Camelot, avec des version
    * la première est la version noramle
    * la deuxième est la version pixels dissiner par VINCENT :)
    */
    private final Image[][] SPRITES = {
            {
                    new Image(getClass().getResourceAsStream("/assets/camelot1.png")),
                    new Image(getClass().getResourceAsStream("/assets/camelot2.png"))},
            {
                    new Image(getClass().getResourceAsStream("/assets/pixelCamelot00.png")),
                    new Image(getClass().getResourceAsStream("/assets/pixelCamelot01.png")),
                    new Image(getClass().getResourceAsStream("/assets/pixelCamelot02.png")),
                    new Image(getClass().getResourceAsStream("/assets/pixelCamelot03.png")),
                    new Image(getClass().getResourceAsStream("/assets/pixelCamelot04.png")),
                    new Image(getClass().getResourceAsStream("/assets/pixelCamelot05.png")),
                    new Image(getClass().getResourceAsStream("/assets/pixelCamelot06.png")),
                    new Image(getClass().getResourceAsStream("/assets/pixelCamelot07.png")),
                    new Image(getClass().getResourceAsStream("/assets/pixelCamelot08.png")),
                    new Image(getClass().getResourceAsStream("/assets/pixelCamelot09.png")),
                    new Image(getClass().getResourceAsStream("/assets/pixelCamelot10.png")),
                    new Image(getClass().getResourceAsStream("/assets/pixelCamelot11.png")),
                    new Image(getClass().getResourceAsStream("/assets/pixelCamelot12.png")),
                    new Image(getClass().getResourceAsStream("/assets/pixelCamelot13.png")),
                    new Image(getClass().getResourceAsStream("/assets/pixelCamelot14.png")),
                    new Image(getClass().getResourceAsStream("/assets/pixelCamelot15.png")),
            }
    };

    private boolean isGrounded = true;

    private double timer = 0;
    private double throwTime = 0;

    private Point2D speed;
    private Point2D acceleration;
    private float speedThrowFactor = 1;

    /**
     * Constructeur de Camelot.
     * @param posX position horizontale initiale
     * @param posY position verticale initiale
     * @param width largeur du sprite
     * @param height hauteur du sprite
     */
    public Camelot(double posX, double posY, double width, double height) {
        super(posX, posY, width, height);
        speed = new Point2D(400,0);
        acceleration = new Point2D(0,0);
    }

    /**
     * Met à jour la position, la physique et l’animation de Camelot.
     */
    @Override
    protected void update() {
        timer += Time.deltaTimeSec;
        position = position.add(speed.multiply(Time.deltaTimeSec));
        speed = speed.add(acceleration.multiply(Time.deltaTimeSec));

        speed = new Point2D(Math.max(Math.min(speed.getX(), 600.), 200), speed.getY());

        handleInput();
    }
    /**
     * Dessine Camelot avec le bon sprite selon l’animation.
     */
    @Override
    protected void draw(GraphicsContext graphicsContext, Camera camera) {
        graphicsContext.drawImage(
                SPRITES[GameObject.imgIndex][(int) (Math.floor(timer * 4) % SPRITES[GameObject.imgIndex].length)],
                position.getX() - camera.getX(),
                graphicsContext.getCanvas().getHeight() -(size.getY()+position.getY()),
                size.getX(), size.getY());
    }

    /**
     * Applique la gravité tant que Camelot n’est pas au sol.
     */
    @Override
    public void applyGravity() {
        if (!isGrounded){
            acceleration = acceleration.add(gravity.multiply(Time.deltaTimeSec));
        }
        if(position.getY() < 0){
            isGrounded = true;
            position = new Point2D(position.getX(), 0);

            acceleration = new Point2D(acceleration.getX(), 0);
            speed = new Point2D(speed.getX(), 0);
        }
    }
    /**
     * Lit les touches pressées et applique les actions
     */
    private void handleInput(){
        //desacceleration
        if(Input.isPressed(KeyCode.RIGHT)) {
            acceleration = new Point2D(300, acceleration.getY());
        }else if (speed.getX() > 400)
            acceleration = new Point2D(-300, acceleration.getY());
        //acceleration augmente
        if(Input.isPressed(KeyCode.LEFT)) {
            acceleration = new Point2D(-300, acceleration.getY());
        }else if (speed.getX() < 400)
            acceleration = new Point2D(+300, acceleration.getY());
        //saute
        if(isGrounded && ((Input.isPressed(KeyCode.SPACE) || (Input.isPressed(KeyCode.UP))))) {
            isGrounded = false;
            speed = speed.add(0, 500);
        }

        speedThrowFactor = Input.isPressed(KeyCode.SHIFT)? 1.5f : 1.f;
        // Lancer les journaux
        //vers le haut
        if(Input.isPressed(KeyCode.X) && timer - throwTime > 0.5) {
            new Journal(position.getX() + size.getX() / 2.,
                    position.getY() + size.getY()/2,
                    52, 31, speed,
                    new Point2D(150, 1100).multiply(speedThrowFactor));

            throwTime = timer;
        }
        // à la longeur
        if(Input.isPressed(KeyCode.Z) && timer - throwTime > 0.5) {
            new Journal(position.getX() + size.getX() / 2.,
                    position.getY() + size.getY()/2,
                    52, 31, speed,
                    new Point2D(900, 900).multiply(speedThrowFactor));

            throwTime = timer;
        }
    }
}