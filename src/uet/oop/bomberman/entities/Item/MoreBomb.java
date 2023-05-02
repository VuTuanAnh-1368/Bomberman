package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;
import static uet.oop.bomberman.BombermanGame.*;

public class MoreBomb extends Item {
    public MoreBomb(int x, int y, Image image) {
        super(x, y, image);
    }

    @Override
    public void powerUpBomber() {
        bomber.moreBomb();
        System.out.println("Now you have more bomb!");
    }
}
