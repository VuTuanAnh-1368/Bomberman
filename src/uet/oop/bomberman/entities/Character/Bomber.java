package uet.oop.bomberman.entities.Character;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Character.Bomb.Bomb;
import uet.oop.bomberman.entities.Character.enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;
import java.util.List;
import static uet.oop.bomberman.BombermanGame.FPS;
import static uet.oop.bomberman.BombermanGame.enemies;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;
import static uet.oop.bomberman.sound.Sound.*;
import static uet.oop.bomberman.sound.Sound.plantBombSound;
import static uet.oop.bomberman.entities.Character.Bomb.Bomb.resetFlameLength;

public class Bomber extends uet.oop.bomberman.entities.Character.Character {

    private final int maxNumberOfBombs = 4;
    private int haveBomb = 1;

    private final int maxSpeed = SCALED_SIZE / 4;

    // number of life in a game
    public static int maxLife = 3;

    //Coordinate x, y to respond after died
    private final int respondX;
    private final int respondY;

    //Bomber bring list of bomb
    public static List<Entity> bom = new java.util.ArrayList<>();

    //Constructor
    public Bomber(int _x, int _y, Image image) {
        super(_x, _y, image);
        movingState = uet.oop.bomberman.entities.Character.MovingState.STANDING;
        velocity = SCALED_SIZE / 8;
        respondX = x;
        respondY = y;
    }

    /**
     *  Remove all bombs.
     */
    public void clearBom() {
        bom.clear();
    }

    /**
     * Update if bomber move, collision with enemies.
     */
    public void update() {
        moveChecking(bom);
        move();
        updateImg();
        updateBom();

        if (deathLoading) {
            loadDestroyImage();
        } else {
            for (int i = 0; i < enemies.size(); i++) {
                if (CollisionTest(this.getRectangle(), enemies.get(i).getRectangle())) {
                    //die
                    deathLoading = true;
                }
            }
        }
        if (!alive) {
            respondBomber();
        }
    }

    /**
     * Update image of bomber
     */
    protected void updateImg() {
        switch (movingState) {
            case UP -> image = animate(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2);
            case DOWN -> image = animate(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2);
            case LEFT -> image = animate(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2);
            case RIGHT -> image = animate(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2);
            default -> {
            }
        }
    }

    // If a bomb was planted, remove it from list
    private void updateBom() {
        for (int i = 0; i < bom.size(); i++) {
            Bomb bomb = (Bomb) bom.get(i);
            if (bomb.exploded) {
                bom.remove(i);
            }
        }
        for (int i = 0; i < bom.size(); i++) {
            //bomb vs bomb
            Bomb bomb = (Bomb) bom.get(i);
            bomb.update();
            for (int j = i + 1; j < bom.size(); j++) {
                Bomb temp = (Bomb) bom.get(j);
                bomb.collisionWithBomb(temp);
            }
            //bomb vs character
            if (bomb.isExploding) {
                if (!deathLoading && bomb.collisionWithCharacter(this)) {
                    // if bomber died by bomb
                    System.out.println("Bomber has died");
                    deathLoading = true;
                }
                int n = enemies.size();
                for (int j = 0; j < n; j++) {
                    Enemy enemy = enemies.get(j);
                    // if enemy died by bomb
                    if (!enemy.deathLoading && bomb.collisionWithCharacter(enemy)) {
                        enemy.deathLoading = true;
                        System.out.println("enemy has died");
                    }
                }
            }
        }
    }

    // respond bomber if life > 0
    private void respondBomber() {
        if (maxLife > 0) {
            maxLife = maxLife - 1;
            System.out.println("Still has: " + maxLife + " life(s)");
            alive = true;
            x = respondX;
            y = respondY;
            bom.clear();
            deathLoading = false;
            image = Sprite.player_down_1.getFxImage();
        }
    }

    /**
     * bomber plant bomb on grass.
     */
    public void plantBomb() {
        if (bom.size() >= haveBomb) return;
        int ux = (x + SCALED_SIZE / 3) / SCALED_SIZE;
        int uy = (y + SCALED_SIZE / 3) / SCALED_SIZE;
        for (int i = 0; i < bom.size(); i++) {
            if (bom.get(i).getX() == ux * SCALED_SIZE && bom.get(i).getY() == uy * SCALED_SIZE) {
                return;
            }
        }
        playMedia(plantBombSound);
        bom.add(new Bomb(ux, uy, Sprite.bomb.getFxImage()));
    }

    /**
     * Reset life of bomber after pass 1 level.
     */
    public static void reset() {
        maxLife = 3;
        resetFlameLength();
    }

    /**
     * Load image if bomber died.
     */
    public void loadDestroyImage() {
        if (timeDeathLoading == 0) {
            alive = false;
            timeDeathLoading = FPS;
        }
        image = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, timeDeathLoading--, FPS).getFxImage();
    }

    /**
     * @param graphicsContext: render piece of image to (x, y) in the scene
     */
    public void render(GraphicsContext graphicsContext) {
        super.render(graphicsContext);
        bom.forEach(graphic -> graphic.render(graphicsContext));
    }

    public void speedUp() {
        velocity += SCALED_SIZE / 8;
    }

    public void moreBomb() {
        if (haveBomb < maxNumberOfBombs) {
            haveBomb++;
        }
    }
}
