package com.spaceblaster.util;

import javafx.scene.shape.Rectangle;
import java.util.List;

public class CollisionDetector {
    
    public static boolean checkCollision(Rectangle r1, Rectangle r2) {
        return r1.intersects(r2.getBoundsInParent());
    }
    
    public static <T> T getCollision(Rectangle rect, List<?> objects, Class<T> type) {
        for (Object obj : objects) {
            if (obj instanceof Rectangle) {
                if (rect.intersects(((Rectangle) obj).getBoundsInParent())) {
                    return type.cast(obj);
                }
            }
        }
        return null;
    }
}