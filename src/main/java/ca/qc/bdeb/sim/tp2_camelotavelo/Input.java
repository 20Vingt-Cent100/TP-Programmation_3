package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe qui s'occupe de gérer les inputs
 */
public class Input {
    //Set qui contient les touches appuyé lors de la frame actuelle
    private static final Set<KeyCode> PRESSED_KEYS = new HashSet<>();

    //Set qui contient les touches qui étaient appuyé lors de la dernière frame
    private static final Set<KeyCode> LAST_KEYS_STATE = new HashSet<>();

    /**
     *Ajoute une touche à la liste de touches appuyées
     * @param k touche à ajoutée
     */
    public static void addKey(KeyCode k){
        PRESSED_KEYS.add(k);
    }

    /**
     *Retire une touche à la liste de touches appuyées
     * @param k touche à retirée
     */
    public static void removeKey(KeyCode k){
        PRESSED_KEYS.remove(k);
    }

    /**
     *Retourne vraie si la touche est appuyée
     * @param k touche à vérifier
     */
    public static boolean isPressed(KeyCode k){return PRESSED_KEYS.contains(k);}

    /**
     *Retourne vraie si la touche était appuyée
     * @param k touche à vérifier
     */
    public static boolean wasPressed(KeyCode k){return LAST_KEYS_STATE.contains(k);}

    /**
     * Mets à jour la liste des touches qui était appuyées.
     */
    public static void endOfFrame(){
        LAST_KEYS_STATE.clear();
        LAST_KEYS_STATE.addAll(PRESSED_KEYS);
    }
}
