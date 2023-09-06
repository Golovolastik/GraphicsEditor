package figures;

import javafx.scene.canvas.GraphicsContext;

public class Circle extends Figure {
    private double radius;

    public Circle(double x, double y) {
        super(x, y);
        this.radius = 50;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setLineWidth(getLineWidth());
        gc.setStroke(getStrokeColor());
        gc.strokeOval(getX() - radius, getY() - radius, 2 * radius, 2 * radius);    }
}
