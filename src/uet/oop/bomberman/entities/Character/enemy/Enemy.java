package uet.oop.bomberman.entities.Character.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Character.Character;
import uet.oop.bomberman.entities.Character.MovingState;
import static uet.oop.bomberman.BombermanGame.bomber;
import static uet.oop.bomberman.BombermanGame.enemies;

public abstract class Enemy extends Character {
    /**
     * Point after it's killed
     * Vie: số điểm sau khi bị giết
     */
    protected int point;

    public Enemy(int x, int y, Image image) {
        super(x, y, image);
    }

    public abstract void changeMovingState();

    /**
     * Check collision with other enemies. if 2 enemies meet, they will reverse moves
     * Vie: Hàm này kiểm tra va chạm giữa các 2 quân địch, nếu 2 quân địch gặp nhau, chúng sẽ đi ngược lại
     */
    protected void checkCollisionEnemy() {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = (Enemy) enemies.get(i);
            if (this != e && e.movable) {
                if (CollisionTest(this.getNextRectangle(), e.getNextRectangle())) {
                    this.movable = false;
                    e.movable = false;
                    this.reverseMovingState();
                    e.reverseMovingState();
                }
            }
        }
    }

    /**
     * @return point after kill an enemy
     */
    public int getPoint() {
        return point;
    }

    /**
     * Set a random moving state for enemies.
     * Vie: Cài đặt trạng thái di chuyển mặc định cho quân địch.
     */
    protected void randomMovingState() {
        if (!alive || deathLoading) return;
        movingState = MovingState.random();
        while (movingState == MovingState.STANDING) {
            movingState = MovingState.random();
        }
    }


    /**
     * Reverse Moving State funtion is used to reverse moving state 1 enemy
     * Vie: Hàm này sẽ đảo ngược trạng thái di chuyển hiện tại của một quân địch
     */
    protected void reverseMovingState() {
        if (!alive || deathLoading) return;
        switch (movingState) {
            case UP:
                movingState = MovingState.DOWN;
                break;
            case DOWN:
                movingState = MovingState.UP;
                break;
            case LEFT:
                movingState = MovingState.RIGHT;
                break;
            case RIGHT:
                movingState = MovingState.LEFT;
                break;
        }
    }

    /**
     * If an enemy die, load it's destroy image
     * else keep moving, check collision with other enemies, bombs, and update moving image.
     *
     * Vie: Nếu quân địch chết, tải hình ảnh bị chết của nó
     * nếu không: tiếp tục di chuyển, kiểm tra va chạm với các quân địch khác, bom, sau đó tải hình ảnh đang di chuyển
     * của nó
     */
    @Override
    public void update() {
        if (deathLoading) {
            loadDestroyImage();
            return;
        }
        changeMovingState();
        checkCollisionEnemy();
        moveChecking(bomber.bom);
        move();
        updateImg();
    }
}
