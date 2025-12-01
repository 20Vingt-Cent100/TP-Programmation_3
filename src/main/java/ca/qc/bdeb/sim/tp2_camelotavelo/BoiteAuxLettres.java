package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class BoiteAuxLettres extends GameObject implements Collidable, Debuggable{
    //Attributs
    public static final double LARGEUR = 81;
    public static final double HAUTEUR = 76;
    private final boolean abonnee;
    private boolean touchee;
    private Boolean livraisonBonne;

    // Image BoiteAuxLettres
    private final Image [][] SPRITES = {
            {
                    new Image(getClass().getResourceAsStream("/assets/boite-aux-lettres.png")),
                    new Image(getClass().getResourceAsStream("/assets/boite-aux-lettres-vert.png")),
                    new Image(getClass().getResourceAsStream("/assets/boite-aux-lettres-rouge.png"))},
            {
    }};

    /**
     * Constructeur.
     * @param posX position horizontale
     * @param posY position verticale
     * @param abonnee indique si la maison est abonnée au journal
     */
    public BoiteAuxLettres(double posX, double posY, boolean abonnee) {
      super(posX,posY, LARGEUR, HAUTEUR);
        this.abonnee = abonnee;
        this.touchee = false;
        this.livraisonBonne = null;
    }

    /**
     * Gestion d'une collision avec un autre GameObject.
     * Seuls les journaux déclenchent une action.
     */
    @Override
    public void isColliding(GameObject other) {
        if (other instanceof Journal && !touchee) {
            touchee = true;
            if (abonnee) {
                livraisonBonne = true;
                UI.argent += 1;
            } else {
                livraisonBonne = false;
            }
        }
    }

    @Override
    protected void update() {

    }

    /**
     * Dessine la boîte aux lettres selon son état
     * (normal, verte ou rouge).
     *
     */
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

    /**
     * Dessine la hitbox en mode debug.
     */
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
