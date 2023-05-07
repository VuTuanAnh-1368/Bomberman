package uet.oop.bomberman.entities.Character.Bomb;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Character.MovingState;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;
import static uet.oop.bomberman.BombermanGame.FPS;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class Flame extends Entity {

    public static final int maxLength = 4;

    private int length = 0;
    private int explodingTime = FPS;

    public boolean exploded;

    private Image middleFlameImage;
    private final MovingState movingState;

    public Flame(int x, int y, Image image, int length, MovingState ms) {
        super(x, y, image);
        exploded = false;
        this.movingState = ms;
        this.length = Math.min(length, maxLength);
    }

    public void render(GraphicsContext gc, int flameLength) {

        if (length == 1) {
            super.render(gc);
        } else if (length > 0) {
            switch (movingState) {
                case UP -> {
                    for (int i = 0; i < length - 1; i++) {
                        gc.drawImage(middleFlameImage, x, y - SCALED_SIZE * i);
                    }
                    if (length <= flameLength) gc.drawImage(image, x, y - SCALED_SIZE * (length - 1));
                }
                case DOWN -> {
                    for (int i = 0; i < length - 1; i++) {
                        gc.drawImage(middleFlameImage, x, y + SCALED_SIZE * i);
                    }
                    if (length <= flameLength) gc.drawImage(image, x, y + SCALED_SIZE * (length - 1));
                }
                case LEFT -> {
                    for (int i = 0; i < length - 1; i++) {
                        gc.drawImage(middleFlameImage, x - SCALED_SIZE * i, y);
                    }
                    if (length <= flameLength) gc.drawImage(image, x - SCALED_SIZE * (length - 1), y);
                }
                case RIGHT -> {
                    for (int i = 0; i < length - 1; i++) {
                        gc.drawImage(middleFlameImage, x + SCALED_SIZE * i, y);
                    }
                    if (length <= flameLength) gc.drawImage(image, x + SCALED_SIZE * (length - 1), y);
                }
                default -> {
                }
            }
        }
    }

    @Override
    public void update() {
        if (explodingTime == 0) {
            exploded = true;
            return;
        }
        if (length == 0) return;
        explodingTime--;
        if (length >= 1) {
            switch (movingState) {
                case UP -> image = Sprite.movingSprite(Sprite.explosion_vertical_top_last,
                        Sprite.explosion_vertical_top_last1,
                        Sprite.explosion_vertical_top_last2, explodingTime, FPS).getFxImage();
                case DOWN -> image = Sprite.movingSprite(Sprite.explosion_vertical_down_last,
                        Sprite.explosion_vertical_down_last1,
                        Sprite.explosion_vertical_down_last2, explodingTime, FPS).getFxImage();
                case LEFT -> image = Sprite.movingSprite(Sprite.explosion_horizontal_left_last,
                        Sprite.explosion_horizontal_left_last1,
                        Sprite.explosion_horizontal_left_last2, explodingTime, FPS).getFxImage();
                case RIGHT -> image = Sprite.movingSprite(Sprite.explosion_horizontal_right_last,
                        Sprite.explosion_horizontal_right_last1,
                        Sprite.explosion_horizontal_right_last2, explodingTime, FPS).getFxImage();
                default -> {
                }
            }
        }

        if (length >= 2) {
            switch (movingState) {
                case UP, DOWN -> this.middleFlameImage = Sprite.movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1,
                        Sprite.explosion_vertical2, explodingTime, FPS).getFxImage();
                case LEFT, RIGHT -> this.middleFlameImage = Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1,
                        Sprite.explosion_horizontal2, explodingTime, FPS).getFxImage();
                default -> {
                }
            }

        }
    }

    public void setExplodingTime(int timeExplode) {
        this.explodingTime = timeExplode;
    }
}
