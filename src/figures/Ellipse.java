package figures;

import javafx.scene.canvas.GraphicsContext;

public class Ellipse extends Figure implements Drawable {
    private double radiusX;
    private double radiusY;

    public Ellipse(double centerX, double centerY, double radiusX, double radiusY) {
        super(centerX, centerY);
        this.radiusX = radiusX;
        this.radiusY = radiusY;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.fillOval(getX() - radiusX, getY() - radiusY, 2 * radiusX, 2 * radiusY);
    }
}
