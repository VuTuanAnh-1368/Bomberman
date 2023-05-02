package uet.oop.bomberman.entities.Character;

import uet.oop.bomberman.entities.Entity;

import java.util.List;

public interface Move {
    void MoveUp();

    void MoveDown();

    void MoveLeft();

    void MoveRight();

    void Standing();

    void moveChecking(List<Entity> entities);
}
