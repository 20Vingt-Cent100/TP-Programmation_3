package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Random;

public class Maison extends GameObject {
 private final int adresse;
 private final boolean abonnee;
 private static final double largeur=120;
 private static final double hauteur =220;

 private final BoiteAuxLettres boite;
 private final Fenetre[] fenetres;

 private static final ArrayList<Maison> LISTE_MAISON = new ArrayList<>();

 public static double coordoneDerniereMaison;

 public Maison(double posX, double posY, int adresse, boolean abonnee, BoiteAuxLettres boite, Fenetre[] fenetres) {
    super(posX, posY ,largeur, hauteur);
     this.adresse = adresse;
     this.abonnee = abonnee;
     this.boite = boite;
     this.fenetres = fenetres;
 }

 public static void genererMaisons (int nbMaisons){
     Random random = new Random();

     LISTE_MAISON.clear();

     int adresseInitiale = random.nextInt(100, 951);

     for (int i = 0; i < nbMaisons; i++) {
         double baseX = 1300.0 * (i + 1);
         int adresse = adresseInitiale + 2 * i;

         boolean abonnee = random.nextBoolean();

         //Génération des boîtes aux lettres
         double yBoite = random.nextDouble(0.20 * App.HEIGHT, 0.7* App.HEIGHT);
         BoiteAuxLettres boites = new BoiteAuxLettres(baseX + 200, App.HEIGHT - yBoite, abonnee);

         //Génération des fenêtres
         Fenetre[] fenetres = new Fenetre[random.nextInt(3)];

         for (int j = 0; j < fenetres.length; j++){
            fenetres[j] = new Fenetre(baseX + 300 * j, App.HEIGHT - 50 - Fenetre.hauteur, abonnee);
         }

         LISTE_MAISON.add(new Maison(baseX, 0, adresse, abonnee, boites, fenetres));
     }

     coordoneDerniereMaison = LISTE_MAISON.getLast().position.getX();
 }



 public double getAdresse() {
     return adresse;
 }
 public boolean estAbonnee() {
     return abonnee;
 }

 @Override
 protected void update(){

 }

    @Override
    public void draw(GraphicsContext gc, Camera camera) {
        double positionX = position.getX() - camera.getX();
        double positionY = App.HEIGHT - (position.getY() + size.getY());

        double largeurPorte = size.getX();
        double hauteurPorte = size.getY();
        gc.setFill(Color.rgb(119, 60, 5));
        gc.fillRect(positionX - 5, positionY + hauteurPorte, largeurPorte + 10, 10);
        gc.setFill(Color.rgb(119, 60, 5));
        gc.fillRect(positionX, positionY, largeurPorte, hauteurPorte);
        double rayonPoignee = 12;
        double poigneeX = positionX + largeurPorte * 0.22;
        double poigneeY = positionY + hauteurPorte * 0.45;
        gc.setFill(Color.rgb(246, 135, 14));
        gc.fillOval(poigneeX - rayonPoignee, poigneeY - rayonPoignee,
                rayonPoignee * 2, rayonPoignee * 2);
        gc.setFill(Color.YELLOW);
        gc.setFont(Font.font(36));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.TOP);
        gc.fillText(Integer.toString(adresse),
                positionX + largeurPorte / 2.0,
                positionY + 10);
    }

    public static ArrayList<Maison> getListeMaison() {
        return LISTE_MAISON;
    }
}
