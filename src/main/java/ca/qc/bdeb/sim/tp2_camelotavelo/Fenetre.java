package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Fenetre extends GameObject {
    public static final double largeur = 159;
    public static final double hauteur = 130;

    private final boolean abonnee;
    private boolean brisee;
    private Boolean bonneCasse;

    private final Image intacte;
    private final Image briseeVerte;
    private final Image briseeRouge;

    public Fenetre( double posX , double posY, boolean abonnee) {
   super(posX,posY, largeur, hauteur);
        this.abonnee = abonnee;
        this.intacte = new Image(getClass().getResourceAsStream("/assets/fenetre.png"));;
        this.briseeVerte = new Image(getClass().getResourceAsStream("/assets/fenetre-brisee-vert.png"));
        this.briseeRouge = new Image(getClass().getResourceAsStream("/assets/fenetre-brisee-rouge.png"));;
        this.brisee = false;
        this.bonneCasse = null;
    }

    public Rectangle2D getLimite() {
        return new Rectangle2D(position.getX(), position.getY() , largeur, hauteur);
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

    @Override
    protected void update(){

    }

    @Override
    public void draw(GraphicsContext gc, Camera camera) {
        double posX = position.getX() - camera.getX();
        double posY = position.getY() - camera.getY();

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
        gc.drawImage(imageFenetre, posX, posY, largeur, hauteur);
    }

    public void drawDebuggage(GraphicsContext gc, Camera camera) {
        double posX = position.getX() - camera.getX();
        double posY = position.getY() - camera.getY();
        gc.strokeRect(posX, posY, largeur, hauteur);
    }
}
