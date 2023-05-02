package uet.oop.bomberman;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Item.Item;
import uet.oop.bomberman.entities.Item.Portal;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;

import java.util.List;

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

}
