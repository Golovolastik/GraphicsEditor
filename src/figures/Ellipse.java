package figures;

import javafx.scene.canvas.GraphicsContext;

public class Ellipse extends Figure {
    private double radiusX;
    private double radiusY;

    public Ellipse(double centerX, double centerY) {
        super(centerX, centerY);
        this.radiusX = 80;
        this.radiusY = 40;
    }

    public Ellipse() {
        this(0, 0);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setLineWidth(getLineWidth());
        gc.setStroke(getStrokeColor());
        gc.strokeOval(getX() - radiusX, getY() - radiusY, 2 * radiusX, 2 * radiusY);
    }
}
