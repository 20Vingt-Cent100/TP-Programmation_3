package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class BoiteAuxLettres extends GameObject implements Collidable, Debuggable{
    private static final double largeur = 81;
    private static final double hauteur = 76;
    private final boolean abonnee;
    private boolean touchee;
    private Boolean livraisonBonne;

    private final Image [][] SPRITES = {
            {
                    new Image(getClass().getResourceAsStream("/assets/boite-aux-lettres.png")),
                    new Image(getClass().getResourceAsStream("/assets/boite-aux-lettres-vert.png")),
                    new Image(getClass().getResourceAsStream("/assets/boite-aux-lettres-rouge.png"))},
            {
    }};

    public BoiteAuxLettres(double posX, double posY, boolean abonnee) {
      super(posX,posY,largeur,hauteur);
        this.abonnee = abonnee;
        this.touchee = false;
        this.livraisonBonne = null;
    }

    @Override
    public void isColliding(GameObject other) {
        if (!(other instanceof Journal)) return;
        if (touchee) return;
        touchee = true;
        if (abonnee) {
            livraisonBonne = true;
            UI.argent += 1;
        } else {
            livraisonBonne = false;
        }
    }

    public boolean estAbonnee() {
        return abonnee;
    }

    public boolean estTouchee() {
        return touchee;
    }

    public Boolean livraisonBonne() {
        return livraisonBonne;
    }


    @Override
    protected void update() {

    }

    public void draw(GraphicsContext gc, Camera camera) {
        Image imageFenetre = SPRITES[0][0];

        if (livraisonBonne != null && livraisonBonne) {
            if (SPRITES[0][1] != null) {
                imageFenetre = SPRITES[0][1];
            } else {
                imageFenetre = SPRITES[0][0];
            }
        } else if (livraisonBonne != null && !livraisonBonne) {
            if (SPRITES[0][2] != null) {
                imageFenetre = SPRITES[0][2];
            } else {
                imageFenetre = SPRITES[0][0];
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
