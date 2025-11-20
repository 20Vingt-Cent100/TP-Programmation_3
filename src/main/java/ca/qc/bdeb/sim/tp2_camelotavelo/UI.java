package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.List;

public class UI extends GameObject{
    public static int journauxInventaire = 0;
    public static double argent = 0;
    public static List<Maison> maisons;

    public final Image[] iconeMaison =  {new Image(getClass().getResourceAsStream("/assets/icone-maison.png")),
    };
    public final Image[] iconeJournal =  {new Image(getClass().getResourceAsStream("/assets/icone-journal.png")),
    };
    public final Image [] iconeDollar =  {new Image(getClass().getResourceAsStream("/assets/icone-dollar.png")),
    };

    public UI(double posX, double posY, double width, double height) {
        super(posX, posY, width, height);
    }

    @Override
    protected void update() {
    }
    @Override
    protected void draw(GraphicsContext graphicsContext, Camera camera) {
        graphicsContext.setFill(Color.color(0,0,0,0.5));
        graphicsContext.fillRect(position.getX(),
                graphicsContext.getCanvas().getHeight() - position.getY(),
                size.getX(),
                size.getY());
        //tetx
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(Font.font(16));
        graphicsContext.setTextAlign(TextAlignment.LEFT);
        graphicsContext.setTextBaseline(VPos.CENTER);

        double separationx = 10;
        double milieuY = size.getY() / 2.0;

        if (iconeJournal[0] != null) {
            graphicsContext.drawImage(iconeJournal[0], separationx, milieuY - 12, 24, 24);
            separationx += 40;
        }
        graphicsContext.fillText(" " + journauxInventaire, separationx, milieuY);
        separationx += 90;


        if (iconeDollar[0] != null) {
            graphicsContext.drawImage(iconeDollar[0], separationx, milieuY - 12, 24, 24);
            separationx += 38;
        }
        graphicsContext.fillText(String.format("%.0f $", argent), separationx, milieuY);
        separationx += 110;


        if (iconeMaison[0] != null) {
            graphicsContext.drawImage(iconeMaison[0], separationx, milieuY - 12, 24, 24);
            separationx += 150;
        }

        if (maisons != null) {
            StringBuilder stringBuilder = new StringBuilder();
            boolean premiermaison = true;
            for (Maison maison : maisons) {
                if (maison.estAbonnee()) {
                    if (!premiermaison) stringBuilder.append(", ");
                    stringBuilder.append((int) maison.getAdresse());
                    premiermaison = false;
                }
            }
            graphicsContext.fillText(stringBuilder.toString(), separationx, milieuY);
        }

    }
}
