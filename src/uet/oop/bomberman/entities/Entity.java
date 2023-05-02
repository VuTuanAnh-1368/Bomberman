package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.awt.Rectangle;

import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;
import static uet.oop.bomberman.BombermanGame.WIDTH;

public abstract class Entity {

    // Coordinate x, y
    protected int x;
    protected int y;
    // Image of entity
    protected Image image;

    //Constructor
    public Entity(int _x, int _y, Image image1) {
        x = _x * SCALED_SIZE;
        y = _y * SCALED_SIZE;
        image = image1;
    }

    //Coordinate getter
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    // Check for Collision
    protected boolean CollisionTest(Rectangle r1, Rectangle r2) {
        return r1.intersects(r2);
    }

    /**
     * @param graphicsContext: render piece of image to (x, y) in the scene
     */
    public void render(GraphicsContext graphicsContext) {
        graphicsContext.drawImage(image, x, y);
    }

    /**
     * @return java.awt rectangle surrounding entity.
     */
    public Rectangle getRectangle() {
        return new Rectangle(x, y, SCALED_SIZE, SCALED_SIZE);
    }

    /**
     * @return position of entity
     */
    public int getPosition() {
        return (x / SCALED_SIZE) + (y / SCALED_SIZE * WIDTH);
    }

    public abstract void update();
}
