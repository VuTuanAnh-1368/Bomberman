package uet.oop.bomberman.entities.character;

import java.util.*;

public enum MovingState {
    STANDING, UP, DOWN, LEFT, RIGHT;

    private static final List<MovingState> VALUES = List.of(values());

    private static final int SIZE = VALUES.size();

    private static final Random RANDOM = new Random();

    public static MovingState random() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
