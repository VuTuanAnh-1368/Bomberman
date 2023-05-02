package uet.oop.bomberman.entities.Item;


import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Unmovable.Grass;

import static uet.oop.bomberman.BombermanGame.*;

public class Portal extends Item {

    public Portal(int x, int y, Image image) {
        super(x, y, image);
    }

    @Override
    public void update() {
        if (!Hide && CollisionTest(getRectangle(), bomber.getRectangle())) {
            portal = null;
            Lying = false;
            image = null;
        }
        if (enemies.size() == 0 && map.get(getPosition()) instanceof Grass) {
            Hide = false;
        }
    }

    @Override
    public void powerUpBomber() {
        System.out.println("Now you have in portal!");
    }
}