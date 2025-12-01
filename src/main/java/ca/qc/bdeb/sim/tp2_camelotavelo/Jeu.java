package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jeu {

    private enum EtatJeu {
        CHARGEMENT,   // écran Niveau X
        EN_COURS,     // on joue
        FINI          // écran de fin
    }

    private EtatJeu etat = EtatJeu.CHARGEMENT;
    private double tempsEtat = 0;

    private int niveau = 1;
    private double positionFinNiveau = 0;   // x où le niveau est considéré fini

    private final Random random = new Random();

    private Camelot camelot;
    private Camera camera;
    private List<Maison> maisons = new ArrayList<>();

    public Jeu() {
        nouvellePartie();
    }

    private void nouvellePartie() {
        niveau = 1;
        UI.argent = 0;
        Journal.emptyJournals();   // remet l'inventaire à 0
        etat = EtatJeu.CHARGEMENT;
        tempsEtat = 0;

        initialiserNiveau();       // prépare le niveau 1 (mais on voit juste l’écran de chargement au début)
    }

    private void initialiserNiveau() {
        // On efface tous les GameObject du niveau précédent
        GameObject.clearAll();
        maisons.clear();

        // Mur de briques
        new Wall(0, 0, 192, 96);

        // Génération des maisons
        maisons = Maison.genererMaisons(12, App.HEIGHT, 350, random);

        // Camelot
        double solY = 0; // dans ton jeu, y=0 est le sol
        camelot = new Camelot(0, solY, 172, 144);
        camera  = new Camera(camelot);

        // UI
        new UI(0, App.HEIGHT, App.WIDTH, 75);

        // Journaux +12 à chaque nouveau niveau
        Journal.setMass();
        Journal.addJournal(12);

        // Position où le niveau est considéré comme fini
        if (!maisons.isEmpty()) {
            double baseDerniereMaison = maisons.get(maisons.size() - 1).getBaseX();
            positionFinNiveau = baseDerniereMaison + 1.5 * App.WIDTH;
            ParticuleChargee.setNiveauWidth(positionFinNiveau);
        } else {
            positionFinNiveau = 3 * App.WIDTH;
        }

        // Particules chargées à partir du niveau 2
        if (niveau >= 2) {
            int nbPart = Math.min((niveau - 1) * 30, 400);
            ParticuleChargee.generParticlesRandom(nbPart, positionFinNiveau, App.HEIGHT, random);
        } else {
            ParticuleChargee.clearAll();
        }
    }

    public void update(double dt) {
        tempsEtat += dt;
        switch (etat) {
            case CHARGEMENT -> {
                if (tempsEtat >= 3.0) {
                    // après 3 s, on commence le niveau
                    etat = EtatJeu.EN_COURS;
                    tempsEtat = 0;
                }
            }
            case EN_COURS -> updateEnCours();
            case FINI -> {
                if (tempsEtat >= 3.0) {
                    // après 3 s sur l'écran de fin, on recommence une partie complète
                    nouvellePartie();
                }
            }
        }
    }

    private void updateEnCours() {
        handleInputs();
        GameObject.updateAll();
        GameObject.checkCollision();

        // Fin de niveau
        if (camelot.position.getX() > positionFinNiveau) {
            niveau++;
            etat = EtatJeu.CHARGEMENT;
            tempsEtat = 0;
            initialiserNiveau();
            return;
        }
        // Fin de partie  (plus de journaux en inventaire ET plus aucun journal à l'écran)
        if (Journal.getJournalCount() <= 0 && !Journal.journalsActive()) {
            etat = EtatJeu.FINI;
            tempsEtat = 0;
        }
    }

    public void draw(GraphicsContext gc) {
        switch (etat) {
            case CHARGEMENT -> drawEcranChargement(gc);
            case EN_COURS -> drawJeu(gc);
            case FINI -> drawEcranFin(gc);
        }
    }



    private void drawJeu(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, App.WIDTH, App.HEIGHT);
        GameObject.drawAll(gc, camera);
    }

    private void drawEcranChargement(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, App.WIDTH, App.HEIGHT);

        gc.setFill(Color.LIMEGREEN);
        gc.setFont(javafx.scene.text.Font.font(48));
        gc.setTextAlign(javafx.scene.text.TextAlignment.CENTER);
        gc.setTextBaseline(javafx.geometry.VPos.CENTER);

        String texte = "Niveau " + niveau;
        gc.fillText(texte, App.WIDTH / 2.0, App.HEIGHT / 2.0);
    }

    private void drawEcranFin(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, App.WIDTH, App.HEIGHT);

        gc.setFont(javafx.scene.text.Font.font(36));
        gc.setTextAlign(javafx.scene.text.TextAlignment.CENTER);
        gc.setTextBaseline(javafx.geometry.VPos.CENTER);

        gc.setFill(Color.RED);
        gc.fillText("Fin de la partie !", App.WIDTH / 2.0, App.HEIGHT / 2.0 - 30);

        gc.setFill(Color.LIMEGREEN);
        gc.fillText(String.format("Argent total : %.0f $", UI.argent),
                App.WIDTH / 2.0, App.HEIGHT / 2.0 + 20);
    }

    private static void handleInputs(){
        if (Input.isPressed(KeyCode.D) && !Input.wasPressed(KeyCode.D)){
            GameObject.switchDebugState();
        }

        if (Input.isPressed(KeyCode.Q) && !Input.wasPressed(KeyCode.Q)){
            Journal.addJournal(10);
        }

        if (Input.isPressed(KeyCode.P) && !Input.wasPressed(KeyCode.P)){
           GameObject.imgIndex = -GameObject.imgIndex + 1;
        }

        if (Input.isPressed(KeyCode.K) && !Input.wasPressed(KeyCode.K)){
            Journal.emptyJournals();
        }
        if (Input.isPressed(KeyCode.F) && !Input.wasPressed(KeyCode.F)) {
            GameObject.switchDebugParticleState();
        }
        if (Input.isPressed(KeyCode.I) && !Input.wasPressed(KeyCode.I)) {
            ParticuleChargee.testParticles();
        }
    }
    // TODO: Faire le debbuagge des particules ca marche pas et debegage des nivaux TOUCHE I, L , F
}