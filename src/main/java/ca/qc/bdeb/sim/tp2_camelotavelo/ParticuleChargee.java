package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class ParticuleChargee extends GameObject {

    public static final double RAYON  = 10.0;
    public static final double CHARGE = 900.0;

    private static final List<ParticuleChargee> PARTICULES = new ArrayList<>();

    private final Color color;

    public ParticuleChargee(Point2D position, Color color) {
        super(position.getX(), position.getY(), 0, 0);
        this.color = color;
        PARTICULES.add(this);
    }

    public Point2D getPosition() {
        return position;
    }


    public static Point2D champElectrique(Point2D positionMonde) {
        Point2D total = new Point2D(0, 0);

        for (ParticuleChargee p : PARTICULES) {
            Point2D diff = positionMonde.subtract(p.getPosition());
            double distance = diff.magnitude();
            if (distance < 1) distance = 1;

            double k = 90.0;
            double Ei = k * Math.abs(CHARGE) / (distance * distance);

            Point2D dir = diff.normalize();
            Point2D contrib = dir.multiply(Ei);
            total = total.add(contrib);
        }

        return total;
    }

    // TODO: changer ca ca marche oas
    public static void testParticles() {
        PARTICULES.clear();

        double marge = 10;
        double yBas  = RAYON + marge;                // ligne du bas
        double yHaut = App.HEIGHT - (RAYON + marge); // ligne du haut

        for (double x = RAYON + marge; x < App.WIDTH - RAYON - marge; x += 50) {
            new ParticuleChargee(new Point2D(x, yBas),  Color.YELLOW);
            new ParticuleChargee(new Point2D(x, yHaut), Color.YELLOW);
        }
    }


    @Override
    protected void update() {

    }

    @Override
    protected void draw(GraphicsContext gc, Camera camera) {
        double screenX = position.getX() - camera.getX() - RAYON;
        double screenY = gc.getCanvas().getHeight() - position.getY() - RAYON;

        gc.setFill(color);
        gc.fillOval(screenX, screenY, RAYON * 2, RAYON * 2);
    }

    public static void clearAll() {
        PARTICULES.clear();
    }

    public static void generParticlesRandom(int nb, double levelWidth, double levelHeight, java.util.Random rnd) {
        clearAll();
        for (int i = 0; i < nb; i++) {
            double x = rnd.nextDouble() * levelWidth;
            double y = rnd.nextDouble() * levelHeight;
            javafx.scene.paint.Color c = javafx.scene.paint.Color.hsb(rnd.nextDouble() * 360.0, 1, 1);
            new ParticuleChargee(new Point2D(x, y), c);
        }
    }

    public static void drawDebugageChamp(GraphicsContext gc, Camera camera) {
        double pas = 50.0;
        double hauteurEcran = gc.getCanvas().getHeight();

        double debutX = camera.getX();
        double finX = camera.getX() + App.WIDTH;
        for (double x = debutX; x < finX; x += pas) {
            for (double y = 0; y <= App.HEIGHT; y += pas) {
                Point2D positionMonde = new Point2D(x, y);
                double ecranX = x - camera.getX();
                double ecranY = hauteurEcran - y;
                Point2D force = champElectrique(positionMonde);
                if (force.magnitude() < 1e-3) continue;
                Point2D champEcran = new Point2D(force.getX(), -force.getY());
                UtilitairesDessins.dessinerVecteurForce(
                        new Point2D(ecranX, ecranY),
                        champEcran,
                        gc
                );
            }
        }
    }


}