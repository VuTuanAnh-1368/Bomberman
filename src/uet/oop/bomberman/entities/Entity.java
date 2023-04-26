package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.geom.Rectangle2D;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected Image img;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity( int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    public abstract void update();

    /**
     * Method set/get x,y.
     */
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isCollidedWith(Entity other, int xNew, int yNew) {
        // Tính toán khoảng cách giữa 2 đối tượng
        double distance = Math.sqrt(Math.pow((xNew + Sprite.SCALED_SIZE/2) - (other.x + Sprite.SCALED_SIZE/2), 2) +
                Math.pow((yNew + Sprite.SCALED_SIZE/2) - (other.y + Sprite.SCALED_SIZE/2), 2));
        // Nếu khoảng cách nhỏ hơn hoặc bằng với bán kính của 2 đối tượng thì có va chạm
        return distance <= Sprite.SCALED_SIZE;
    }

    public boolean intersects(Entity entity) {
        return (this.getX() + Sprite.SCALED_SIZE > entity.getX()) &&
                (this.getX() < entity.getX() + Sprite.SCALED_SIZE) &&
                (this.getY() + Sprite.SCALED_SIZE > entity.getY()) &&
                (this.getY() < entity.getY() + Sprite.SCALED_SIZE);
    }

}
