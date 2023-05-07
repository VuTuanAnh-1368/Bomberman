package uet.oop.bomberman.entities.Character.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Character.MovingState;
import uet.oop.bomberman.graphics.Sprite;
import static uet.oop.bomberman.BombermanGame.FPS;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class Doll extends Enemy {

    private int timeChangeSpeed = FPS * 2;

    private int timeChangeMoveState = FPS * 3;

    public Doll(int x, int y, Image image) {
        super(x, y, image);
        movable = true;
        movingState = MovingState.RIGHT;
        velocity = SCALED_SIZE / 8;
        point = 70;
    }

    @Override
    public void changeMovingState() {
        timeChangeMoveState--;
        if (!movable || timeChangeMoveState == 0) {
            randomMovingState();
            timeChangeMoveState = FPS * 3;
        }
    }

    public void changeSpeed() {
        int sp1 = SCALED_SIZE / 16;
        int sp2 = SCALED_SIZE / 8;
        int sp3 = SCALED_SIZE / 4;

        int rand = (int) (Math.random() * 3 + 1);
        timeChangeSpeed--;
        if (timeChangeSpeed == 0) {
            if (rand == 1) {
                velocity = sp1;
            } else if (rand == 2) {
                velocity = sp2;
            } else if (rand == 3) {
                velocity = sp3;
            }
            timeChangeSpeed = FPS * 3;
        }
    }


    @Override
    public void update() {
        changeSpeed();
        super.update();
    }

    @Override
    public void loadDestroyImage() {
        if (timeDeathLoading == 0) {
            alive = false;
        }
        image = Sprite.doll_dead.getFxImage();
        timeDeathLoading--;
    }

    @Override
    public void updateImg() {
        switch (movingState) {
            case LEFT:
            case UP:
                image = animate(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3);
                break;
            case RIGHT:
            case DOWN:
                image = animate(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3);
                break;
            default:
                break;
        }
    }
}
