package uet.oop.bomberman;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Item.Item;
import uet.oop.bomberman.entities.Item.Portal;
import uet.oop.bomberman.entities.Character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.Item.*;
import uet.oop.bomberman.entities.Character.Bomber;
import uet.oop.bomberman.entities.Character.enemy.Enemy;
import uet.oop.bomberman.entities.Unmovable.Brick;
import uet.oop.bomberman.entities.Unmovable.Grass;
import uet.oop.bomberman.entities.Unmovable.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.Character.enemy.Balloom;
import uet.oop.bomberman.entities.Character.enemy.Doll;
import uet.oop.bomberman.entities.Character.enemy.Oneal;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

/**
 * import libraries, don't modify
 */

public class BombermanGame {
    /**
     * local variables, don't modify
     */
    public static int WIDTH;
    public static int HEIGHT;
    public static final int FPS = 20;
    private GraphicsContext gc;
    private Canvas canvas;
    private int timeLoadImage;
    public static List<Enemy> enemies;
    public static List<Item> items;
    public static List<Entity> map;
    public static Bomber bomber;
    public static final int timeEachFrame = 1000 / FPS;
    public static int level;
    public static int point;
    public static Portal portal;
    public static final String GAMEOVERINFO_LOSE = "LOSE";
    public static final String GAMEOVERINFO_WIN = "WIN";
    public static final String GAMEOVERINFO_LEVELUP = "LEVELUP";
    public static final String GAMEOVERINFO_INGAME = "INGAME";

    public static String GAMEOVERINFO = GAMEOVERINFO_LEVELUP;

    public BombermanGame() {}
    public void createMap() {
        try {
            String path = "Resources/levels/Level" + level + ".txt";
            FileReader fileReader = new FileReader(path);
            Scanner sc = new Scanner(fileReader);
            sc.nextInt();
            HEIGHT = sc.nextInt();
            WIDTH = sc.nextInt();
            int I = sc.nextInt();
            sc.nextLine();

            // Load map
            for (int i = 0; i < HEIGHT; i++) {
                String temp = sc.nextLine();
                for (int j = 0; j < WIDTH; j++) {
                    Entity object;
                    Enemy enemy = null;
                    char p = temp.charAt(j);

                    switch (p) {
                        case '#' -> object = new Wall(j, i, Sprite.wall.getFxImage());
                        case '*' -> object = new Brick(j, i, Sprite.brick.getFxImage());
                        case 'p' -> {
                            bomber = new uet.oop.bomberman.entities.character.Bomber(j, i, Sprite.player_right.getFxImage());
                            object = new Grass(j, i, Sprite.grass.getFxImage());
                        }
                        case '1' -> {
                            enemy = new Balloom(j, i, Sprite.balloom_right1.getFxImage());
                            object = new Grass(j, i, Sprite.grass.getFxImage());
                        }
                        case '2' -> {
                            enemy = new Doll(j, i, Sprite.doll_right1.getFxImage());
                            object = new Grass(j, i, Sprite.grass.getFxImage());
                        }
                        case '3' -> {
                            enemy = new Oneal(j, i, Sprite.oneal_right1.getFxImage());
                            object = new Grass(j, i, Sprite.grass.getFxImage());
                        }
                        // add new enemy

                        default -> object = new Grass(j, i, Sprite.grass.getFxImage());
                    }
                    if (enemy != null) enemies.add(enemy);
                    map.add(object);
                }
            }

            // Load item
            for (int i = 0; i < I; i++) {
                String temp = sc.nextLine();
                String[] s = temp.split(" ");
                if ("x".equals(s[0])) {
                    for (int j = 1; j < s.length; j += 2) {
                        int xUnit = Integer.parseInt(s[j + 1]);
                        int yUnit = Integer.parseInt(s[j]);
                        portal = new Portal(xUnit, yUnit, Sprite.portal.getFxImage());
                    }
                }
                if ("s".equals(s[0])) {
                    for (int j = 1; j < s.length; j += 2) {
                        int xUnit = Integer.parseInt(s[j + 1]);
                        int yUnit = Integer.parseInt(s[j]);
                        items.add(new SpeedUp(xUnit, yUnit, Sprite.powerup_speed.getFxImage()));
                    }
                }
                if ("b".equals(s[0])) {
                    for (int j = 1; j < s.length; j += 2) {
                        int xUnit = Integer.parseInt(s[j + 1]);
                        int yUnit = Integer.parseInt(s[j]);
                        items.add(new MoreBomb(xUnit, yUnit, Sprite.powerup_bombs.getFxImage()));
                    }
                }
                if ("f".equals(s[0])) {
                    for (int j = 1; j < s.length; j += 2) {
                        int xUnit = Integer.parseInt(s[j + 1]);
                        int yUnit = Integer.parseInt(s[j]);
                        items.add(new LongerFlame(xUnit, yUnit, Sprite.powerup_flames.getFxImage()));
                    }
                }
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }



    /**
     * render if in a level.
     */
    public void render() {
        if (GAMEOVERINFO.equals(GAMEOVERINFO_INGAME)) {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            map.forEach(g -> g.render(gc));
            items.forEach(g -> g.render(gc));
            if (portal != null) portal.render(gc);
            enemies.forEach(g -> g.render(gc));
            //bomber.render(gc);
        }
    }
    public int getFPS() {
        return FPS;
    }

    /**
     * update map
     */
    private void updateMap() {
        int n = map.size();
        for (int j = 0; j < n; j++) {
            Entity entity = map.get(j);
            if (entity instanceof Brick && ((Brick) entity).destroyed) {
                Entity obj = new Grass(entity.getX() / SCALED_SIZE,
                        entity.getY() / SCALED_SIZE, Sprite.grass.getFxImage());
                map.remove(j);
                map.add(j, obj);
            }
        }
    }

    private void updateItem() {
        for (int i = 0; i < items.size(); i++) {
            if (!items.get(i).Lying) {
                items.remove(i);
                return;
            }
        }
    }
}
