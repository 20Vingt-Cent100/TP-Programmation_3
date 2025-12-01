package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Fenetre extends GameObject implements Collidable, Debuggable {
    private static final double largeur = 159;
    private static final double hauteur = 130;

    private final boolean abonnee;
    private boolean brisee;
    private Boolean bonneCasse;

    private final Image [][] SPRITES = {
            {new Image(getClass().getResourceAsStream("/assets/fenetre.png")),
                    new Image(getClass().getResourceAsStream("/assets/fenetre-brisee-vert.png")),
                    new Image(getClass().getResourceAsStream("/assets/fenetre-brisee-rouge.png"))
            },
            {new Image(getClass().getResourceAsStream("/assets/Window.png"))}
    };


    public Fenetre( double posX , double posY, boolean abonnee) {
   super(posX,posY, largeur, hauteur);
        this.abonnee = abonnee;
        this.brisee = false;
        this.bonneCasse = null;
    }

    @Override
    public void isColliding(GameObject other) {
        if (other instanceof Journal && !brisee){
            brisee = true;
            if (abonnee) {
                bonneCasse = false;
                UI.argent -= 2;
            } else {
                bonneCasse = true;
                UI.argent += 2;
            }
        }
    }

    @Override
    protected void update(){

    }

    @Override
    public void draw(GraphicsContext gc, Camera camera) {

        Image imageFenetre = SPRITES[imgIndex][0];

        if (brisee && bonneCasse != null) {
            if (bonneCasse) {
                if (SPRITES[0][1] != null) {
                    imageFenetre = SPRITES[0][1];
                } else {
                    imageFenetre = SPRITES[imgIndex][0];
                }
            } else {
                if (SPRITES[0][2] != null) {
                    imageFenetre = SPRITES[0][2];
                } else {
                    imageFenetre = SPRITES[imgIndex][0];
                }
            }
        }
        gc.drawImage(imageFenetre,
                position.getX() - camera.getX(),
                gc.getCanvas().getHeight() -(size.getY()+position.getY())
        );
    }

    @Override
    public void drawDebugState(GraphicsContext gc, Camera camera) {
        gc.setStroke(Color.YELLOW);
        gc.strokeRect(hitBoxe.getMinX() - camera.getX(),
                gc.getCanvas().getHeight() -(hitBoxe.getHeight()+hitBoxe.getMinY()),
                hitBoxe.getWidth(),
                hitBoxe.getHeight()
        );
    }
}
