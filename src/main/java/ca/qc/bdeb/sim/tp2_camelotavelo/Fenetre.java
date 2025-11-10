package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Fenetre {
    public static final double largeur = 159;
    public static final double hauteur = 130;

    private final double x;
    private final double y;
    private final boolean abonnee;
    private boolean brisee;
    private Boolean bonneCasse;

    private final Image intacte;
    private final Image briseeVerte;
    private final Image briseeRouge;

    public Fenetre(double x, double y, boolean abonnee, Image intacte, Image briseeVerte, Image briseeRouge) {
        this.x = x;
        this.y = y;
        this.abonnee = abonnee;
        this.intacte = intacte;
        this.briseeVerte = briseeVerte;
        this.briseeRouge = briseeRouge;
        this.brisee = false;
        this.bonneCasse = null;
    }

    public Rectangle2D getLimite() {
        return new Rectangle2D(x, y, largeur, hauteur);
    }

    public void casser(boolean bonne) {
        this.brisee = true;
        this.bonneCasse = bonne;
    }

    public boolean estBrisee() {
        return brisee;
    }

    public boolean estAbonnee() {
        return abonnee;
    }

    public void draw(GraphicsContext gc, double cameraX) {
        double positionAfficher = x - cameraX;
        Image imageFenetre = intacte;
        if (brisee && bonneCasse != null) {
            if (bonneCasse) {
                if (briseeVerte != null) {
                    imageFenetre = briseeVerte;
                } else {
                    imageFenetre = intacte;
                }
            } else {
                if (briseeRouge != null) {
                    imageFenetre = briseeRouge;
                } else {
                    imageFenetre = intacte;
                }
            }
        }
        gc.drawImage(imageFenetre, positionAfficher, y, largeur, hauteur);
    }

    public void drawDebuggage(GraphicsContext gc, double cameraX) {
        double positionAfficher = x - cameraX;
        gc.strokeRect(positionAfficher, y, largeur, hauteur);
    }
}
