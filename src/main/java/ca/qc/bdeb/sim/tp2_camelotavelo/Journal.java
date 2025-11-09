package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.scene.image.Image;

import java.util.Random;

public class Journal extends GameObject implements Gravity{
    private final Image SPRITE = new Image("assets/journal.png");
    private static float mass;

    public Journal(double posX, double posY) {
        super(posX, posY, 52, 31);
    }

    @Override
    protected void update() {

    }

    @Override
    protected void draw() {

    }

    public static void reloadMass(){

    }

    public static void CreateJournal(){

    }

    @Override
    public void applyGravity() {

    }
}
