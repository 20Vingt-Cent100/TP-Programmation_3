package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Random;

/**
 * Représente un journal lancé par le camelot.
 * - il est affecte par la gravité
 * - il est attiré ou repoussé par les particules chargées
 * - il peut entrer en collision avec les fenêtres / boîtesaux lettres
 * - il disparaît lorsqu'il touche un objet ou sort de l'écran
 */
public class Journal extends GameObject implements Gravity, Collidable, Debuggable {
    private static int journalCount = 0;
    private static float mass;
    private static final double Charge = 900.0;

    private static int journauxActif;

    private final Image sprite = new Image(getClass().getResourceAsStream("/assets/journal.png"));

    private Point2D acceleration;
    private Point2D speed;

    /**
     * Constructeur : crée un journal lancé par le Camelot.
     * @param posX position X
     * @param posY position Y
     * @param width largeur
     * @param height hauteur
     * @param speed vitesse initiale du Camelot
     * @param initialMomentum impulsion ajoutée lors du lancer
     */
    public Journal(double posX, double posY, double width, double height, Point2D speed, Point2D initialMomentum) {
        super(posX, posY, width, height);

        if (journalCount <= 0) {
            this.delete();
        }else {
            journauxActif++; // Ce journal existe physiquement dans le niveau
            journalCount--; // On enlève 1 du stock
            this.acceleration = new Point2D(0, 0);
            this.speed = speed.add(initialMomentum.multiply(1 / mass));
        }
    }

    //Mise à jour du journal
    @Override
    protected void update() {
        // Déplacement physique de base
        position = position.add(speed.multiply(Time.deltaTimeSec));
        speed = speed.add(acceleration.multiply(Time.deltaTimeSec));

        // calcul et influence du champ électrique des particules
        Point2D centre = position.add(size.getX() / 2.0, size.getY() / 2.0);
        Point2D champ = ParticuleChargee.champElectrique(centre);
        Point2D aChamp = champ.multiply(Charge / mass);
        //la vittesse influencer par le champ électrique
        speed = speed.add(aChamp.multiply(Time.deltaTimeSec));

        //en bas de l'écran delete le journal
        if (position.getY() < 0) {
            this.delete();
        }

    }
    //Dessin du journal à l'écran
    @Override
    protected void draw(GraphicsContext graphicsContext, Camera camera) {
        graphicsContext.drawImage(sprite, position.getX() - camera.getX(), graphicsContext.getCanvas().getHeight() - (size.getY() + position.getY()));
    }
    //Application de la gravité
    @Override
    public void applyGravity() {
        acceleration = acceleration.add(gravity.multiply(Time.deltaTimeSec));
    }
    // setter de la masse
    public static void setMass() {
        mass = new Random().nextFloat(1, 2);
    }
    // Réaction après collision avec une fenêtre ou une boîte aux lettres
    @Override
    public void isColliding(GameObject other) {
        if (other instanceof Fenetre || other instanceof BoiteAuxLettres)
            this.delete();
    }
    // Ajouter journal
    public static void addJournal(int n) {
        journalCount += n;
    }
    // getter
    public static int getJournalCount() {
        return journalCount;
    }
    public static int getJournauxActif() {
        return journauxActif;
    }
    //vider l'inventaire
    public static void emptyJournals(){
        journalCount = 0;
    }

    //Dessine la hitbox pour le mode debug
    @Override
    public void drawDebugState(GraphicsContext gc, Camera camera) {
        gc.setStroke(Color.YELLOW);
        gc.strokeRect(hitBoxe.getMinX() - camera.getX(),
                gc.getCanvas().getHeight() -(hitBoxe.getHeight()+hitBoxe.getMinY()),
                hitBoxe.getWidth(),
                hitBoxe.getHeight()
        );
    }
    //Supprime ce journal de la scène On retire aussi 1 du compteur des journaux actifs
    @Override
    protected void delete() {
        super.delete();
        journauxActif--;
    }
}
