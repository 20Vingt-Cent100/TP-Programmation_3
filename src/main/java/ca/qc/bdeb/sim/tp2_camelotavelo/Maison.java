package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.List;

public class Maison {
 private final double base;
 private final int adresse;
 private final boolean abonnee;

 private final BoiteAuxLettres boite;
 private final List<Fenetre> fenetres;

 public Maison(double base, int adresse, boolean abonnee, BoiteAuxLettres boite, List<Fenetre> fenetres) {
     this.base = base;
     this.adresse = adresse;
     this.abonnee = abonnee;
     this.boite = boite;
     this.fenetres = fenetres;
 }

 public double getBase() {
     return base;
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

 public void draw (GraphicsContext gc , double cameraX, double solY) {
     double positionX = base - cameraX;
     double largeurPorte = 120;
     double hauteurPorte =220;
     double positionPorteX = positionX;
     double largeurPorteY = solY - hauteurPorte;

     gc.setFill(Color.rgb(119,60,5));
     gc.fillRect(positionPorteX - 5, solY - 10, largeurPorte + 10, 10);

     gc.setFill(Color.rgb(119,60,5));
     gc.fillRect(positionPorteX, largeurPorteY, largeurPorte, hauteurPorte);

     double rayonPoignie = 12;
     double poignieX = positionPorteX + largeurPorte * 0.22;
     double poignieY = solY - hauteurPorte * 0.55;

     gc.setFill(Color.rgb(200, 101, 8));
     gc.setFill(Color.rgb(210, 160, 90, 0.9));
     gc.fillOval(poignieX - rayonPoignie, poignieY - rayonPoignie,
             rayonPoignie * 2, rayonPoignie * 2);

     gc.setFill(Color.YELLOW);
     gc.setFont(Font.font(36));
     gc.setTextAlign(TextAlignment.CENTER);
     gc.setTextBaseline(VPos.TOP);
     gc.fillText(Integer.toString(adresse),
             positionPorteX + largeurPorte / 2.0,
             largeurPorteY + 10);
     boite.draw(gc, cameraX);
     for (Fenetre f : fenetres) {
         f.draw(gc, cameraX);
     }
 }
    public void drawDebuggage(GraphicsContext gc, double cameraX) {
        boite.drawDebuggage(gc, cameraX);
        for (Fenetre f : fenetres) {
            f.drawDebuggage(gc, cameraX);
        }
    }



}
