package ca.qc.bdeb.sim.tp2_camelotavelo;

public class Time {
    public static double deltaTimeSec;
    public static long deltaTimeNano;

    //Temps de début de la dernière frame
    private static long pastTime;

    /**
     * Génère la valeur initiale de pastTime (Juste avant de démarrer la boucle de jeu)
     */
    public static void loadPastTime(){pastTime = System.nanoTime();}

    /**
     * Génère la différence entre le pastTime et now
     * @param now le temps actuelle de jeu
     */
    public static void deltaTime(long now){
        deltaTimeNano = now - pastTime;
        deltaTimeSec = deltaTimeNano / 1_000_000_000.;

        pastTime = now;
    }
}
