package uet.oop.bomberman.entities.character;

import uet.oop.bomberman.BombermanGame;
import static uet.oop.bomberman.BombermanGame.FPS;

public interface Destroy {
    int destroyTime = FPS;

    void loadDestroyImage();
}
