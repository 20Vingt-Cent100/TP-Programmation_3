package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BoiteAuxLettres {
    public static final double largeur = 81;
    public static final double hauteur = 76;

    private final double x;
    private final double y;
    private final boolean abonnee;
    private boolean touchee;
    private Boolean livraisonBonne;

    private final Image normale;
    private final Image vert;
    private final Image rouge;

    public BoiteAuxLettres(double x, double y, boolean abonnee) {
        this.x = x;
        this.y = y;
        this.abonnee = abonnee;
        this.normale = new Image(getClass().getResourceAsStream("/assets/boite-aux-lettres.png"));;
        this.vert = new Image(getClass().getResourceAsStream("/assets/boite-aux-lettres-vert.png"));;
        this.rouge = new Image(getClass().getResourceAsStream("/assets/boite-aux-lettres-rouge.png"));;
        this.touchee = false;
        this.livraisonBonne = null;
    }

    public Rectangle2D getLimite() {
        return new Rectangle2D(x, y, largeur, hauteur);
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

    public void livrer(boolean versAbonnee) {
        this.touchee = true;
        this.livraisonBonne = versAbonnee;
    }

    public void draw(GraphicsContext gc, double cameraX) {
        double positionAfficher = x - cameraX;
        Image imageFenetre = normale;
        if (livraisonBonne != null && livraisonBonne) {
            if (vert != null) {
                imageFenetre = vert;
            } else {
                imageFenetre = normale;
            }
        } else if (livraisonBonne != null && !livraisonBonne) {
            if (rouge != null) {
                imageFenetre = rouge;
            } else {
                imageFenetre = normale;
            }
        }
        gc.drawImage(imageFenetre, positionAfficher, y, largeur, hauteur);
    }

    public void drawDebuggage(GraphicsContext gc, double cameraX) {
        double positionAfficher = x - cameraX;
        gc.strokeRect(positionAfficher, y, largeur, hauteur);
    }

}
