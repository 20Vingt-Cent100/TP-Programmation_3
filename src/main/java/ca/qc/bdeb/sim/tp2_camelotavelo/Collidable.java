package ca.qc.bdeb.sim.tp2_camelotavelo;

//Interface pour les objets capables de détecter et réagir aux collisions.
public interface Collidable {
    void isColliding(GameObject other);
}
