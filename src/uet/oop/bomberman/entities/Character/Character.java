package uet.oop.bomberman.entities.character;

import javafx.scene.image.Image;
import java.awt.Rectangle;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Unmovable.Grass;
import uet.oop.bomberman.graphics.Sprite;
import java.util.List;
import static uet.oop.bomberman.BombermanGame.FPS;
import static uet.oop.bomberman.BombermanGame.WIDTH;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;
import static uet.oop.bomberman.BombermanGame.map;
import static uet.oop.bomberman.BombermanGame.*;
import static uet.oop.bomberman.graphics.Sprite.bomb;

public abstract class Character extends Entity implements Destroy, Move {

    protected int timeAnimation = 6;

    protected int presentImage = 2;

    protected int velocity;

    protected boolean movable;

    protected boolean deathLoading = false;

    protected MovingState movingState;

    protected int timeDeathLoading = FPS;

    public boolean alive = true;

    public Character(int x, int y, Image image) {
        super(x, y, image);
    }

    @Override
    public void MoveUp() {
        movingState = MovingState.UP;
    }

    @Override
    public void MoveDown() {
        movingState = MovingState.DOWN;
    }

    @Override
    public void MoveLeft() {
        movingState = MovingState.LEFT;
    }

    @Override
    public void MoveRight() {
        movingState = MovingState.RIGHT;
    }

    @Override
    public void Standing() {
        movingState = MovingState.STANDING;
    }

    public Rectangle getNextRectangle() {
        return switch (movingState) {
            case UP -> new Rectangle(x, y - velocity, SCALED_SIZE, SCALED_SIZE);
            case DOWN -> new Rectangle(x, y + velocity, SCALED_SIZE, SCALED_SIZE);
            case LEFT -> new Rectangle(x - velocity, y, SCALED_SIZE, SCALED_SIZE);
            case RIGHT -> new Rectangle(x + velocity, y, SCALED_SIZE, SCALED_SIZE);
            default -> null;
        };
    }

    protected void move() {
        if (!alive || deathLoading) return;
        if (!movable) return;
        switch (movingState) {
            case UP:
                y = y - velocity;
                break;
            case DOWN:
                y = y + velocity;
                break;
            case LEFT:
                x = x - velocity;
                break;
            case RIGHT:
                x = x + velocity;
                break;
            default: break;
        }
    }

    @Override
    public void moveChecking(List<Entity> bom) {
        if (!alive || deathLoading || movingState == MovingState.STANDING) {
            movable = false;
            return;
        }
        int position = getPosition();
        Rectangle rec = null;
        Entity map1 = null;
        Entity map2 = null;
        int D = SCALED_SIZE / 4;
        switch (movingState) {
            case UP:
                rec = new Rectangle(x + 2, y - velocity, SCALED_SIZE, SCALED_SIZE);
                if (position - WIDTH < 0) {
                    movable = false;
                    return;
                }
                map1 = map.get(position - WIDTH);
                map2 = map.get(position - WIDTH + 1);
                if (!(map1 instanceof Grass)) {
                    if (map2 instanceof Grass && (map2.getX() - x >= 0) && (map2.getX() - x <= D)) {
                        x = map2.getX();
                        return;
                    } else if (CollisionTest(rec, map1.getRectangle())) {
                        movable = false;
                        return;
                    }
                }
                if (!(map2 instanceof Grass)) {
                    if (map1 instanceof Grass && (x - map1.getX() >= 0) && (x - map1.getX() <= D + 5)) {
                        x = map1.getX();
                        break;
                    } else if (CollisionTest(rec, map2.getRectangle())) {
                        movable = false;
                        return;
                    }
                }
                break;

            case DOWN:
                rec = new Rectangle(x + 2, y + velocity, SCALED_SIZE, SCALED_SIZE);
                if (position + WIDTH + 1 >= map.size()) {
                    movable = false;
                    return;
                }
                map1 = map.get(position + WIDTH);
                map2 = map.get(position + WIDTH + 1);
                if (!(map1 instanceof Grass)) {
                    if (map2 instanceof Grass && (map2.getX() - x >= 0) && (map2.getX() - x <= D)) {
                        x = map2.getX();
                        break;
                    } else if (CollisionTest(rec, map1.getRectangle())) {
                        movable = false;
                        return;
                    }
                }
                if (!(map2 instanceof Grass)) {
                    if (map1 instanceof Grass && x - map1.getX() >= 0 && x - map1.getX() <= D + 5) {
                        x = map1.getX();
                        break;
                    } else if (CollisionTest(rec, map2.getRectangle())) {
                        movable = false;
                        return;
                    }
                }
                break;

            case LEFT:
                rec = new Rectangle(x - velocity, y + 2, SCALED_SIZE, SCALED_SIZE);
                if (position - 1 < 0 || position + WIDTH - 1 >= map.size()) {
                    movable = false;
                    return;
                }
                map1 = map.get(position - 1);
                map2 = map.get(position + WIDTH - 1);
                if (!(map1 instanceof Grass)) {
                    if (map2 instanceof Grass && map2.getY() - y >= 0 && map2.getY() - y <= D) {
                        y = map2.getY();
                        break;
                    } else if (CollisionTest(rec, map1.getRectangle())) {
                        movable = false;
                        return;
                    }

                }
                if (!(map2 instanceof Grass)) {
                    if (map1 instanceof Grass && y - map1.getY() >= 0 && y - map1.getY() <= D) {
                        y = map1.getY();
                        break;
                    } else if (CollisionTest(rec, map2.getRectangle())) {
                        movable = false;
                        return;
                    }
                }
                break;

            case RIGHT:
                rec = new Rectangle(x + velocity, y + 2, SCALED_SIZE, SCALED_SIZE);
                if (position - 1 < 0 || position + WIDTH + 1 >= map.size()) {
                    movable = false;
                    return;
                }
                map1 = map.get(position + 1);
                map2 = map.get(position + WIDTH + 1);
                if (!(map1 instanceof Grass)) {
                    if (map2 instanceof Grass && map2.getY() - y >= 0 && map2.getY() - y <= D) {
                        y = map2.getY();
                        break;
                    } else if (CollisionTest(rec, map1.getRectangle())) {
                        movable = false;
                        return;
                    }

                }
                if (!(map2 instanceof Grass)) {
                    if (map1 instanceof Grass && y - map1.getY() >= 0 && y - map1.getY() <= D) {
                        y = map1.getY();
                        break;
                    } else if (CollisionTest(rec, map2.getRectangle())) {
                        movable = false;
                        return;
                    }
                }
                break;
        }


        // Check collision with unExploded bomb
        rec = new Rectangle(x, y, SCALED_SIZE, SCALED_SIZE);
        for (int i = 0; i < bom.size(); i++) {
            if (!CollisionTest(rec, bom.get(i).getRectangle())) {
                Rectangle tmp = getNextRectangleBomber();
                if (CollisionTest(tmp, bom.get(i).getRectangle())) {
                    movable = false;
                    return;
                }
            }
        }
        movable = true;
    }

    protected Image animate(Sprite normal, Sprite X1, Sprite X2) {
        presentImage = presentImage % timeAnimation;
        return Sprite.movingSprite(normal, X1, X2, (presentImage++), timeAnimation).getFxImage();
    }

    public Rectangle getNextRectangleBomber() {
        switch (movingState) {
            case UP:
                return new Rectangle(x, y - velocity, SCALED_SIZE, SCALED_SIZE);
            case DOWN:
                return new Rectangle(x, y + velocity, SCALED_SIZE, SCALED_SIZE);
            case LEFT:
                return new Rectangle(x - velocity, y, SCALED_SIZE, SCALED_SIZE);
            case RIGHT:
                return new Rectangle(x + velocity, y, SCALED_SIZE, SCALED_SIZE);
        }
        return null;

    }

    protected abstract void updateImg();
}


