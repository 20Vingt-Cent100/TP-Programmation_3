package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Maison extends GameObject {
 private final int adresse;
 private final boolean abonnee;
 private static final double largeur=120;
 private static final double hauteur =220;

 private final BoiteAuxLettres boite;
 private final List<Fenetre> fenetres;

 public Maison(double posX, double posY, int adresse, boolean abonnee, BoiteAuxLettres boite, List<Fenetre> fenetres) {
    super(posX, posY ,largeur, hauteur);
     this.adresse = adresse;
     this.abonnee = abonnee;
     this.boite = boite;
     this.fenetres = fenetres;
 }

 public static List<Maison> genererMaisons (int nbMaisons, double hauteurFenetreJeu , double posY, Random random){
     List<Maison> maisons = new ArrayList<>();
     int adresseInitiale = 100 + random.nextInt(851);
     for (int i = 0; i < nbMaisons; i++) {
         double baseX = 1300.0 * (i + 1);
         int adresse = adresseInitiale + 2 * i;
         boolean abonnee = random.nextBoolean();
         double yBoite = hauteurFenetreJeu * (0.2 + 0.5 * random.nextDouble());
         BoiteAuxLettres boite = new BoiteAuxLettres(
                 baseX + 200, yBoite, abonnee
         );

         List<Fenetre> fenetres = new ArrayList<>();
         int nbFenetres = random.nextInt(3); // 0,1,2
         double yFenetre = 50;
         if (nbFenetres >= 1) {
             fenetres.add(new Fenetre(baseX + 300, yFenetre, abonnee));
         }
         if (nbFenetres == 2) {
             fenetres.add(new Fenetre(baseX + 600, yFenetre, abonnee));
         }
         Maison maison = new Maison(baseX, posY, adresse, abonnee, boite, fenetres);
         maisons.add(maison);
     }

   return maisons;
 }



 public double getAdresse() {
     return adresse;
 }
 public boolean estAbonnee() {
     return abonnee;
 }
 public BoiteAuxLettres getBoite() {
     return boite;
 }
 public List<Fenetre> getFenetres() {
     return fenetres;
 }

 @Override
 protected void update(){

 }

    @Override
    public void draw(GraphicsContext gc, Camera camera) {
        double positionX = position.getX() - camera.getX();
        double positionY = position.getY();
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
    public void drawDebuggage(GraphicsContext gc,Camera camera) {
        boite.drawDebuggage(gc, camera);
        for (Fenetre f : fenetres) {
            f.drawDebuggage(gc, camera);
        }
    }
}
