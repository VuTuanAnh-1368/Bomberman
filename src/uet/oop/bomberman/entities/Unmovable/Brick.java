package uet.oop.bomberman.entities.Unmovable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.FPS;


public class Brick extends Entity {
    public boolean destroyed = false;
    private boolean destroying = false;
    private int time = FPS;
    private final Image backgroundImage = Sprite.grass.getFxImage();


    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        if (destroying) {
            image = Sprite.movingSprite(Sprite.brick_exploded,
                    Sprite.brick_exploded1,
                    Sprite.brick_exploded2,
                    time--, FPS).getFxImage();
        }
        if (time == 0) {
            destroyed = true;
            destroying = false;
            time--;
        }

    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(backgroundImage, x, y);
        super.render(gc);
    }

    public void destroy() {
        destroying = true;
    }
}