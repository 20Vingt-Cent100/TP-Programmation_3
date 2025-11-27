package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BoiteAuxLettres extends GameObject implements Collidable{
    public static final double largeur = 81;
    public static final double hauteur = 76;
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
    public Rectangle2D getLimite() {
        return new Rectangle2D(position.getX(), position.getY(), size.getX(), size.getY());
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
        double posX = position.getX() - camera.getX();
        double posY = position.getY();

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
        gc.drawImage(imageFenetre, posX, posY, largeur, hauteur);
    }

    public void drawDebuggage(GraphicsContext gc, Camera camera) {
        double posX = position.getX() - camera.getX();
        double posY = position.getY();
        gc.strokeRect(posX, posY, size.getX(), size.getY());
    }

}
