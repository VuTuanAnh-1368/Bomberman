package uet.oop.bomberman.keyboard;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.entities.Bomber;

public class KeyControl implements EventHandler<KeyEvent> {
    private Bomber bomber; // Đối tượng Bomber để điều khiển

    public KeyControl(Bomber bomber) {
        this.bomber = bomber;
    }

    @Override
    public void handle(KeyEvent event) {
        KeyCode keyCode = event.getCode();

        // Xử lý sự kiện từ bàn phím
        if (event.getEventType() == KeyEvent.KEY_PRESSED && bomber.getY() > 0 && bomber.getX() > 0) {
            // Xử lý sự kiện từ bàn phím khi phím được nhấn
            if (keyCode == KeyCode.UP) {
                bomber.moveUp();
            } else if (keyCode == KeyCode.DOWN) {
                bomber.moveDown();
            } else if (keyCode == KeyCode.LEFT) {
                bomber.moveLeft();
            } else if (keyCode == KeyCode.RIGHT) {
                bomber.moveRight();
            } else if (keyCode == KeyCode.SPACE) {
                bomber.placeBomb();
            }
        } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            // Xử lý sự kiện từ bàn phím khi phím được thả
            if (keyCode == KeyCode.UP) {
                bomber.stopMoving(); // Dừng sự kiện di chuyển khi thả phím UP
            } else if (keyCode == KeyCode.DOWN) {
                bomber.stopMoving(); // Dừng sự kiện di chuyển khi thả phím DOWN
            } else if (keyCode == KeyCode.LEFT) {
                bomber.stopMoving(); // Dừng sự kiện di chuyển khi thả phím LEFT
            } else if (keyCode == KeyCode.RIGHT) {
                bomber.stopMoving(); // Dừng sự kiện di chuyển khi thả phím RIGHT
            }
        }
    }
}
