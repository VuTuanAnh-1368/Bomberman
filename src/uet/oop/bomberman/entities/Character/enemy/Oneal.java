package uet.oop.bomberman.entities.Character.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Character.MovingState;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.FPS;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class Oneal extends Enemy {

    /**
     * time to change moving state (fixed: 3s)
     * Vie: thời gian sẽ thay đổi trạng thái di chuyển (cố định = 3s)
     */
    private static final int timeToChangeMovingState = FPS * 3;

    /**
     * Temporary to count down
     * Vie: biến tạm để đếm ngược
     */
    private int timeToChange = timeToChangeMovingState;

    /**
     * Enemy: Oneal
     * @param x: x coordinate
     * @param y: y coordinate
     * @param image: Oneal's image
     *
     * Quân địch Oneal:
     * Vận tốc(tốc độ) mặc định: 1 ô / 8 / s
     * Điểm khi giết được Oneal: 70
     */
    public Oneal(int x, int y, Image image) {
        super(x, y, image);
        velocity = SCALED_SIZE / 8;
        movingState = MovingState.RIGHT;
        point = 70;
    }

    @Override
    protected void updateImg() {
        switch (movingState) {
            case LEFT:
            case UP:
                image = animate(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3);
                break;
            case RIGHT:
            case DOWN:
                image = animate(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3);
                break;
            default:
                break;
        }
    }

    @Override
    public void loadDestroyImage() {
        if (timeDeathLoading == 0) {
            alive = false;
        }
        image = Sprite.oneal_dead.getFxImage();
        timeDeathLoading--;
    }

    /**
     * When countdown time = 0 or unmovable => change moving state
     * Vie:Khi thời gian đếm ngược = 0 hoặc không thể di chuyển => đổi hướng di chuyển
     *
     */
    @Override
    public void changeMovingState() {
        timeToChange--;
        if (timeToChange == 0 || !movable) {
            randomMovingState();
            timeToChange = timeToChangeMovingState;
        }
    }
}
