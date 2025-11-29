package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;

public class Input {
    private static final Set<KeyCode> PRESSED_KEYS = new HashSet<>();
    private static final Set<KeyCode> LAST_KEYS_STATE = new HashSet<>();

    public static void addKey(KeyCode k){
        PRESSED_KEYS.add(k);
    }
    public static void removeKey(KeyCode k){
        PRESSED_KEYS.remove(k);
    }

    public static boolean isPressed(KeyCode k){return PRESSED_KEYS.contains(k);}
    public static boolean wasPressed(KeyCode k){return LAST_KEYS_STATE.contains(k);}

    public static void endOfFrame(){
        LAST_KEYS_STATE.clear();
        LAST_KEYS_STATE.addAll(PRESSED_KEYS);
    }
}
