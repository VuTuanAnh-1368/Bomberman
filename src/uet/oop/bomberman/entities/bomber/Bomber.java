package uet.oop.bomberman.entities.bomber;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.controller.audio.Audio;
import uet.oop.bomberman.controller.conllision.CollisionManager;
import uet.oop.bomberman.entities.EntityDestroyable;
import uet.oop.bomberman.graphics.sprite.Sprite;

public class Bomber extends EntityDestroyable {
    /**
     * Size Bomber.
     */
    public static final int HEIGHT = Sprite.SCALED_SIZE * 30 / 32;
    public static final int WIDTH = Sprite.SCALED_SIZE * 20 / 32;
    public static final int FIX_SIZE = Sprite.SCALED_SIZE * 11 / 32;
    /**
     * Bomber collision management.
     */
    private CollisionManager collisionManager;

    /*
        Bomber status.
     */
    public enum bomberStatus {
        ALIVE, DEAD
    }
    bomberStatus status;

    /**
     * Move Bomber and life of Bomber.
     */
    public static int speed;
    public static int flameLength;
    public static int bombCount;
    public static int lifeCount;

    /**
     * Place Bomb and event Keycode.
     */
    private boolean placedBomb = false, isExit = false;
    private boolean goUp = false, goDown = false, goLeft = false, goRight = false;
    private KeyCode lastKey = KeyCode.D;

    /**
     * Audio -- sound of action.
     */
    private Audio audio = new Audio();

    /**
     * status move (up, left,down,right) -- img = sprite (Bomber).
     */
    private int spriteIndex = 0;

    /**
     *
     * @param x
     * @param y
     * @param img
     * @param collisionManager
     */
    public Bomber(int x, int y, Image img, CollisionManager collisionManager) {
        super(x, y, img);
        this.collisionManager = collisionManager;
        resetStarts();
    }

    /**
     * Starts.
     */
    public void resetStarts() {
        lifeCount = 3;
        speed = 2;
        flameLength = 1;
        bombCount = 1;
        status = bomberStatus.ALIVE;
        pickSprite(Sprite.player_right.getFxImage()); //initial state
    }

    /**
     * Status key press or key release.
     * @param keyCode
     * @param isPress
     */
    public void isPressed(KeyCode keyCode, boolean isPress) {
        switch (keyCode) {
            case UP :
            case W :
                goUp = isPress;
                lastKey = keyCode;
                break;
            case DOWN:
            case S:
                goDown = isPress;
                lastKey = keyCode;
                break;
            case LEFT:
            case A:
                goLeft = isPress;
                lastKey = keyCode;
                break;
            case RIGHT:
            case D:
                goRight = isPress;
                lastKey = keyCode;
                break;
            case SPACE:
                placedBomb = isPress;
                break;
            case ESCAPE:
                isExit = isPress;
                break;
            default:
                break;
        }
        if (isPress == false) {
            spriteIndex = 0;
        }
    }

    /**
     * Method get/set.
     */
    public int getLivesCount() {
        return lifeCount;
    }

    public void setLifeCount(int lifeCount) {
        this.lifeCount = lifeCount;
    }

    public int getBombCount() {
        return bombCount;
    }

    public int getFlameLength() {
        return flameLength;
    }

    public int getSpeed() {
        return speed;
    }


}
