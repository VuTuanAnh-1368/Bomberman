package uet.oop.bomberman.entities.Character;

import static uet.oop.bomberman.BombermanGame.FPS;

public interface Destroy {
    int destroyTime = FPS;

    void loadDestroyImage();
}
