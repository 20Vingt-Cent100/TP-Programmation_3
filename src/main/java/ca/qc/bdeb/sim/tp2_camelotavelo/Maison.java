package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.List;

public class Maison extends GameObject {
 private final double posX;
 private final double posY;
 private final int adresse;
 private final boolean abonnee;
 private static final double largeur=120;
 private static final double hauteur =220;

 private final BoiteAuxLettres boite;
 private final List<Fenetre> fenetres;

 public Maison(double posX, double posY, int adresse, boolean abonnee, BoiteAuxLettres boite, List<Fenetre> fenetres) {
    super(posX, posY - hauteur,largeur, hauteur);
     this.posX = posX;
     this.posY = posY;
     this.adresse = adresse;
     this.abonnee = abonnee;
     this.boite = boite;
     this.fenetres = fenetres;
 }

 public double getPosX() {
     return posX;
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

 public void draw (GraphicsContext gc , Camera camera) {
     double positionX = position.getX() - camera.getX();
     double largeurPorte = size.getX();
     double hauteurPorte =size.getY();
     double positionPorteX = positionX;
     double positionPorteY = posY - hauteurPorte;

     gc.setFill(Color.rgb(119,60,5));
     gc.fillRect(positionPorteX - 5, posY - 10, largeurPorte + 10, 10);

     gc.setFill(Color.rgb(119,60,5));
     gc.fillRect(positionPorteX, positionPorteY, largeurPorte, hauteurPorte);

     double rayonPoignie = 12;
     double poignieX = positionPorteX + largeurPorte * 0.22;
     double poignieY = posY - hauteurPorte * 0.55;

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
             positionPorteY + 10);

     boite.draw(gc, camera);
     for (Fenetre f : fenetres) {
         f.draw(gc, camera);
     }
 }
    public void drawDebuggage(GraphicsContext gc,Camera camera) {
        boite.drawDebuggage(gc, camera);
        for (Fenetre f : fenetres) {
            f.drawDebuggage(gc, camera);
        }
    }



}
