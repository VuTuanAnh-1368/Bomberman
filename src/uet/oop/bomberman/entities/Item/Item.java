package uet.oop.bomberman.entities.Item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Unmovable.Grass;
import java.awt.*;
import static uet.oop.bomberman.BombermanGame.bomber;
import static uet.oop.bomberman.BombermanGame.map;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;
import static uet.oop.bomberman.sound.Sound.getItemSound;
import static uet.oop.bomberman.sound.Sound.playMedia;


public abstract class Item extends Entity {
    protected boolean Hide = true;

    public boolean Lying = true;

    public Item(int x, int y, Image image) {
        super(x, y, image);
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(x + SCALED_SIZE / 4, y + SCALED_SIZE / 4, SCALED_SIZE / 2, SCALED_SIZE / 2);
    }

    @Override
    public void update() {
        if (CollisionTest(this.getRectangle(), bomber.getRectangle())) {
            playMedia(getItemSound);
            powerUpBomber();
            Lying = false;
            image = null;
        }
        if (map.get(getPosition()) instanceof Grass) {
            Hide = false;
        }
    }

    @Override
    public void render(GraphicsContext graphicsContext) {
        if (!Hide) {
            super.render(graphicsContext);
        }
    }

    public abstract void powerUpBomber();
}
