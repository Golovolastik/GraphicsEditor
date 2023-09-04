package figures;

import javafx.scene.canvas.GraphicsContext;

public abstract class Figure implements Drawable {
    private double x;
    private double y;

    public Figure(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    // Этот метод должен быть реализован в подклассах
    public abstract void draw(GraphicsContext gc);
}
