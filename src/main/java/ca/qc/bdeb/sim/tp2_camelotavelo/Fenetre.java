package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Fenetre extends GameObject implements Collidable, Debuggable {
   //Attributs
    private static final double LARGEUR = 159;
    private static final double HAUTEUR = 130;
    private final boolean abonnee;
    private boolean brisee;
    private Boolean bonneCasse;
    // Images Fênetre
    private final Image [][] SPRITES = {
            {new Image(getClass().getResourceAsStream("/assets/fenetre.png")),
                    new Image(getClass().getResourceAsStream("/assets/fenetre-brisee-vert.png")),
                    new Image(getClass().getResourceAsStream("/assets/fenetre-brisee-rouge.png"))
            },
            {new Image(getClass().getResourceAsStream("/assets/Window.png"))}
    };

    /**
     * Constructeur
     * @param posX position X dans le monde
     * @param posY position Y dans le monde
     * @param abonnee indique si la maison est abonnée au journal
     */
    public Fenetre( double posX , double posY, boolean abonnee) {
   super(posX,posY, LARGEUR, HAUTEUR);
        this.abonnee = abonnee;
        this.brisee = false;
        this.bonneCasse = null;
    }

    /**
     * Réaction à une collision.
     * Si un journal touche la fenêtre, elle se casse et augmente ou diminue l'argent.
     */
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

    //Dessine la fenêtre dans l'écran selon son état.
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
    //Dessine la hitbox de la fenêtre lorsque le mode debug est actif
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
