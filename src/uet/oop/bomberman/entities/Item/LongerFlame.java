package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.character.Bomb.Bomb;

import static uet.oop.bomberman.BombermanGame.*;

public class LongerFlame extends Item {
    public LongerFlame(int x, int y, Image image) {
        super(x, y, image);
    }

    @Override
    public void powerUpBomber() {
        Bomb.longerFlame();
        System.out.println("Now your flame is longer!");
    }
}
