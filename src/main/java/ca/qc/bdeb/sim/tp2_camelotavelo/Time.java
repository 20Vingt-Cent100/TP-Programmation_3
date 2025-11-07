package ca.qc.bdeb.sim.tp2_camelotavelo;

public class Time {
    public static double deltaTimeSec;
    public static long deltaTimeNano;

    private static long pastTime;

    public static void loadPastTime(){pastTime = System.nanoTime();}

    public static void deltaTime(long now){
        deltaTimeNano = now - pastTime;
        deltaTimeSec = deltaTimeNano / 1_000_000_000.;

        pastTime = now;
    }
}
