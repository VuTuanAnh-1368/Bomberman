package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;
import static uet.oop.bomberman.BombermanGame.*;

public class SpeedUp extends Item {
    public SpeedUp(int x, int y, Image image) {
        super(x, y, image);
    }

    @Override
    public void powerUpBomber() {
        bomber.speedUp();
        System.out.println("Now you have speed up!");
    }
}
