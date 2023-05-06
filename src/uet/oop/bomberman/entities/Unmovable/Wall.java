package uet.oop.bomberman.entities.Unmovable;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public class Wall extends Entity {

    /**
     * Constructor of Wall
     * @param x: coordinate x
     * @param y: coordinate y
     * @param image: image of wall
     */
    public Wall(int x, int y, Image image) {
        super(x, y, image);
    }

    @Override
    public void update() {}
}
