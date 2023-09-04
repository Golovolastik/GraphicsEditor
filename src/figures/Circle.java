package figures;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle extends Figure implements Drawable {
    private double radius;

    public Circle(double centerX, double centerY, double radius) {
        super(centerX, centerY);
        this.radius = radius;
    }
    public double getRadius() {
        return radius;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillOval(getX() - radius, getY() - radius, 2 * radius, 2 * radius);    }
}
