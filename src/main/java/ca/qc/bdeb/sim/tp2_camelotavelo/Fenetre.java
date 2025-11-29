package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Fenetre extends GameObject implements Collidable {
    public static final double largeur = 159;
    public static final double hauteur = 130;

    private final boolean abonnee;
    private boolean brisee;
    private Boolean bonneCasse;

    private final Image [][] SPRITES = {
            {new Image(getClass().getResourceAsStream("/assets/fenetre.png")),
                    new Image(getClass().getResourceAsStream("/assets/fenetre-brisee-vert.png")),
                    new Image(getClass().getResourceAsStream("/assets/fenetre-brisee-rouge.png"))
            },
            {}
    };


    public Fenetre( double posX , double posY, boolean abonnee) {
   super(posX,posY, largeur, hauteur);
        this.abonnee = abonnee;
        this.brisee = false;
        this.bonneCasse = null;
    }

    @Override
    public void isColliding(GameObject other) {
        if (other instanceof Journal){
            brisee = true;
            this.bonneCasse = abonnee;
        }
    }

    public boolean bonneCasse() {return bonneCasse;}
    public boolean estBrisee() {
        return brisee;
    }
    public boolean estAbonnee() {
        return abonnee;
    }

    @Override
    protected void update(){

    }

    @Override
    public void draw(GraphicsContext gc, Camera camera) {
        double posX = position.getX() - camera.getX();
        double posY = position.getY();

        Image imageFenetre = SPRITES[0][0];

        if (brisee && bonneCasse != null) {
            if (bonneCasse) {
                if (SPRITES[0][1] != null) {
                    imageFenetre = SPRITES[0][1];
                } else {
                    imageFenetre = SPRITES[0][0];
                }
            } else {
                if (SPRITES[0][2] != null) {
                    imageFenetre = SPRITES[0][2];
                } else {
                    imageFenetre = SPRITES[0][0];
                }
            }
        }
        gc.drawImage(imageFenetre, posX, posY, size.getX(),size.getY());
    }

    public void drawDebuggage(GraphicsContext gc, Camera camera) {
        double posX = position.getX() - camera.getX();
        double posY = position.getY();
        gc.strokeRect(posX, posY, size.getX(), size.getY());
    }
}
