package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Entity {
    private boolean movingUp = false;
    private boolean movingDown = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    public void placeBomb() {
        // Xử lý việc đặt bom
        // Ví dụ: Tạo đối tượng bom mới và đặt bom vào vị trí hiện tại của Bomber
        Bomb bomb = new Bomb(this.x, this.y); // Giả sử Bomb có constructor nhận vào tọa độ x, y
        // Thêm bomb vào danh sách quản lý bomb của Bomber hoặc của đối tượng quản lý bom khác
        // ...
    }

    @Override
    public void update() {
        if (movingUp) {
            setY(getY() - 1);
        }
        if (movingDown) {
            setY(getY() + 1);
        }
        if (movingLeft) {
            setX(getX() - 1);
        }
        if (movingRight) {
            setX(getX() + 1);
        }
        if (!movingDown && !movingLeft && !movingRight && !movingUp) {
            setX(Math.round(getX()));
            setY(Math.round(getY()));
        }
    }

    public void moveUp() {
        movingUp = true;
    }

    public void moveDown() {
        movingDown = true;
    }

    public void moveLeft() {
        movingLeft = true;
    }

    public void moveRight() {
        movingRight = true;
    }

    public void stopMoving() {
        movingUp = false;
        movingDown = false;
        movingLeft = false;
        movingRight = false;
    }
}
