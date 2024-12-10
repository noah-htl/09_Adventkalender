package at.htlsaafelden.adventkalender;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public abstract class ImageCache {
    private static Map<String, Image> imageMap = new HashMap<>();

    public static Image get(String path, Class<?> clazz) {
        if(!imageMap.containsKey(path)) {
            imageMap.put(path, create(path, clazz));
        }

        return imageMap.get(path);
    }

    private static Image create(String path, Class<?> clazz) {
        return new Image(clazz.getResourceAsStream(path));
    }
}
