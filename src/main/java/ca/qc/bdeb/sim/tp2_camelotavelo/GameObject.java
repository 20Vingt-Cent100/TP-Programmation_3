package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;

/**
 * Classe de base pour tous les objets du jeu.
 * ça gère:
 * - l'enregistrement des objets
 * - les updates et les draw de tous les objets
 * - les boîtes de collision
 * - les modes de débogage
 */

public abstract class GameObject {
    //Listes
    private final static ArrayList<GameObject> GAME_OBJECT_ARRAY_LIST = new ArrayList<>();
    private final static ArrayList<GameObject> TEMP_ADD_LIST = new ArrayList<>();
    private final static ArrayList<GameObject> TEMP_REMOVE_LIST = new ArrayList<>();
    //États
    protected static int imgIndex = 0;
    private static boolean isDebugStateActive = false;
    private static boolean etatDebugChamp = false;
    //Attributs
    protected Point2D position;
    protected Point2D size;
    protected Rectangle2D hitBoxe;

    //méthodes abstract
    protected abstract void update();
    protected abstract void draw(GraphicsContext graphicsContext, Camera camera);

    protected void delete(){TEMP_REMOVE_LIST.add(this);}

    /**
     * Constructeur de base
     *
     * @param posX   position en X dans le monde
     * @param posY   position en Y dans le monde
     * @param width  largeur de l'objet
     * @param height hauteur de l'objet
     */
    public GameObject(double posX, double posY, double width, double height){
        TEMP_ADD_LIST.add(this);

        position = new Point2D(posX, posY);
        size = new Point2D(width, height);
        hitBoxe = new Rectangle2D(posX, posY, width, height);
    }

    /**
     * Met à jour tous les GameObject :
     * - applique la gravité
     * - appelle update sur chaque objet
     * - synchronise les hitbox au position.
     */
    public static void updateAll(){
        GAME_OBJECT_ARRAY_LIST.addAll(TEMP_ADD_LIST);
        TEMP_ADD_LIST.clear();

        GAME_OBJECT_ARRAY_LIST.removeAll(TEMP_REMOVE_LIST);
        TEMP_REMOVE_LIST.clear();

        GAME_OBJECT_ARRAY_LIST.forEach(
                (g) -> {

                    if(g instanceof Gravity)
                        ((Gravity) g).applyGravity();

                    g.update();
                    g.updateHitboxe();
                }
        );

    }


    /**
     * Vérifie les collisions
     * Si deux objets se touchent et implémentent Collidable,
     * leur méthode isColliding est appelée.
     */
    public static void checkCollision(){
        for (int i = 0; i < GAME_OBJECT_ARRAY_LIST.size() - 1; i++){
            for (int j = i + 1; j < GAME_OBJECT_ARRAY_LIST.size(); j++) {

                GameObject obj1 = GAME_OBJECT_ARRAY_LIST.get(i);
                GameObject obj2 = GAME_OBJECT_ARRAY_LIST.get(j);

                if(isColliding(obj1, obj2)){
                    if(obj1 instanceof Collidable collidableObj)
                        collidableObj.isColliding(obj2);
                    if(obj2 instanceof Collidable collidableObj)
                        collidableObj.isColliding(obj1);
                }
            }
        }
    }

    /**
     * Dessine tous les objets par rapport à la caméra.
     * Si le debug des hitbox est activé ça dessine aussi les hitbox et la ligne de la caméra.
     * Si le debug du champ est activé, ça dessine le champ électrique.
     */
    public static void drawAll(GraphicsContext graphicsContext, Camera camera){
        camera.updateFov();
        GAME_OBJECT_ARRAY_LIST.forEach(object -> {
            object.draw(graphicsContext, camera);

            if (isDebugStateActive && object instanceof Debuggable debuggable) {
                debuggable.drawDebugState(graphicsContext, camera);
                camera.drawDebugState(graphicsContext, camera);
            }
        });
        if (etatDebugChamp) {
           ParticuleChargee.drawDebugageChamp(graphicsContext, camera);
        }
    }

    //getter de la liste d'objets
    protected static java.util.ArrayList<GameObject> getGameObjects() {
        return GAME_OBJECT_ARRAY_LIST;
    }
    // Supprime tous les objets du jeu
    public static void clearAll() {
        GAME_OBJECT_ARRAY_LIST.clear();
        TEMP_ADD_LIST.clear();
        TEMP_REMOVE_LIST.clear();
    }
    //vérifie la collision selon le hitboxe
    private static boolean isColliding(GameObject obj1, GameObject obj2){
        return obj1.hitBoxe.intersects(obj2.hitBoxe);
    }
    // met à jour le hitboxe
    private void updateHitboxe(){
        hitBoxe = new Rectangle2D(position.getX(), position.getY(), size.getX(), size.getY());
    }
    //Active ou désactive le mode debug des hitbox et de la caméra.
    public static void switchDebugState(){
        isDebugStateActive = !isDebugStateActive;
    }
    //Active ou désactive le mode debug du champ électrique
    public static void switchDebugParticleState(){
        etatDebugChamp = !etatDebugChamp;
    }
}
