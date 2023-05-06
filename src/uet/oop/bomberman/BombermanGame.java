package uet.oop.bomberman;

/**
 * import libraries, don't modify
 */
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import uet.oop.bomberman.entities.Item.Item;
import uet.oop.bomberman.entities.Item.Portal;
import uet.oop.bomberman.entities.Item.*;
import uet.oop.bomberman.entities.Character.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Unmovable.*;

import uet.oop.bomberman.entities.Character.enemy.Balloom;
import uet.oop.bomberman.entities.Character.enemy.Doll;
import uet.oop.bomberman.entities.Character.enemy.Enemy;
import uet.oop.bomberman.entities.Character.enemy.Oneal;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.userInterface.Main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static uet.oop.bomberman.Sound.Sound.*;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;
import static uet.oop.bomberman.entities.character.Bomber.*;

import static uet.oop.bomberman.userInterface.userLoginController.username;
import static uet.oop.bomberman.userInterface.Main.*;
import static uet.oop.bomberman.userInterface.Main.addHistoryPlay;

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

    public Scene startGame(Stage stage) {
        stage.setTitle("Bomberman Go!");
        level = 1;
        point = 0;
        portal = null;
        GAMEOVERINFO = GAMEOVERINFO_LEVELUP;
        if (bomber != null) {
            bomber.clearBom();
        }
        bomber = null;
        timeLoadImage = FPS * 3;
        enemies = new ArrayList<>();
        map = new ArrayList<>();
        items = new ArrayList<>();
        reset();
        createMap();
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                long start = System.currentTimeMillis();
                // The update functions
                updateGameInfoImage();
                update();
                render();
                if ((GAMEOVERINFO.equals(GAMEOVERINFO_WIN) || GAMEOVERINFO.equals(GAMEOVERINFO_LOSE)) && timeLoadImage == 0) {
                    //System.out.println("End game! You Lose!");
                    try {
                        stop();
                        Main.loadHomePage(stage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                long realTime = System.currentTimeMillis() - start;
                if (realTime < timeEachFrame) {
                    try {
                        Thread.sleep(timeEachFrame - realTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();
        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);
        // Tao scene
        Scene scene = new Scene(root);
        // Them scene vao stage
        stage.setScene(scene);
        stage.setResizable(false);


        scene.setOnKeyPressed(key -> {
            switch (key.getCode()) {
                case UP:
                    bomber.MoveUp();
                    break;
                case DOWN:
                    bomber.MoveDown();
                    break;
                case LEFT:
                    bomber.MoveLeft();
                    break;
                case RIGHT:
                    bomber.MoveRight();
                    break;
                case SPACE:
                    bomber.plantBomb();
                    break;
                case Z:
                    enemies.clear();
                    break;
                case ESCAPE:
                    timer.stop();
                    break;
            }
        });
        scene.setOnKeyReleased((KeyEvent key) -> bomber.Standing());
        timer.start();
        return scene;
    }

    /**
     * Create map.
     */
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
                        case '#' -> object = new uet.oop.bomberman.entities.Unmovable.Wall(j, i, Sprite.wall.getFxImage());
                        case '*' -> object = new uet.oop.bomberman.entities.Unmovable.Brick(j, i, Sprite.brick.getFxImage());
                        case 'p' -> {
                            bomber = new Bomber(j, i, Sprite.player_right.getFxImage());
                            object = new uet.oop.bomberman.entities.Unmovable.Grass(j, i, Sprite.grass.getFxImage());
                        }
                        case '1' -> {
                            enemy = new Balloom(j, i, Sprite.balloom_right1.getFxImage());
                            object = new uet.oop.bomberman.entities.Unmovable.Grass(j, i, Sprite.grass.getFxImage());
                        }
                        case '2' -> {
                            enemy = new Doll(j, i, Sprite.doll_right1.getFxImage());
                            object = new uet.oop.bomberman.entities.Unmovable.Grass(j, i, Sprite.grass.getFxImage());
                        }
                        case '3' -> {
                            enemy = new Oneal(j, i, Sprite.oneal_right1.getFxImage());
                            object = new uet.oop.bomberman.entities.Unmovable.Grass(j, i, Sprite.grass.getFxImage());
                        }
                        // add new enemy

                        default -> object = new uet.oop.bomberman.entities.Unmovable.Grass(j, i, Sprite.grass.getFxImage());
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
     * update anything: bomber, items, map, status.
     */
    public void update() {
        if (GAMEOVERINFO.equals(GAMEOVERINFO_INGAME)) {
            bomber.update();
            updateEnemy();
            items.forEach(Entity::update);
            map.forEach(Entity::update);
            updateItem();
            if (portal != null) portal.update();
            updateMap();
            passThisLevelOrLose();
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
            bomber.render(gc);
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
            if (entity instanceof uet.oop.bomberman.entities.Unmovable.Brick && ((uet.oop.bomberman.entities.Unmovable.Brick) entity).destroyed) {
                Entity obj = new uet.oop.bomberman.entities.Unmovable.Grass(entity.getX() / SCALED_SIZE,
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

    private void updateEnemy() {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            if (enemy != null) {
                enemy.update();
                if (!enemy.alive) {
                    point += enemy.getPoint();
                    System.out.println("Point: " + point);
                    enemies.remove(enemy);
                }
            }
        }
    }

    private void passThisLevelOrLose() {
        if (enemies.size() == 0 && portal == null) {
            point = point + level * 100;
            level = level + 1;
            // level 1 : 100điểm, level2: 200 điểm
            timeLoadImage = FPS * 3;
            GAMEOVERINFO = GAMEOVERINFO_LEVELUP;
            if (level > 3) {
                GAMEOVERINFO = GAMEOVERINFO_WIN;
                playMedia(winGameSound);
                System.out.println(username + " " + point);
                //addHistoryPlay(username, point);
                timeLoadImage = FPS * 5;
                System.out.println("Well done, you won this game. Your score: " + this.point);
                return;
            }
            //playMedia(winGameSound);
            bomber.reset();
            bomber.clearBom();
            items.clear();
            map.clear();
            createMap();
            System.out.println("You won this level!");
        } else if (bomber.maxLife == 0) {
            GAMEOVERINFO = GAMEOVERINFO_LOSE;
            System.out.println(username + " " + point);
            addHistoryPlay(username, point);
            playMedia(loseGameSound);
            timeLoadImage = FPS * 3;
            System.out.println("You have lost. Your score: " + this.point);
        }

    }

    /**
     * update Game info image after play a level (or lose)
     */
    private void updateGameInfoImage() {
        Image inforImage = null;
        // LOSE
        if (GAMEOVERINFO.equals(GAMEOVERINFO_LOSE)) {
            playMusicInGame.stop();
            if (timeLoadImage >= 0) {
                timeLoadImage--;
            }
            try {
                inforImage = new Image(new FileInputStream("Resources/image/loseGame.jpg"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (timeLoadImage >= 0) gc.drawImage(inforImage, 0, 0);
            // WIN
        } else if (GAMEOVERINFO.equals(GAMEOVERINFO_WIN)) {
            playMusicInGame.stop();

            if (timeLoadImage >= 0) {
                timeLoadImage--;
            }
            try {
                inforImage = new Image(new FileInputStream("Resources/image/winGame.jpg"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (timeLoadImage >= 0) gc.drawImage(inforImage, 0 , 0);
            //LEVEL UP
        } else if (GAMEOVERINFO.equals(GAMEOVERINFO_LEVELUP)) {
            if (timeLoadImage >= 0) {
                timeLoadImage--;
            }
            try {
                String url = "Resources/image/level" + level + ".jpg";
                inforImage = new Image(new FileInputStream(url));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (timeLoadImage > 0) {
                gc.drawImage(inforImage, 0, 0);
            }
            if (timeLoadImage == 0) {
                GAMEOVERINFO = GAMEOVERINFO_INGAME;
            }
        }
    }

}
