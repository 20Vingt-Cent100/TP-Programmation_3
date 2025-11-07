package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;

public class Input {
    private static final Set<KeyCode> PRESSED_KEYS = new HashSet<>();

    public static void addKey(KeyCode k){
        PRESSED_KEYS.add(k);
    }
    public static void removeKey(KeyCode k){
        PRESSED_KEYS.remove(k);
    }

    public static boolean isPressed(KeyCode k){return PRESSED_KEYS.contains(k);}
}
