package uet.oop.bomberman.entities.Character.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Character.MovingState;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class Balloom extends Enemy {

    public Balloom(int x, int y, Image image) {
        super(x, y, image);
        movingState = MovingState.RIGHT;
        movable = true;
        velocity = SCALED_SIZE / 16;
        point = 50;
    }

    @Override
    public void changeMovingState() {
        if (!movable) {
            randomMovingState();
        }
    }

    @Override
    protected void updateImg() {
        switch (movingState) {
            case LEFT, DOWN -> image = animate(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3);
            case UP, RIGHT -> image = animate(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3);
            default -> {
            }
        }
    }

    @Override
    public void loadDestroyImage() {
        if (timeDeathLoading == 0) {
            alive = false;
        }
        image = Sprite.balloom_dead.getFxImage();
        timeDeathLoading = timeDeathLoading - 1;
    }
}
