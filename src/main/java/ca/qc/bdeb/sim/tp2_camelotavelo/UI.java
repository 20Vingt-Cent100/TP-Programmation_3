package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class UI extends GameObject {
    public static int argent = 0;

    private final String addressStr;

    public final Image[] iconeMaison = {
            new Image(getClass().getResourceAsStream("/assets/icone-maison.png")),
            new Image(getClass().getResourceAsStream("/assets/HouseIcon.png"))

    };

    public final Image[] iconeJournal = {
            new Image(getClass().getResourceAsStream("/assets/icone-journal.png")),
            new Image(getClass().getResourceAsStream("/assets/JournalIcon.png"))
    };

    public final Image[] iconeDollar = {
            new Image(getClass().getResourceAsStream("/assets/icone-dollar.png")),
            new Image(getClass().getResourceAsStream("/assets/MoneyIcon.png"))
    };

    public UI(double posX, double posY, double width, double height) {
        super(posX, posY, width, height);
        addressStr = loadAddressString();
    }

    @Override
    protected void update() {
    }

    @Override
    protected void draw(GraphicsContext graphicsContext, Camera camera) {
        graphicsContext.setFill(Color.color(0, 0, 0, 0.5));
        graphicsContext.fillRect(position.getX(),
                graphicsContext.getCanvas().getHeight() - position.getY(),
                size.getX(),
                size.getY());
        //text
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(Font.font(16));
        graphicsContext.setTextAlign(TextAlignment.LEFT);
        graphicsContext.setTextBaseline(VPos.CENTER);

        double separationx = 10;
        double milieuY = size.getY() / 2.0;

        if (iconeJournal[0] != null) {
            graphicsContext.drawImage(iconeJournal[GameObject.imgIndex], separationx, milieuY - 12, 24, 24);
            separationx += 40;
        }
        graphicsContext.fillText(" " + Journal.getJournalCount(), separationx, milieuY);
        separationx += 90;


        if (iconeDollar[0] != null) {
            graphicsContext.drawImage(iconeDollar[GameObject.imgIndex], separationx, milieuY - 12, 24, 24);
            separationx += 38;
        }
        graphicsContext.fillText(String.format("%d $", argent), separationx, milieuY);
        separationx += 110;


        if (iconeMaison[0] != null) {
            graphicsContext.drawImage(iconeMaison[GameObject.imgIndex], separationx, milieuY - 12, 24, 24);
            separationx += 150;
        }


        graphicsContext.fillText(addressStr, separationx, milieuY);
    }

    private String loadAddressString() {
        var maisons = Maison.getListeMaison();

        if (maisons != null) {
            StringBuilder stringBuilder = new StringBuilder();
            boolean premiermaison = true;
            for (Maison m : maisons) {
                if (m.estAbonnee()) {
                    if (!premiermaison) stringBuilder.append(", ");
                    stringBuilder.append((int) m.getAdresse());
                    premiermaison = false;
                }
            }
            return stringBuilder.toString();
        }

        return "";
    }
}
