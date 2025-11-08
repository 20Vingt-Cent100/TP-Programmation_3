package ca.qc.bdeb.sim.tp2_camelotavelo;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class AssetsLoader {
    private final static HashMap<String, Image> ASSETS = new HashMap<>();

    public static void loadAssets(){
        File f = new File("src/main/resources/assets");
        try {
            for (String s : f.list()) {
                ASSETS.put(s, new Image("assets/" + s));
            }
        }
        catch(NullPointerException exception){
            System.out.println("File: " + f.getPath() + " was empty or nonexistent");
        }
    }

    public static Image getImage(String key){
        return ASSETS.get(key);
    }
}
