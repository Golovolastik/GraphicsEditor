package figures;

import javafx.scene.canvas.GraphicsContext;

public class Ellipse extends Figure {
    private double radiusX;
    private double radiusY;

    public Ellipse(double centerX, double centerY, double radiusX, double radiusY) {
        super(centerX, centerY);
        this.radiusX = radiusX;
        this.radiusY = radiusY;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setLineWidth(getLineWidth());
        gc.setStroke(getStrokeColor());
        gc.strokeOval(getX() - radiusX, getY() - radiusY, 2 * radiusX, 2 * radiusY);
    }
}
